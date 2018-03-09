package com.echounion.boss.core.email;

import java.io.Serializable;
import java.util.LinkedList;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import com.echounion.boss.core.cache.PushMailContainer;

/**
 * 邮件发送者配置类
 * @author 胡礼波
 * 2012-11-15 下午04:27:09
 */
public class EmailConfig implements Serializable {
	
	/**
	 * 邮件后缀
	 */
	private static String suffixsFor163[]={"@163.com","@126.com","@vip.163.com"};
	
	/**
	 * @author 胡礼波
	 * 2012-11-15 下午04:28:35
	 */
	private static final long serialVersionUID = 1L;
	
	public static synchronized EmailConfig getConfig(String channel,String recevier,boolean isSpecile)
	{
		if(StringUtils.isBlank(channel) || !PushMailContainer.isExist(channel))			//通道为空或者不存在则选中默认通道
		{
			channel="1000";
		}
		LinkedList<EmailConfig> list=PushMailContainer.getMailContainer();
		EmailConfig config=getConfigBySuffix(list,channel,recevier,isSpecile);
		if(config!=null)		//不存在相同后缀的发件邮箱 则默认选用第一个同通道的邮箱
		{
			return config;
		}
		for (EmailConfig cfg:list) {
			if(channel.equals(cfg.getChannel()))			//每个系统走自己的邮件渠道
			{
				list.remove(cfg);				//从队列中移除该元素
				list.offer(cfg);					//加入到列表末尾
				return cfg;
			}
		}
		return null;
	}
	
	/**
	 * 同种邮箱渠道的发送 比如 163的邮箱 由163的邮箱发送
	 * 每个系统走自己的邮件渠道
	 * @author 胡礼波
	 * 2014-3-5 下午4:44:28
	 * @param list
	 * @param channel
	 * @param suffix
	 * @return
	 */
	private static EmailConfig getConfigBySuffix(LinkedList<EmailConfig> list,String channel,String recevier,boolean isSpecile)
	{
		if(!ArrayUtils.contains(suffixsFor163,getMailSuffix(recevier)))		//不存在特殊处理的收件人
		{
			return null;
		}
		for (EmailConfig config:list) {
			if(channel.equals(config.getChannel()))			//每个系统走自己的邮件渠道
			{
				if(!isSpecile&&!config.getUsername().toLowerCase().endsWith(suffixsFor163[0]))		//不需要对163 126的通道做特殊处理 走其他的通道
				{
					list.remove(config);				//从队列中移除该元素
					list.offer(config);					//加入到列表末尾
					return config;
				}
				if(isSpecile&&config.getUsername().toLowerCase().endsWith(suffixsFor163[0]))
				{
					list.remove(config);				//从队列中移除该元素
					list.offer(config);					//加入到列表末尾
					return config;					
				}
				continue;
			}
		}
		return null;
	}
	
	/**
	 * 邮件后缀
	 * @author 胡礼波
	 * 2014-3-5 下午5:28:45
	 * @param address
	 * @return
	 */
	private static String getMailSuffix(String address)
	{
		int intdex=address.indexOf("@");
		return address.substring(intdex);
	}
	
	/**
	 * 发送服务器IP
	 * @author 胡礼波
	 * 2012-11-15 下午04:27:09
	 * @return
	 */
	private  String host;

	/**
	 * 发送服务器端口
	 * @author 胡礼波
	 * 2012-11-15 下午04:27:09
	 * @return
	 */
	private  Integer port;

	/**
	 * 发送编码
	 * @author 胡礼波
	 * 2012-11-15 下午04:27:09
	 * @return
	 */
	private String encoding="UTF-8";			//默认为UTF-8

	/**
	 *  登录名
	 * @author 胡礼波
	 * 2012-11-15 下午04:27:09
	 * @return
	 */
	private String username;

	/**
	 * 密码
	 * @author 胡礼波
	 * 2012-11-15 下午04:27:09
	 * @return
	 */
	private String password;

	/**
	 * 来源
	 * @author 胡礼波
	 * 2012-11-15 下午04:27:09
	 * @return
	 */
	private String from;
	
	/**
	 * 答复地址
	 * @author 胡礼波
	 * 2012-11-15 下午04:27:09
	 * @return
	 */
	private String reply;			//默认答复地址为发送地址
	
	private String channel;			//通道标志

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getReply() {
		if(null==reply)
		{
			reply=from;
		}
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
