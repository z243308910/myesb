package com.echounion.boss.cargosmart.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.echounion.boss.cargosmart.entity.CargosmartFileRoutingLog;
import com.echounion.boss.cargosmart.service.ISiService;
import com.echounion.boss.cargosmart.si.CargoSmartConfig;
import com.echounion.boss.cargosmart.tracking.TrackingXmlParse;
import com.echounion.boss.core.cache.UrlContainer;
import com.echounion.boss.core.esb.service.IEsbService;
import com.echounion.boss.core.monitor.service.impl.IMonitorService;
import com.echounion.boss.entity.dto.Provider;
import com.echounion.boss.entity.dto.Record;
import com.echounion.boss.logs.service.ILogService;

/**
 * CargoSmart FTP 监控目录实现类
 * @author 胡礼波
 * 2013-10-14 下午3:39:10
 */
@Service("FtpDirMonitorServiceImpl")
public class FtpDirMonitorServiceImpl implements IMonitorService {

	private static Logger logger = Logger.getLogger(FtpDirMonitorServiceImpl.class);
	
	@Autowired
	@Qualifier("cargosmartPath")
	private CargoSmartConfig pathConfig;
	@Autowired
	private IEsbService esbService;
	
	@Autowired
	private TrackingXmlParse xmlParse;
	
	@Autowired
	private ISiService siService;

	@Autowired
	@Qualifier("gatewayTaskExecutor")
	private ThreadPoolTaskExecutor gatewayTaskExecutor;
	
	@Autowired
	@Qualifier("CargosmartFileRoutingLogServiceImpl")
	private ILogService<CargosmartFileRoutingLog> fileRoutingLogService;
	
	@Override
	public void onCreate(Path path) {
		final String filePath=path.toString();
		logger.info("开始处理文件"+path);
		Callable<String> call=new Callable<String>()
		{
			@Override
			public String call() throws Exception {
				if(filePath.startsWith("CHINATRANS_AKXML"))		//SI的监控目录处理--SI回执
				{
					doSiAckCallBack(filePath);
				}
				else if(filePath.startsWith("CHINATRANS_CTXML"))			//CT的监控目录---cargo tracking
				{
					doCtCallBack(filePath);
				}
				else if(filePath.startsWith("CHINATRANS_BLXML"))
				{
					doBlCallBack(filePath);
				}
				else if(filePath.startsWith("CHINATRANS_SIXML"))		//SI上传的监控那个目录
				{
					doSiUploadBack(filePath);
				}
				return null;
			}
	
		};
		gatewayTaskExecutor.submit(call);
	}

	/**
	 * 处理SI的上传
	 * @author 胡礼波
	 * 2013-10-22 上午11:50:02
	 */
	private void doSiUploadBack(String filePath)
	{
		Path source=Paths.get(pathConfig.getMonitorLocalPath().get("si"),filePath);
		if(source==null)
		{
			logger.info(filePath+"不存在!");
			return;
		}
		int index=1;
		boolean isUpload=false;										//判断是否上传成功
		File file=null;												//要上传的文件
			do
			{
				try
				{
					Thread.sleep(5000);
					file=source.toFile();
					if(!file.exists())
					{
						break;
					}
					boolean flag=file.renameTo(file);				//通过修改文件名来判断文件是否写入完毕
					if(flag)										//文件写入完毕
					{
						isUpload=siService.uploadSiToFtp(source.toString());
						break;
					}
				}catch (Exception e)
				{
					logger.error(file.getName()+"SI文件上传出错:"+e.getMessage());
					addFileRoutingLog(file.getName(),file.getName()+"SI文件第"+index+"次上传出错:"+e.getMessage());
				}
				index++;
				if(index<=3)
				{
					logger.warn("正在等待文件第"+index+"次操作.......");
				}
			}
			while(index<=3);
			if(file!=null)
			{
				siUploadCallBackToBC(file.getName(),isUpload);
			}
	}
	
