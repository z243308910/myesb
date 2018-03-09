package com.echounion.boss.core.rtx;

public class RtxConfig {

	private static String serverUrl;	//RTX服务器URL
	
	public RtxConfig(){};
	
	public RtxConfig(String serverUrl)
	{
		RtxConfig.serverUrl=serverUrl;
	};
	
	public RtxConfig(String host,int port)
	{
		serverUrl="http://"+host+":"+port+"/";
	}
	
	/**
	 * 获得RTX Http地址
	 * @author 胡礼波
	 * 2013-3-17 下午2:21:49
	 * @return
	 */
	public static String getRtxServerUlr()
	{
		return serverUrl;
	}
}
