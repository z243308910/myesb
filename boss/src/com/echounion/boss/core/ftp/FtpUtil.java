package com.echounion.boss.core.ftp;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * FTP工具类
 * @author 胡礼波
 * 2013-10-14 下午2:02:02
 */
@Component
public class FtpUtil {

	private static Logger logger = Logger.getLogger(FtpUtil.class);
	
	/**
	 * 获得FTP连接客户端
	 * @author 胡礼波
	 * 2013-10-14 下午2:10:21
	 * @param config
	 * @return
	 * @throws IOException
	 */
	public static FTPClient getFtpClient(FtpConfig config) throws IOException
	{
		FTPClient ftp = new FTPClient();
		FTPClientConfig clientConfig=new FTPClientConfig(FTPClientConfig.SYST_UNIX);
		clientConfig.setServerLanguageCode("zh");
		ftp.configure(clientConfig);
		ftp.connect(config.getUrl(),config.getPort());
		
		ftp.enterLocalPassiveMode();
		ftp.setFileType(FTP.BINARY_FILE_TYPE);
		ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
		
		ftp.login(config.getUserName(),config.getPassword());

		ftp.setControlEncoding(config.getCharset());
		ftp.setCharset(Charset.forName(config.getCharset()));
		logger.info(ftp.getReplyString());
		int reply=ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
	    	logger.error("FTP server refused connection.");
	        ftp.disconnect();
	    }
		return ftp;
	}
	
	/**
	 * 关闭FTP连接
	 * @author 胡礼波
	 * 2013-10-14 下午2:10:30
	 * @param ftp
	 */
	public static void closeFtp(FTPClient ftp)
	{
		if(ftp!=null)
		{
			try {
				ftp.logout();
				logger.info(ftp.getReplyString());
				ftp.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
