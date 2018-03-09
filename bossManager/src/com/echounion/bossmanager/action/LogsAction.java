package com.echounion.bossmanager.action;

import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.echounion.bossmanager.common.enums.LoginFlag;
import com.echounion.bossmanager.common.json.JsonUtil;
import com.echounion.bossmanager.common.security.annotation.ActionRightCtl;
import com.echounion.bossmanager.entity.SysLogCons;
import com.echounion.bossmanager.service.ILogService;

/**
 * Console系统日志Action
 * @author 胡礼波
 * 2012-11-1 下午01:54:41
 */
@Controller("logsAction")
@Scope("prototype")
public class LogsAction extends BaseAction {

	@Autowired
	private ILogService logService;
	/**
	 * @author 胡礼波
	 * 2012-11-1 下午01:55:00
	 */
	private static final long serialVersionUID = -5745478862052282258L;

	@Override
	public String execute() throws Exception {
		return getLogsList();
	}
	
	/**
	 * 获取系统日志信息
	 * @author 胡礼波
	 * 2012-11-1 下午01:55:32
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	public String getLogsList()
	{
		instantPage();				//实例化分页
		List<SysLogCons> logsList=logService.getAllLog();
		int total=logService.getCountLog();
		JSONObject jsonObject=JsonUtil.toJsonStringFilterPropter(logsList, total);
		setJsonData(jsonObject);
		return JSON;
	}
	
	/**
	 * 删除系统日志
	 * @author 胡礼波
	 * 2012-11-1 下午02:02:39
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	public String delLogs()
	{
		String dataStr=getServletReqeust().getParameter("data");
		String datas[]=StringUtils.split(dataStr,",");
		Integer[] ids=(Integer[])ConvertUtils.convert(datas,Integer.class);
		int count=logService.delLog(ids);
		writeData(count);
		return null;
	}
	
	/**
	 * 导出日志
	 * @author 胡礼波
	 * 2013-2-4 下午03:57:40
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	public String exportLogs()
	{
		String path=getServletSession().getServletContext().getRealPath("/WEB-INF/report/export");
		int count=logService.exportLogsToExcel(path);
		writeData(count+"条数据成功导出!");
		return null;
	}
}