	/**
	 * SI上传cargosmart后的回调BC接口
	 * @param file
	 * @param flag
	 */
	private void siUploadCallBackToBC(String fileName,boolean flag)
	{
		boolean isNull=false;
		Provider provider= UrlContainer.getServiceProviderByCode("sicallback");
		if(provider!=null)
		{
			invokeSiUploadCallBack(provider,fileName,flag);
			isNull=true;
		}
		
		provider= UrlContainer.getServiceProviderByCode("bcsicallback");
		if(provider!=null)
		{
			invokeSiUploadCallBack(provider,fileName,flag);
			isNull=true;
		}
		if(!isNull)
		{
			logger.warn("SI回调接口服务提供方为空!");
			addFileRoutingLog(fileName,"BC回调接口为空,请检查BOSS接口配置<sicallback、bcsicallback>");
		}
	}
	
	/**
	 * 处理SI的回执
	 * @author 胡礼波
	 * 2013-10-22 上午11:50:02
	 */
	private void doSiAckCallBack(String filePath)
	{
		Path target=Paths.get(pathConfig.getMonitorLocalPath().get("ack"),filePath);
		if(target==null)
		{
			logger.info(filePath+"不存在!");
			return;
		}
		int index=1;
			do
			{
				try
				{
					Thread.sleep(5000);
					File file=target.toFile();
					if(!file.exists())
					{
						break;
					}
					boolean flag=file.renameTo(file);				//通过修改文件名来判断文件是否写入完毕
					if(flag)										//文件写入完毕
					{
						siAckCallBack(file);
						Path path=Paths.get(pathConfig.getLocalAck(),filePath);		//处理完后移除监控目录ACK文件到本地本份库中
						try
						{
							Files.move(target, path,StandardCopyOption.REPLACE_EXISTING,StandardCopyOption.ATOMIC_MOVE);
							logger.info("ACK文件"+filePath+"处理成功!");
							addFileRoutingLog(file.getName(),file.getName()+"文件删除成功，备份成功!");
						}catch (Exception e) {
							logger.error(file.getName()+"文件删除备份过程中异常!"+e.getMessage());
							addFileRoutingLog(file.getName(),file.getName()+"文件删除备份过程中异常!");
						}
						break;
					}
				}catch (Exception e) {e.printStackTrace();}
				index++;
				logger.warn("正在等待文件第"+index+"次写入.......");
			}
			while(index<=3);
	}
	
	/**
	 * ACK文件回调
	 * @param file
	 * @throws Exception
	 */
	private void siAckCallBack(File file) throws Exception
	{
		boolean isNull=false;
		Provider provider= UrlContainer.getServiceProviderByCode("ftpsicallback");		//前端后端各自触发
		if(provider!=null)
		{
			invokeBcCallBack(file,provider,"SI","ackXml");
			isNull=true;
		}
		
		provider= UrlContainer.getServiceProviderByCode("bcftpsicallback");
		if(provider!=null)
		{
			invokeBcCallBack(file,provider,"SI","ackXml");
			isNull=true;
		}
		if(!isNull)
		{
			logger.warn("SI ACK回调接口服务提供方为空!");
			addFileRoutingLog(file.getName(),"BC ACK回调接口为空,请检查BOSS接口配置<ftpsicallback、bcftpsicallback>");
		}
	}
	
	/**
	 * 处理cargo tracking的信息
	 * @author 胡礼波
	 * 2013-10-22 上午11:50:41
	 */
	private void doCtCallBack(String filePath)
	{
		Path target=Paths.get(pathConfig.getMonitorLocalPath().get("ct"),filePath);
		if(target==null)
		{
			logger.info(filePath+"不存在!");
			return;
		}
		int index=1;
			do
			{
				try {
						Thread.sleep(5000);
						File file=target.toFile();
						if(!file.exists())
						{
							break;
						}
						boolean flag=file.renameTo(file);				//通过修改文件名来判断文件是否写入完毕
						if(flag)										//文件写入完毕
						{
							xmlParse.parse(file);
							Path path=Paths.get(pathConfig.getLocalCt(),filePath);		//处理完后移除监控目录CT文件到本地本份库中
							Files.move(target, path,StandardCopyOption.REPLACE_EXISTING,StandardCopyOption.ATOMIC_MOVE);
							logger.info("CT文件"+filePath+"处理成功!");
							break;
						}
					}catch (Exception e) {logger.error(e.getMessage());}
				index++;
				logger.warn("正在等待文件第"+index+"次写入.......");
			}
			while(index<=3);
	}
	
