package com.echounion.bossmanager.action.tracking;

import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.echounion.bossmanager.action.BaseAction;
import com.echounion.bossmanager.common.constant.Constant;
import com.echounion.bossmanager.common.enums.LoginFlag;
import com.echounion.bossmanager.common.json.JsonUtil;
import com.echounion.bossmanager.common.security.annotation.ActionRightCtl;
import com.echounion.bossmanager.entity.SysConfig;
import com.echounion.bossmanager.entity.TrackingHistory;
import com.echounion.bossmanager.service.ISysConfigService;
import com.echounion.bossmanager.service.tracking.ITrackingService;

/**
 * 采集日志Action
 * @author 胡礼波
 * 2012-11-5 下午12:01:17
 */
@Controller("trackingAction")
@Scope("prototype")
public class TrackingAction extends BaseAction {

	/**
	 * @author 胡礼波
	 * 2012-11-5 下午12:01:48
	 */
	private static final long serialVersionUID = 3989195444711151015L;
	private Logger logger = Logger.getLogger(TrackingAction.class);
	@Autowired
	private ITrackingService trackingService;
	@Autowired
	private ISysConfigService sysConfigService;
	
	private SysConfig config;
	
	public SysConfig getConfig() {
		return config;
	}

	public void setConfig(SysConfig config) {
		this.config = config;
	}

	@ActionRightCtl(login=LoginFlag.YES)
	public String execute()
	{
		return getTrackingList();
	}

	/**
	 * 采集日志列表
	 * @author 胡礼波
	 * 2012-11-5 下午12:02:52
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	public String getTrackingList()
	{
		instantPage();
		List<TrackingHistory> list=trackingService.getTrackingHistory();
		int total=trackingService.getCount();
		JSONObject jsonData=JsonUtil.toJsonStringFilterPropter(list, total);
		setJsonData(jsonData);
		return JSON;
	}
	
	/**
	 * 删除采集记录
	 * @author 胡礼波
	 * 2012-11-5 下午01:11:48
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	public String delTracking()
	{
		String dataStr=getServletReqeust().getParameter("data");
		String[] datas=StringUtils.split(dataStr, ",");
		Integer ids[]=(Integer[])ConvertUtils.convert(datas, Integer.class);
		int count=trackingService.delTrackingHistory(ids);
		writeData(count);
		return null;
	}
	
	/**
	 * 获得采集器集合
	 * @author 胡礼波
	 * 2012-11-6 上午09:57:38
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	public String getTracktor()
	{
		List<SysConfig> tracktorList=sysConfigService.getSysConfigByType(Constant.SYS_TRACKING);
		int total=sysConfigService.getCount(Constant.SYS_TRACKING);
		JSONObject jsonData=JsonUtil.toJsonStringFilterPropter(tracktorList, total);
		setJsonData(jsonData);
		return JSON;
	}
	
	/**
	 * 查看采集器信息
	 * @author 张霖
	 * 2013-1-31下午12:00:25
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	public String viewTracktor()
	{
		int id = (Integer)ConvertUtils.convert(getServletReqeust().getParameter("id"),Integer.class);
		SysConfig config = sysConfigService.getSysConfigById(id);
		getRequest().put("config", config);
		return "tracktor_view";
	}
	
	/**
	 * 编辑采集器信息
	 * @author 张霖
	 * 2013-1-31下午2:17:11
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	public String editTracktor()
	{
		int result = 0;
		try
		{
			result = sysConfigService.editSysConfig(config);
		}
		catch (Exception e)
		{
			logger.error("编辑采集信息出错"+e);
		}
		writeData(result>0?Constant.OP_SUCCESS:Constant.OP_FAIL);
		return null;
	}
	
	/**
	 * 检查采集器是否已存在
	 * @author 张霖
	 * 2013-1-31下午6:38:25
	 * @return
	 */
	public String checkExist()
	{
		boolean flag = false;
		String configCode = "Track_"+getServletReqeust().getParameter("code");
		SysConfig sysConfig = sysConfigService.getSysConfig(configCode, Constant.SYS_TRACKING);
		if(sysConfig != null)
		{
			flag = true;
		}
		writeData(flag);
		return null;
	}
}
