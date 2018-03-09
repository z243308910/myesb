package com.echounion.bossmanager.action.softserver;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.echounion.bossmanager.action.BaseAction;
import com.echounion.bossmanager.common.enums.LoginFlag;
import com.echounion.bossmanager.common.json.JsonUtil;
import com.echounion.bossmanager.common.security.annotation.ActionRightCtl;
import com.echounion.bossmanager.common.util.DataConvertUtils;
import com.echounion.bossmanager.common.util.DateUtil;
import com.echounion.bossmanager.entity.EsbApiLog;
import com.echounion.bossmanager.service.softserver.IApiLogService;

@Controller("ApiLogAction")
@Scope("prototype")
public class ApiLogAction extends BaseAction{
	
	private static final long serialVersionUID = -7794256930426366423L;
	
	@Autowired
	IApiLogService apiLogService;
	
	public String execute()
	{
		return getApiLogList();
	}
	
	public String getApiLogList()
	{
		instantPage();
		JSONObject data=JsonUtil.toJsonStringFilterPropter(apiLogService.getAll(), apiLogService.getAllCount());
		setJsonData(data);
		return JSON;
	}
	
	@ActionRightCtl(login=LoginFlag.YES)
	public String delLogs()
	{
		String dataStr=getServletReqeust().getParameter("data");
		String datas[]=StringUtils.split(dataStr,",");
		Integer[] ids=(Integer[])ConvertUtils.convert(datas,Integer.class);
		int count=apiLogService.delLogs(ids);
		writeData(count);
		return null;
	}
	
	@ActionRightCtl(login=LoginFlag.YES)
	public String viewSoftware()
	{
		int apiId=DataConvertUtils.convertInteger(getServletReqeust().getParameter("apiId"));
		EsbApiLog apiLog = apiLogService.getUniqueById(apiId);
		if(apiLog!=null)
		{
			getRequest().put("apilog",apiLog);
		}
		return "apilogs_view";
	}
	
	public String searchApiLogList()
	{
		instantPage();
		Integer softId = DataConvertUtils.convertInteger(getServletReqeust().getParameter("softId"));
		Integer serviceId = DataConvertUtils.convertInteger(getServletReqeust().getParameter("serviceId"));
		Date effectDate = DateUtil.parseStrDate(getServletReqeust().getParameter("effectDate"));
		Date expireDate = DateUtil.parseStrDate(getServletReqeust().getParameter("expireDate"));
		List<EsbApiLog> logs =apiLogService.getApiLogs(softId, serviceId, effectDate, expireDate);
		int total = apiLogService.getApiLogCount(softId, serviceId, effectDate, expireDate);
		JSONObject data=JsonUtil.toJsonStringFilterPropter(logs, total);
		setJsonData(data);
		return JSON;
	}
}
