package com.echounion.bossmanager.action.softserver;

import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.echounion.bossmanager.action.BaseAction;
import com.echounion.bossmanager.common.constant.Constant;
import com.echounion.bossmanager.common.enums.LoginFlag;
import com.echounion.bossmanager.common.json.JsonUtil;
import com.echounion.bossmanager.common.security.annotation.ActionRightCtl;
import com.echounion.bossmanager.common.util.DataConvertUtils;
import com.echounion.bossmanager.common.util.StringUtil;
import com.echounion.bossmanager.entity.EsbServer;
import com.echounion.bossmanager.entity.EsbSoftWare;
import com.echounion.bossmanager.service.softserver.IServerService;
import com.echounion.bossmanager.service.softserver.ISoftwareService;
/**
 * 软件Action
 * @author 胡礼波
 * 2012-11-7 上午09:27:44
 */
@Controller("softwareAction")
@Scope("prototype")
public class SoftwareAction extends BaseAction {

	/**
	 * @author 胡礼波
	 * 2012-11-7 上午09:27:53
	 */
	private static final long serialVersionUID = 5059040102527596983L;
	private Logger logger=Logger.getLogger(SoftwareAction.class);

	@Autowired
	private ISoftwareService softwareService;
	
	@Autowired
	private IServerService serverVice;

	private EsbSoftWare software;
	
	public EsbSoftWare getSoftware() {
		return software;
	}

	public void setSoftware(EsbSoftWare software) {
		this.software = software;
	}

	@ActionRightCtl(login=LoginFlag.YES)
	public String execute()
	{
		return getSoftwareList();
	}
	
	/**
	 * 获得未绑定服务器的软件列表
	 * @author 胡礼波
	 * 2013-7-16 上午11:32:50
	 * @return
	 */
	public String getUnBindSoftWareList()
	{
		instantPage();
		List<EsbSoftWare> list=softwareService.getSoftwareByServerId(0);
		int total=softwareService.getServerSoftCount(0);
		JSONObject jsonData=JsonUtil.toJsonStringFilterPropter(list, total);
		setJsonData(jsonData);
		return JSON;
	}
	
	/**
	 * 软件列表
	 * @author 胡礼波
	 * 2012-11-7 上午09:30:10
	 * @return
	 */
	public String getSoftwareList()
	{
		instantPage();
		List<EsbSoftWare> list=softwareService.getSoftwareList();
		int total=softwareService.getCount();
		JSONObject jsonData=JsonUtil.toJsonStringFilterPropter(list, total);
		setJsonData(jsonData);
		return JSON;
	}
	
	/**
	 * 根据服务器ID查询该服务器上部署了多少软件信息
	 * @author 胡礼波
	 * 2012-11-9 上午10:38:04
	 * @return
	 */
	public String getSoftwareByServerId()
	{
		int serverId=(Integer)ConvertUtils.convert(getServletReqeust().getParameter("svId"),Integer.class);
		List<EsbSoftWare> list=softwareService.getSoftwareByServerId(serverId);
		int total=softwareService.getServerSoftCount(serverId);
		JSONObject jsonData=JsonUtil.toJsonStringFilterPropter(list, total);
		setJsonData(jsonData);
		return JSON;
	}
	
	/**
	 * 根据软件ID查看软件信息
	 *@author 张霖
	 * 2013-7-9 下午5:12:45
	 * @return
	 */
	public String getSoftwareByIds()
	{
		String str=String.valueOf(getServletReqeust().getParameter("softIds"));
		String dataStr[]=str.split(",");
		Integer[] ids=(Integer[])DataConvertUtils.convertIntegerByString(dataStr);
		instantPage();
		List<EsbSoftWare> list=softwareService.getSoftwareList(ids);
		int total=softwareService.getCount();
		JSONObject jsonData=JsonUtil.toJsonStringFilterPropter(list, total);
		setJsonData(jsonData);
		return JSON;
	}
	
	/**
	 * 添加软件---服务器添加软件用到
	 * @author 胡礼波
	 * 2012-11-7 上午09:30:33
	 * @return
	 */
	public String addSoftware()
	{
		int result=0;
		try
		{
			result=softwareService.addSoftware(software);
			if(result>0)
			{
				writeData(JsonUtil.toJsonString(software));
			}
			else
			{
				writeData(null);
				logger.error("添加软件出错!");
			}
		}catch (Exception e) {
			logger.error("添加软件出错",e);
		}
		return null;
	}
	
