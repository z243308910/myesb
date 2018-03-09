package com.echounion.boss.core.shortmsg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.echounion.boss.common.util.ValidateUtil;

/**
 * 短信实体类
 * @author 胡礼波
 * 2012-11-17 下午05:38:26
 */
public class ShortMsg implements Serializable{

	/**
	 * @author 胡礼波
	 * 2012-11-17 下午05:38:34
	 */
	private static final long serialVersionUID = -6013864219504007234L;
	
	/**
	 * 短信ID
	 * @author 胡礼波
	 * 2012-11-15 下午04:27:09
	 */	
	private int historyId;
	
	private String mobile;	//号码 --------该字段用户内部手机发送，在多用户地址的情况下拆分为一条条短信发送
	private String content;	//内容
	private Date sendTime;	//发送时间
	private String btype;		//业务类型
	private String bcode;	//业务代号
	private String channel;			//通道名称---可能不同系统使用不同的短信通道
	public String getBtype() {
		return btype;
	}
	public void setBtype(String btype) {
		this.btype = btype;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getBcode() {
		return bcode;
	}
	public void setBcode(String bcode) {
		this.bcode = bcode;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		if(StringUtils.isEmpty(content))
		{
			content="_";
		}
		this.content = content;
	}
	public int getHistoryId() {
		return historyId;
	}
	public void setHistoryId(int historyId) {
		this.historyId = historyId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public List<String> getMobiles() {
		List<String> list=null;
		if(!StringUtils.isBlank(this.mobile))
		{
			list=new ArrayList<String>();
			String mobiles[]=mobile.split(",");
			for (String mobile: mobiles) {
				if(ValidateUtil.isInteger(mobile))
				{
				list.add(mobile.trim());
				}
			}
		}
		return list;
	}
}
