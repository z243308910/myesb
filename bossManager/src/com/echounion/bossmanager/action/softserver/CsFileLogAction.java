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
import com.echounion.bossmanager.common.util.DateUtil;
import com.echounion.bossmanager.entity.CargosmartFileRoutingLog;
import com.echounion.bossmanager.service.softserver.ICsFileLogService;

@Controller("CsFileLogAction")
@Scope("prototype")
public class CsFileLogAction extends BaseAction {
	
	private static final long serialVersionUID = -2219616613012603514L;
	
	@Autowired
	ICsFileLogService csFileLogService;
	
	public String execute()
	{
		return getCsFileLogList();
	}
	
	public String getCsFileLogList()
	{
		instantPage();
		JSONObject data=JsonUtil.toJsonStringFilterPropter(csFileLogService.getAll(), csFileLogService.getAllCount());
		setJsonData(data);
		return JSON;
	}
	
	@ActionRightCtl(login=LoginFlag.YES)
	public String delLogs()
	{
		String dataStr=getServletReqeust().getParameter("data");
		String datas[]=StringUtils.split(dataStr,",");
		Integer[] ids=(Integer[])ConvertUtils.convert(datas,Integer.class);
		int count=csFileLogService.delLogs(ids);
		writeData(count);
		return null;
	}
	
	public String searchCsFileLogList()
	{
		instantPage();
		String fileName = getServletReqeust().getParameter("softId");
		Date effectDate = DateUtil.parseStrDate(getServletReqeust().getParameter("effectDate"));
		Date expireDate = DateUtil.parseStrDate(getServletReqeust().getParameter("expireDate"));
		List<CargosmartFileRoutingLog> logs = csFileLogService.getCsFileLogs(fileName, effectDate, expireDate);
		int total = csFileLogService.getCsFileLogCount(fileName, effectDate, expireDate);
		JSONObject data=JsonUtil.toJsonStringFilterPropter(logs, total);
		setJsonData(data);
		return JSON;
	}
	
}
