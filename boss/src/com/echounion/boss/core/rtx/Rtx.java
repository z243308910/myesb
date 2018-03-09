package com.echounion.boss.core.rtx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * RTX消息实体类
 * @author 胡礼波
 * 2013-7-22 下午3:47:38
 */
public class Rtx implements Serializable {

	/**
	 * @author 胡礼波
	 * 2013-7-22 下午3:47:51
	 */
	private static final long serialVersionUID = 4723392489106989985L;

	private int historyId;
	private String receiver;		//接收方
	private String title;			//消息标题
	private String content;			//消息内容
	private String btype;				//业务类型ID
	private String bcode;				//业务代号
	private String phone;			//手机号码
	
	public int getHistoryId() {
		return historyId;
	}

	public void setHistoryId(int historyId) {
		this.historyId = historyId;
	}
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<String> getReceiverList()
	{
		List<String> list=null;
		if(!StringUtils.isBlank(this.receiver))
		{
			list=new ArrayList<String>();
			String receivers[]=receiver.split(",");
			for (String receiver : receivers) {
				list.add(receiver.trim());
			}
		}
		return list;
	}
	
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getBtype() {
		return btype;
	}
	public void setBtype(String btype) {
		this.btype = btype;
	}
	public String getBcode() {
		return bcode;
	}
	public void setBcode(String bcode) {
		this.bcode = bcode;
	}
	
}
