package com.echounion.boss.core.ftp;

/**
 * CS FTP配置
 * @author 胡礼波
 * 2013-10-14 下午1:58:13
 */
public class FtpConfig {

	private String url;		//FTP地址
	private int port;		//FTP端口
	private String userName;//FTP用户名
	private String password;//FTP密码
	private String charset;	//FTP编码
	private boolean passive=true;//是否被动连接 默认为被动连接
	
	public boolean isPassive() {
		return passive;
	}
	public void setPassive(boolean passive) {
		this.passive = passive;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
