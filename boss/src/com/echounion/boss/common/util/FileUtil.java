package com.echounion.boss.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import com.echounion.boss.common.constant.Constant;
import com.echounion.boss.core.SystemLoaderListener;


/**
 * 文件工具类
 * 
 * @author 胡礼波 2012-11-22 下午02:22:45
 */
public class FileUtil {

	private static Logger logger=Logger.getLogger(FileUtil.class);
	
	/**
	 * 删除文件或文件夹
	 * @author 胡礼波
	 * 2012-11-22 下午02:24:51
	 * @param path
	 * @return
	 */
	public static boolean delFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			if (file.isFile()) {
				return file.delete();
			} else {
				try {
					FileUtils.deleteDirectory(file);
				} catch (IOException e) {
					logger.warn("删除文件夹失败!"+e);
					return false;
				}
				return true;
			}
		} else {
			logger.warn("要删除的文件不存在!");
			return false;
		}
	}
	
	/**
	 * 上传文件
	 * @author 胡礼波
	 * 2012-11-22 下午02:33:49
	 * @param file
	 * @param des
	 * @return
	 */
	public static boolean storeFile(File file,String des)
	{
		File tempFile=new File(des,file.getName());
		try {
			FileUtils.copyFile(file, tempFile);
			logger.info("上传文件成功!");
			return true;
		} catch (IOException e) {
			logger.info("上传文件失败!"+e);
			return false;
		}
	}
	
	/**
	 * 对文件路径转码
	 * @author 胡礼波
	 * 2012-11-22 下午02:26:57
	 * @param path
	 * @return
	 */
	public static String decodeUrl(String path)
	{
		try {
			return URLDecoder.decode(path,Constant.ENCODEING_UTF8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 创建文件
	 * @author 胡礼波
	 * 2012-11-24 下午02:32:59
	 * @param path
	 */
	public static void createFile(String path)
	{
		File file=new File(path);
		if(!file.exists())
		{
			file.mkdirs();
		}
	}
	
	/**
	 * 判断是否是windows服务器
	 * @author 胡礼波
	 * Nov 27, 2012 5:08:55 PM
	 * @return
	 */
	public static boolean isWindows()
	{
		if(System.getProperty("os.name").toLowerCase().indexOf("win")!=-1)
		{
			return true;		
		}
		return false;
	}
	
	/**
	 * 读取属性文件
	 * @author 胡礼波
	 * 2013-7-30 下午4:06:22
	 * @param filename
	 * @return
	 */
	public static Properties getPropertiesFile(String filename) {
		Properties prop = null;
		try {
			InputStream inputStream = SystemLoaderListener.class.getClassLoader().getResourceAsStream(filename);
			prop = new Properties();
			prop.load(inputStream);
			inputStream.close();
		} catch (Exception e) {
			logger.error("不能够读取配置文件 " + filename);
			throw new RuntimeException("can not read config file " + filename, e);
		}
		return prop;
	}
	
	/**
	 * 文件下载到服务器临时目录
	 * @author 胡礼波
	 * 2013-7-30 下午4:08:38
	 * @param url
	 * @return
	 */
	public static File downloadFile(String httpUrl)
	{
		URLConnection conn=null;
		try {
			URL url=new URL(httpUrl);
			conn = url.openConnection();
		} catch (Exception e1) {
			logger.error("文件下载出错!");
		}

		String path=System.getProperty("java.io.tmpdir")+File.separator+getFileName(httpUrl);
		File file =new File(path);
		try(InputStream input=conn.getInputStream();FileOutputStream output=new FileOutputStream(file))
		{
			byte[] bytes=new byte[1024];
			int length=0;
			while((length=input.read(bytes))!=-1)
			{
				output.write(bytes,0,length);
			}
			output.flush();
		}catch (Exception e) {
			logger.error("文件下载出错!",e);
		}
		return file;
	}
	
	/**
	 * 获得文件名称
	 * @author 胡礼波
	 * 2013-7-30 下午6:45:32
	 * @param httpUrl
	 * @return
	 */
	public static String getFileName(String httpUrl)
	{
		int index=httpUrl.lastIndexOf("/");
		String fileName=httpUrl.substring(index+1);
		return fileName;
	}
}
