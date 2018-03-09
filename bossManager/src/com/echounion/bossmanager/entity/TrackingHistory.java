package com.echounion.bossmanager.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.echounion.bossmanager.common.constant.Constant;
import com.echounion.bossmanager.common.util.ContextUtil;
import com.echounion.bossmanager.service.softserver.ISoftwareService;

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


	/**
	 * 获得状态名称
	 * @author 胡礼波
	 * 2012-12-28 下午03:17:10
	 * @return
	 */
	public String getStatusName()
	{
		switch(getStatus())
		{
			case 1:
				return "成功";
			case -1:
				return "失败";
			default:
				return "";
		}
	}
	
	/**
	 * 获得结果类型
	 * @author 胡礼波
	 * 2012-12-28 下午03:21:47
	 * @return
	 */
	public String getTypeName()
	{
		switch(getType())
		{
			case 1:
				return "JSON";
			default:
				return "";
		}
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