	/**
	 * 处理BL的信息
	 * @author 胡礼波
	 * 2013-10-22 上午11:50:41
	 */
	private void doBlCallBack(String filePath)
	{
		Path target=Paths.get(pathConfig.getMonitorLocalPath().get("bl"),filePath);
		if(target==null)
		{
			logger.info(filePath+"不存在!");
			return;
		}
		int index=1;
			do
			{
				try {
						Thread.sleep(5000);
						File file=target.toFile();
						if(!file.exists())
						{
							break;
						}
						boolean flag=file.renameTo(file);				//通过修改文件名来判断文件是否写入完毕
						if(flag)										//文件写入完毕
						{
							blCallBack(file);
							Path path=Paths.get(pathConfig.getLocalBl(),filePath);		//处理完后移除监控目录CT文件到本地本份库中
							try
							{
								Files.move(target, path,StandardCopyOption.REPLACE_EXISTING,StandardCopyOption.ATOMIC_MOVE);
								logger.info("BL回执文件"+filePath+"处理成功!");
								addFileRoutingLog(file.getName(),file.getName()+"文件删除成功，备份成功!");
							}catch (Exception e) {
								logger.error(file.getName()+"文件删除备份过程中异常!"+e.getMessage());
								addFileRoutingLog(file.getName(),file.getName()+"文件删除备份过程中异常!");
							}
							break;
						}
					}catch (Exception e) {e.printStackTrace();}
				index++;
				logger.warn("正在等待文件第"+index+"次写入.......");
			}
			while(index<=3);
	}
	
	private void blCallBack(File file) throws Exception
	{
		boolean isNull=false;
		Provider provider= UrlContainer.getServiceProviderByCode("ftpblcallback");		//前端后端各自触发
		if(provider!=null)
		{
			invokeBcCallBack(file,provider,"BL","blXml");
			isNull=true;
		}
		
		provider= UrlContainer.getServiceProviderByCode("bcftpblcallback");
		if(provider!=null)
		{
			invokeBcCallBack(file,provider,"BL","blXml");
			isNull=true;
		}
		if(!isNull)
		{
			logger.warn("SI BL回调接口服务提供方为空!");
			addFileRoutingLog(file.getName(),"回调BC接口返回:"+"BC BL回调接口为空,请检查BOSS接口配置<ftpblcallback、bcftpblcallback>");
		}
	}
	
	private void invokeBcCallBack(File file,Provider provider,String name,String contentName)throws IOException
	{
		String xmlData=FileUtils.readFileToString(file);
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("fileName",file.getName());
		params.put(contentName,xmlData);
		params.put("clientKey",provider.getAuthorKey());
		provider.setParamMap(params);
		Record record=esbService.invokeService(provider);
		logger.info(provider.getServerIp()+"  Response "+ record.getMessage());
		
		addFileRoutingLog(file.getName(),"回调BC接口返回:"+record.getMessage());
	}	
	
	private void invokeSiUploadCallBack(Provider provider,String fileName,boolean isUpload)
	{
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("fileName",fileName);
		params.put("clientKey",provider.getAuthorKey());
		int flagId=isUpload?1:0;
		params.put("status",flagId);
		provider.setParamMap(params);
		Record record=esbService.invokeService(provider);
		logger.info(record.getMessage());

		addFileRoutingLog(fileName,"回调BC接口返回:"+record.getMessage());
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
	
	@Override
	public void onDelete(Path path) {
	}

	@Override
	public void onModify(Path path) {
	}

}
