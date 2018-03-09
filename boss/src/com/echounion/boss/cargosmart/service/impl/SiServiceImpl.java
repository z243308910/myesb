package com.echounion.boss.cargosmart.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.Callable;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echounion.boss.cargosmart.entity.CargosmartFileRoutingLog;
import com.echounion.boss.cargosmart.enums.SiFlag;
import com.echounion.boss.cargosmart.service.ISiService;
import com.echounion.boss.cargosmart.si.CargoSmartConfig;
import com.echounion.boss.common.enums.RecordType;
import com.echounion.boss.core.ftp.FtpConfig;
import com.echounion.boss.core.ftp.FtpUtil;
import com.echounion.boss.entity.dto.Record;
import com.echounion.boss.logs.service.ILogService;

@Service
@Transactional(propagation=Propagation.SUPPORTS)
public class SiServiceImpl implements ISiService {

	private static Logger logger = Logger.getLogger(SiServiceImpl.class);
	
	@Autowired
	@Qualifier("cargosmartFtp")
	private FtpConfig ftpconfig;
	
	@Autowired
	@Qualifier("cargosmartPath")
	private CargoSmartConfig pathConfig; 
	
	@Autowired
	@Qualifier("gatewayTaskExecutor")
	private ThreadPoolTaskExecutor gatewayTaskExecutor;
	
	@Autowired
	@Qualifier("CargosmartFileRoutingLogServiceImpl")
	private ILogService<CargosmartFileRoutingLog> fileRoutingLogService;
	
	@Override
	public Record postSiData(String fileName,String siXml) {
		if(StringUtils.isBlank(fileName))
		{
			logger.warn("SI文件名称为空");
			return Record.setFailRecord(SiFlag.NULL_FILENAME.getCode(),SiFlag.NULL_FILENAME.getName(),RecordType.TEXT);
		}
		if(StringUtils.isBlank(siXml))
		{
			logger.warn("SI文件内容为空!");
			return Record.setFailRecord(SiFlag.NULL_CONTENT.getCode(),SiFlag.NULL_CONTENT.getName(),RecordType.TEXT);
		}
		submitSi(fileName,siXml);
		return Record.setSuccessRecord(SiFlag.SUCCESS.getName(), false,RecordType.TEXT);
	}
	
	/**
	 * 异步处理SI信息
	 * @author 胡礼波
	 * 2013-10-14 下午5:08:21
	 * @param fileName
	 * @param siXml
	 */
	private void submitSi(final String fileName,final String siXml)
	{
		Callable<String> call=new Callable<String>() {
			@Override
			public String call() throws Exception {
				try {
					saveSIToDisk(fileName,siXml);	//SI存放到本地 然后触发目录监听接口 处理si文件
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
		};
		gatewayTaskExecutor.submit(call);
	}
	
	/**
	 * 把SI信息存到本地xml格式 返回存放的地址
	 * @author 胡礼波
	 * 2013-10-14 下午2:47:14
	 * @return
	 */
	private String saveSIToDisk(String fileName,String siXml)throws IOException
	{
		String path=pathConfig.getMonitorLocalPath().get("si")+fileName;
		File file=new File(path);
		try
		{
			FileUtils.writeStringToFile(file,siXml);
			logger.info(fileName+"SI文件保存成功!");
			addFileRoutingLog(fileName,fileName+"文件保存成功，正等待上传处理!");
		}catch (Exception e) {
			logger.error(fileName+"文件保存失败,"+e.getMessage());
			addFileRoutingLog(fileName,fileName+"文件保存失败!"+e.getMessage());
		}
		return path;
	}
	
	/**
	 * 上传到远程SI目录
	 * @author 胡礼波
	 * 2014-3-29 上午11:54:10
	 * @param path
	 * @return
	 * @throws IOException
	 */
	@Override
	public boolean uploadSiToFtp(String path)throws IOException
	{
		boolean flag=uploadFtp(path);
		return flag;
	}
	
	/**
	 * 上传到CS的FTP上 上传成功后则删除上传文件并备份一份到备份目录
	 * @author 胡礼波
	 * 2013-10-14 下午1:52:25
	 * @return
	 */
	private boolean uploadFtp(String path)throws IOException
	{
		FTPClient client=null;
		boolean upload=false;
		try {
			client=FtpUtil.getFtpClient(ftpconfig);
			boolean flag=client.changeWorkingDirectory(pathConfig.getRemoteSi());		//转到SI的目录
			logger.info("切换远程SI目录.......");
			logger.info(client.getReplyString());
			File file=new File(path);
			if(flag)
			{
				upload=client.storeFile(file.getName(),new FileInputStream(file));
			    logger.info(client.getReplyString());
			    if(upload)
			    {
			    	logger.info(path+"上传成功!");
			    	addFileRoutingLog(file.getName(),file.getName()+"文件成功上传CS服务器!");
			    }else
			    {
			    	logger.warn(path+"上传失败!");
			    	addFileRoutingLog(file.getName(),file.getName()+"文件上传CS服务器失败!");
			    }
			}else
			{
				logger.info("切换远程SI目录失败!");
				addFileRoutingLog(file.getName(),file.getName()+"文件上传CS服务器失败，切换CS SI目录不成功!");
			}
		}finally
		{
			FtpUtil.closeFtp(client);
		}
		if(upload)				//上传成功后备份文件 然后删除源文件
		{
			saveSIToBackDisk(path);
		}
		return upload;
	}
	
	/**
	 * 删除上传的文件然后保存一份到备份文件夹
	 * @author 胡礼波
	 * 2014-3-29 上午11:36:52
	 * @param fileName
	 * @param siXml
	 * @return
	 * @throws IOException
	 */
	private String saveSIToBackDisk(String path) throws IOException
	{
		File file=new File(path);
		String target=pathConfig.getLocalSi();
		try
		{
			Files.move(Paths.get(path),Paths.get(target,file.getName()),StandardCopyOption.REPLACE_EXISTING,StandardCopyOption.ATOMIC_MOVE);
			logger.info(file.getName()+" SI备份文件保存成功!");
			addFileRoutingLog(file.getName(),file.getName()+"文件删除成功，备份成功!");
		}catch (Exception e) {
			logger.error(file.getName()+"文件删除备份过程中异常!"+e.getMessage());
			addFileRoutingLog(file.getName(),file.getName()+"文件删除备份过程中异常!"+e.getMessage());
		}
		return path;
	}
	
	/**
	 * 公用的记录文件流转日志
	 * @param fileName
	 * @param remark
	 */
	private void addFileRoutingLog(String fileName,String remark)
	{
		CargosmartFileRoutingLog log=new CargosmartFileRoutingLog(fileName, remark);
		fileRoutingLogService.addLog(log);
	}
}
