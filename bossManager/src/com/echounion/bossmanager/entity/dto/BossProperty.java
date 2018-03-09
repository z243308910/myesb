package com.echounion.bossmanager.entity.dto;

import java.io.Serializable;
import java.util.Properties;

import org.apache.log4j.Logger;
import com.echounion.bossmanager.core.SystemLoaderListener;

/**
 * BOSS服务器相关信息
 * @author 胡礼波
 * 2012-11-21 上午11:08:45
 */
public class BossProperty implements Serializable {

	/**
	 * @author 胡礼波
	 * 2012-11-28 上午09:36:34
	 */
	private static final long serialVersionUID = 768220660495001595L;

	private static Logger log = Logger.getLogger(BossProperty.class);
	
	private String mailTestUrl;			//邮件测试地址
	private String mailResendUrl;		//邮件重发
	
	private String shortMsgTestUrl;		//短信测试地址
	private String shortMsgResendUrl;	//短信重发
	private String rtxTestUrl;			//RTX测试地址
	
	private String bossLoginUrl;			//BOSS系统登录
	private String mailTplUrl;			//邮件模版列表接口
	private String delMailTplUrl;		//删除邮件模版接口地址
	private String uplMailTplUrl;		//上传邮件模版
	
	private String addByPerTask;			//添加系统任务
	private String delTask;				//删除系统任务
	private String pauseTask;			//暂停系统任务
	private String resumeTask;			//恢复系统任务
	private String backUpTask;			//系统备份
	private String backUpList;			//备份数据列表
	private String delBackUp;			//删除备份数据
	
	private String authorKey;			//企业的授权码 是eid,sid,did,securityKey一起加密而成
	private String clientKey;			//系统之间用的SessionId
	
	public static BossProperty bossProperty=null;
	
	public String getRtxTestUrl() {
		return rtxTestUrl;
	}

	public void setRtxTestUrl(String rtxTestUrl) {
		this.rtxTestUrl = rtxTestUrl;
	}
	public String getMailTplUrl() {
		return mailTplUrl;
	}

	public void setMailTplUrl(String mailTplUrl) {
		this.mailTplUrl = mailTplUrl;
	}

	private BossProperty(){}
	
	public String getMailResendUrl() {
		return mailResendUrl;
	}

	public void setMailResendUrl(String mailResendUrl) {
		this.mailResendUrl = mailResendUrl;
	}

	public String getShortMsgResendUrl() {
		return shortMsgResendUrl;
	}

	public void setShortMsgResendUrl(String shortMsgResendUrl) {
		this.shortMsgResendUrl = shortMsgResendUrl;
	}

	public synchronized static BossProperty getInstance()
	{
		if(bossProperty==null)
		{
			bossProperty=new BossProperty();
		}
		return bossProperty;
	}
	
	/**
	 * 初始化Memcached服务器配置
	 * @author 胡礼波
	 * 2012-11-28 上午09:34:20
	 */
	public static void initBoss()
	{
		Properties p = SystemLoaderListener.loadFile("system.properties");
		BossProperty.getInstance().setMailTestUrl(p.getProperty("boss.email.url.test"));
		BossProperty.getInstance().setShortMsgTestUrl(p.getProperty("boss.shortmsg.url.test"));
		BossProperty.getInstance().setMailResendUrl(p.getProperty("boss.email.url.resend"));
		BossProperty.getInstance().setShortMsgResendUrl(p.getProperty("boss.shortmsg.url.resend"));
		BossProperty.getInstance().setBossLoginUrl(p.getProperty("boss.login.url"));
		BossProperty.getInstance().setAuthorKey(p.getProperty("boss.service.authorKey"));
		BossProperty.getInstance().setMailTplUrl(p.getProperty("boss.mailtpl.url.list"));
		BossProperty.getInstance().setDelMailTplUrl(p.getProperty("boss.mailtpl.url.del"));
		BossProperty.getInstance().setUplMailTplUrl(p.getProperty("boss.mailtpl.url.upload"));
		BossProperty.getInstance().setAddByPerTask(p.getProperty("boss.task.url.addbyper"));
		BossProperty.getInstance().setDelTask(p.getProperty("boss.task.url.del"));
		BossProperty.getInstance().setPauseTask(p.getProperty("boss.task.url.pause"));
		BossProperty.getInstance().setResumeTask(p.getProperty("boss.task.url.resume"));
		BossProperty.getInstance().setBackUpTask(p.getProperty("boss.task.url.backup"));
		BossProperty.getInstance().setBackUpList(p.getProperty("boss.task.url.backuplist"));
		BossProperty.getInstance().setDelBackUp(p.getProperty("boss.task.url.delbackup"));
		BossProperty.getInstance().setRtxTestUrl(p.getProperty("boss.rtx.url.text"));
		log.info("....................BOSS接口调用配置成功....................");
		
	}
	
	
	public String getMailTestUrl() {
		return mailTestUrl;
	}
	public void setMailTestUrl(String mailTestUrl) {
		this.mailTestUrl = mailTestUrl;
	}
	public String getShortMsgTestUrl() {
		return shortMsgTestUrl;
	}
	public void setShortMsgTestUrl(String shortMsgTestUrl) {
		this.shortMsgTestUrl = shortMsgTestUrl;
	}


	public String getBossLoginUrl() {
		return bossLoginUrl;
	}

	public void setBossLoginUrl(String bossLoginUrl) {
		this.bossLoginUrl = bossLoginUrl;
	}

	public String getAuthorKey() {
		return authorKey;
	}

	public void setAuthorKey(String authorKey) {
		this.authorKey = authorKey;
	}

	public String getDelMailTplUrl() {
		return delMailTplUrl;
	}

	public void setDelMailTplUrl(String delMailTplUrl) {
		this.delMailTplUrl = delMailTplUrl;
	}

	public String getUplMailTplUrl() {
		return uplMailTplUrl;
	}

	public void setUplMailTplUrl(String uplMailTplUrl) {
		this.uplMailTplUrl = uplMailTplUrl;
	}

	public String getClientKey() {
		return clientKey;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}

	public String getAddByPerTask() {
		return addByPerTask;
	}

	public void setAddByPerTask(String addByPerTask) {
		this.addByPerTask = addByPerTask;
	}

	public String getDelTask() {
		return delTask;
	}

	public void setDelTask(String delTask) {
		this.delTask = delTask;
	}

	public String getPauseTask() {
		return pauseTask;
	}

	public void setPauseTask(String pauseTask) {
		this.pauseTask = pauseTask;
	}

	public String getResumeTask() {
		return resumeTask;
	}

	public void setResumeTask(String resumeTask) {
		this.resumeTask = resumeTask;
	}

	public String getBackUpTask() {
		return backUpTask;
	}

	public void setBackUpTask(String backUpTask) {
		this.backUpTask = backUpTask;
	}

	public String getBackUpList() {
		return backUpList;
	}

	public void setBackUpList(String backUpList) {
		this.backUpList = backUpList;
	}

	public String getDelBackUp() {
		return delBackUp;
	}

	public void setDelBackUp(String delBackUp) {
		this.delBackUp = delBackUp;
	}
}