	/**
	 * 添加软件-添加软件页面用到
	 * @author 胡礼波
	 * 2013-7-18 下午6:17:45
	 * @return
	 */
	public String addSoftwareAjax()
	{
		int result=0;
		try
		{
			String[] dataStr = (String.valueOf(getServletReqeust().getParameter("serviceDirIds"))).split(",");
			Integer[] dirIds = DataConvertUtils.convertIntegerByString(dataStr);		//与服务器关联的软件ID
			result=softwareService.addSoftware(software,dirIds);
			writeData(result>0?Constant.OP_SUCCESS:Constant.OP_FAIL);
		}catch (Exception e) {
			logger.error("添加软件出错",e);
		}
		return null;
	}
	
	/**
	 * 编辑软件
	 * @author 胡礼波
	 * 2012-11-7 上午09:32:52
	 * @return
	 */
	public String editSoftware()
	{
		int result=0;
		try
		{
			String[] dataStr = StringUtil.splitData((String.valueOf(getServletReqeust().getParameter("serviceDirIds"))),",");
			Integer[] ids = DataConvertUtils.convertIntegerByString(dataStr);		//与软件关联的服务接口ID			
			result=softwareService.editSoftware(software,ids);
			writeData(result>0?Constant.OP_SUCCESS:Constant.OP_FAIL);
		}catch (Exception e) {
			logger.error("添加软件出错"+e);
		}
		return null;
	}
	
	/**
	 * 删除软件
	 * @author 胡礼波
	 * 2012-11-7 上午09:34:52
	 * @return
	 */
	public String delSoftware()
	{
		String dataStr=getServletReqeust().getParameter("data");
		String[] datas=StringUtils.split(dataStr, ",");
		Integer ids[]=(Integer[])ConvertUtils.convert(datas, Integer.class);
		int count=softwareService.delSoftware(ids);
		writeData(count);
		return null;
	}
	
	/**
	 * 查看软件
	 * @author 胡礼波
	 * 2012-11-7 上午09:37:24
	 * @return
	 */
	public String viewSoftware()
	{
		int softId=DataConvertUtils.convertInteger(getServletReqeust().getParameter("softId"));
		EsbSoftWare soft= softwareService.getSoftwareById(softId);
		if(soft!=null)
		{
			EsbServer server=serverVice.getServerById(soft.getServerId());
			getRequest().put("server",server);
		}
		getRequest().put("soft",soft);
		return "soft_view";
	}
	

	/**
	 * 查看软件代号是否存在
	 *@author 张霖
	 * 2013-7-22 下午6:33:40
	 * @return
	 */
	public String checkSoftCodeExist()
	{
		boolean flag=false;
		String softCode=getServletReqeust().getParameter("code");
		try
		{
			flag=softwareService.softCodeExist(softCode);
		}
		catch (Exception e) {
			logger.error("检查软件代号失败，"+e);
		}
		writeData(flag);
		return null;
	}
	
	/**
	 * 获得所有的软件列表---开放API调用
	 *@author 张霖
	 * 2013-7-24 下午2:21:30
	 * @return
	 */
	public String getServerSoftwareList()
	{
		instantPage();
		List<EsbServer> serverList=serverVice.getServerList();
		int total=softwareService.getCount();
		List<EsbSoftWare> softList=null;
		JSONObject jsonObj=null;
		JSONArray jsonArray=new JSONArray();
		for (EsbServer server: serverList) {
			jsonObj=new JSONObject();
			jsonObj.put("id", server.getId());
			jsonObj.put("softName",server.getServerName());
			softList=softwareService.getSoftwareByServerId(server.getId());
			jsonObj.put("children",JsonUtil.toJsonStringFilterPropterForArray(softList));
			jsonArray.add(jsonObj);
		}
		JSONObject data=JsonUtil.toJsonStringFilterPropter(jsonArray, total);
		setJsonData(data);
		return JSON;
	}
	
	/**
	 * 获得所有的服务地址列表
	 * @return
	 */
	public String getServersBySoftId()
	{
		Integer softId = DataConvertUtils.convertInteger(getServletReqeust().getParameter("softId"));
		JSONObject data=JsonUtil.toJsonStringFilterPropter(serverVice.getServerBySoftId(softId, true), serverVice.getServerBySoftCount(softId, true));
		setJsonData(data);
		return JSON;
	}
	
}
