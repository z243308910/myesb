package com.echounion.bossmanager.common.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.echounion.bossmanager.common.constant.Constant;


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
}
