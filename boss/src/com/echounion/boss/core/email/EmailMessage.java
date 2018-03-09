package com.echounion.boss.core.email;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.echounion.boss.common.util.ValidateUtil;

/**
 * Emai消息实体类
 * @author 胡礼波
 * 2012-11-15 下午04:27:09
 */
public class EmailMessage implements Serializable {

	/**
	 * @author 胡礼波
	 * 2012-11-17 上午10:30:06
	 */
	private static final long serialVersionUID = -3287115723554186058L;

	/**
	 * 邮件ID
	 * @author 胡礼波
	 * 2012-11-15 下午04:27:09
	 */	
	private int historyId;
	
	/**
	 * 主题
	 * @author 胡礼波
	 * 2012-11-15 下午04:27:09
	 */
	private String subject;
	
	/**
	 * 优先级
	 * @author 胡礼波
	 * 2012-11-15 下午04:27:09
	 */
	private String priority="3";    //1:紧急 3:普通 5:缓慢
	
	/**
	 * 收件人 为邮件地址可以为多个 用"，"隔开
	 * @author 胡礼波
	 * 2012-11-15 下午04:27:09
	 */
	private String receiver;
	
	/**
	 * 邮件回复地址
	 * @author 胡礼波
	 * 2012-11-15 下午04:27:09
	 */
	private String reply;		//邮件回复地址 默认为发件人地址--可指定
	
	/**
	 * 抄送地址
	 * @author 胡礼波
	 * 2012-11-15 下午04:27:09
	 */
	private String cc;			//邮件抄送地址--可指定多个逗号隔开

	/**
	 * 内容
	 * @author 胡礼波
	 * 2012-11-15 下午04:27:09
	 */
	private String content; 
	
	/**
	 * 模版名称
	 * @author 胡礼波
	 * 2012-11-15 下午04:27:09
	 */
	private String tpl;				//模版名称
	
	private String linkAttached;	//附件地址--可指定多个逗号隔开
	
	private String channel;			//通道名称---可能不同系统使用不同的邮件通道
	
	private boolean split=true;			//标志是否拆分一个个发送 默认为true 一个个发送
	
	private String personal;		//发件人名称
	
	private String btype;			//业务类型
	private String bcode;			//业务代号	

	
	public String getChannel() {
		return channel;
	}


	public String getPersonal() {
		return personal;
	}

	public boolean isSplit() {
		return split;
	}


	public void setSplit(boolean split) {
		this.split = split;
	}


	public void setPersonal(String personal) {
		this.personal = personal;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getLinkAttached() {
		return linkAttached;
	}

	public void setLinkAttached(String linkAttached) {
		this.linkAttached = linkAttached;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getTpl() {
		return tpl;
	}

	public void setTpl(String tpl) {
		this.tpl = tpl;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		if(!StringUtils.isBlank(reply))
		{
			if(!ValidateUtil.isEmail(reply))
			{
			return;
			}
		}
		this.reply = reply;
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

	public void setBcode(String code) {
		this.bcode = code;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getSubject() {
		if(subject==null)
		{
			subject="无主题";
		}
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public int getHistoryId() {
		return historyId;
	}

	public void setHistoryId(int historyId) {
		this.historyId = historyId;
	}

	/**
	 * 收件人列表集合
	 * @author 胡礼波
	 * 2013-7-24 下午4:39:30
	 * @return
	 */
	public List<String> getReciList() {
		List<String> list=null;
		if(this.receiver!=null)
		{
			list=new ArrayList<String>();
			String reces[]=receiver.split(",");
			for (String address : reces) {
				if(!ValidateUtil.isEmail(address))
				{
					continue;
				}
				list.add(address);
			}
		}
		return list;
	}
	
	/**
	 * 附件地址列表
	 * @author 胡礼波
	 * 2013-7-24 下午6:00:44
	 * @return
	 */
	public List<String> getAttachment()
	{
		List<String> list=null;
		if(this.linkAttached!=null)
		{
			list=new ArrayList<String>();
			String url[]=linkAttached.split(",");
			for (String address : url) {
				if(StringUtils.isBlank(address))
				{
					continue;
				}
				list.add(address.trim());
			}
		}
		return list;
	}
	
	/**
	 * 抄送人列表
	 * @author 胡礼波
	 * 2013-7-24 下午5:28:56
	 * @return
	 */
	public List<String> getCcList()
	{
		List<String> list=null;
		if(this.cc!=null)
		{
			list=new ArrayList<String>();
			String reces[]=cc.split(",");
			for (String address : reces) {
				if(!ValidateUtil.isEmail(address))
				{
					continue;
				}
				list.add(address.trim());
			}
		}
		return list;
	}
}
