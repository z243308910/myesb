package com.echounion.boss.entity;

import java.io.Serializable;
import java.util.Date;

import com.echounion.boss.common.constant.Constant;

/**
 * 数据采集历史记录
 * 
 * @author 胡礼波 2012-11-5 上午09:57:08
 */
public class TrackingHistory implements Serializable {

	/**
	 * @author 胡礼波 2012-11-5 上午10:42:30
	 */
	private static final long serialVersionUID = 566626927541483767L;

	private int id;

	private int softId; // 软件ID

	private String sourceCode; // 采集来源代号 这个代号来自系统配置中的采集器配置

	private String url; // 请求地址

	private String content; // 采集内容

	private Date time; // 采集时间

	private int status; // 采集状态

	private int type = Constant.JSON_TYPE; // 结果类型---默认为JSON类型

	public TrackingHistory(){}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSoftId() {
		return softId;
	}

	public void setSoftId(int softId) {
		this.softId = softId;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
