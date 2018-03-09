package com.echounion.bossmanager.entity.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 邮件模版DTO
 * @author 胡礼波
 * 2012-11-22 下午12:55:10
 */
public class EmailTplDTO implements Serializable{

	/**
	 * @author 胡礼波
	 * 2012-11-22 下午12:56:50
	 */
	private static final long serialVersionUID = 9116008919993336193L;
	private String fileName;		//模版名称
	private String filePath;		//模版路径
	private Date lastDate;			//上次修改时间
	private String content;			//模版内容
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Date getLastDate() {
		return lastDate;
	}
	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}
}
