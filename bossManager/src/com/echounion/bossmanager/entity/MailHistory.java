package com.echounion.bossmanager.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.echounion.bossmanager.common.util.ContextUtil;
import com.echounion.bossmanager.service.softserver.ISoftwareService;

/**
 * 邮件发送记录
 * @author 胡礼波
 * 2012-11-15 下午07:06:25
 */
public class MailHistory implements Serializable {

	/**
	 * @author 胡礼波
	 * 2012-11-15 下午07:06:32
	 */
	private static final long serialVersionUID = -8398005666242724600L;
	public static final int SEND_SUCCESS=1;		//成功
	public static final int SEND_WAITING=0;		//待发送
	public static final int SEND_FAIL=2;		//失败
	
	private int id;
	private int softId;	//软件ID
	private String emailAddress;//邮箱地址
	private String tplName;		//模版名称
	private Date sendTime;		//发送时间
	private int errosNum;		//失败次数---用于描述邮件发送失败的次数 系统默认为3次 超过3次则标志为失败的邮件
	private Date lastTime;		//最后一次发送时间
	private String content;		//邮件内容
	private int status;			//状态 0为待发送 1为成功 2为失败
	private String btype;					//业务类型ID
	private String bcode;				//业务代号	
	
	public String getStatusName()
	{
		switch(getStatus())
		{
			case SEND_SUCCESS:
				return "发送成功";
			case SEND_WAITING:
				return "待发送";
			case SEND_FAIL:
				return "发送失败";
		}
		return null;
	}
	
	/**
	 * 获得软件名称
	 * @author 胡礼波
	 * 2012-12-4 上午11:15:26
	 * @return
	 */
	public String getSoftName()
	{
		try
		{
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(ContextUtil.getServletContext());
		ISoftwareService softService=ctx.getBean(ISoftwareService.class);
		EsbSoftWare s=softService.getSoftwareById(getSoftId());
		if(s==null)
		{
			return null;
		}
		return s.getSoftName();
		}catch (Exception e) {
			Assert.isTrue(false,"获取软件名称失败!");
		}
		return null;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public int getErrosNum() {
		return errosNum;
	}
	public void setErrosNum(int errosNum) {
		this.errosNum = errosNum;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public int getSoftId() {
		return softId;
	}
	public void setSoftId(int softId) {
		this.softId = softId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getTplName() {
		return tplName;
	}
	public void setTplName(String tplName) {
		this.tplName = tplName;
	}

	public String getBcode() {
		return bcode;
	}

	public void setBcode(String bcode) {
		this.bcode = bcode;
	}

	public String getBtype() {
		return btype;
	}

	public void setBtype(String btype) {
		this.btype = btype;
	}
}
