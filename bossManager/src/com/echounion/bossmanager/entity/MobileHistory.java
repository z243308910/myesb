package com.echounion.bossmanager.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.echounion.bossmanager.common.util.ContextUtil;
import com.echounion.bossmanager.service.softserver.ISoftwareService;

/**
 * 手机短信历史记录
 * @author 胡礼波
 * 2012-11-17 下午05:22:06
 */
public class MobileHistory implements Serializable {

	/**
	 * @author 胡礼波
	 * 2012-11-17 下午05:22:17
	 */
	private static final long serialVersionUID = -1915698275628265807L;
	
	public static final int SEND_SUCCESS=1;		//成功
	public static final int SEND_WAITING=0;		//待发送
	public static final int SEND_FAIL=2;		//失败
	
	public static final int MSG_SEND=1;			//发短信
	public static final int MSG_RECIVE=2;		//收短信
	
	private int id;
	private int softId;		//软件编号
	private String phoneNo;	//发送/接收的号码
	private Date activeTime;	//发送/接收的时间
	private int status;		//状态
	private String content;	//发送/接收内容
	private int type;		//短信类别 1发短信 2收短信
	private int errorNum;	//错误次数
	private Date lastSendTime;//上一次发送时间
	private int recStatus;	//收阅状态
	private String btype;			//业务类型
	private String bcode;		//业务代码
	
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
	
	public Date getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getErrorNum() {
		return errorNum;
	}
	public void setErrorNum(int errorNum) {
		this.errorNum = errorNum;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getLastSendTime() {
		return lastSendTime;
	}
	public void setLastSendTime(Date lastSendTime) {
		this.lastSendTime = lastSendTime;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public int getRecStatus() {
		return recStatus;
	}
	public void setRecStatus(int recStatus) {
		this.recStatus = recStatus;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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
