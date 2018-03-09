package com.echounion.bossmanager.action.softserver;

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
import com.echounion.bossmanager.common.json.JsonUtil;
import com.echounion.bossmanager.common.util.DataConvertUtils;
import com.echounion.bossmanager.common.util.StringUtil;
import com.echounion.bossmanager.entity.EsbServer;
import com.echounion.bossmanager.service.softserver.IServerService;

/**
 * 服务器Action
 * @author 胡礼波
 * 2012-11-8 下午05:23:39
 */
@Controller("serverAction")
@Scope("prototype")
public class ServerAction extends BaseAction {

	/**
	 * @author 胡礼波
	 * 2012-11-8 下午05:29:45
	 */
	private static final long serialVersionUID = -5418349667799726214L;
	private Logger logger=Logger.getLogger(ServerAction.class);
	
	@Autowired
	private IServerService serverService;
	private EsbServer server;
	
	public EsbServer getServer() {
		return server;
	}

	public void setServer(EsbServer server) {
		this.server = server;
	}

	public String execute()
	{
		return getServerList();
	}
	
	/**
	 * 返回所有的服务器信息
	 * @author 胡礼波
	 * 2012-11-8 下午05:29:35
	 * @return
	 */
	public String getServerList()
	{
		List<EsbServer> list=serverService.getServerList();
		int total=serverService.getCount();
		JSONObject jsonData=JsonUtil.toJsonStringFilterPropter(list, total);
		setJsonData(jsonData);
		return JSON;
	}
	
	/**
	 * 添加服务器信息
	 * @author 胡礼波
	 * 2012-11-8 下午05:31:35
	 * @return
	 */
	public String addServer()
	{
		int result=0;
		try
		{
			String[] dataStr = (String.valueOf(getServletReqeust().getParameter("softIds"))).split(",");
			Integer[] ids = DataConvertUtils.convertIntegerByString(dataStr);		//与服务器关联的软件ID
			result=serverService.addServer(server,ids);
		}catch (Exception e) {
			logger.error("添加服务器信息出错",e);
		}
		writeData(result>0?Constant.OP_SUCCESS:Constant.OP_FAIL);
		return null;
	}
	
	/**
	 * 编辑服务器信息
	 * @author 胡礼波
	 * 2012-11-8 下午05:32:00
	 * @return
	 */
	public String editServer()
	{
		int result=0;
		try
		{
			String[] dataStr = StringUtil.splitData((String.valueOf(getServletReqeust().getParameter("softIds"))),",");
			Integer[] ids = DataConvertUtils.convertIntegerByString(dataStr);		//与服务器关联的软件ID
			result=serverService.editServer(server,ids);
		}catch (Exception e) {
			logger.error("添加服务器信息出错"+e);
		}
		writeData(result>0?Constant.OP_SUCCESS:Constant.OP_FAIL);
		return null;
	}
	
	/**
	 * 删除服务器
	 * @author 胡礼波
	 * 2013-7-11 下午7:00:22
	 * @return
	 */
	public String delServer()
	{
		String dataStr=getServletReqeust().getParameter("data");
		String datas[]=StringUtils.split(dataStr,",");
		Integer[] ids=DataConvertUtils.convertIntegerByString(datas);
		int count=serverService.delServer(ids);
		writeData(count);
		return null;
	}
	
	/**
	 * 查看软件基本信息
	 * @author 胡礼波
	 * 2012-11-9 上午10:18:15
	 * @return
	 */
	public String viewServer()
	{
		int serverId=(Integer)ConvertUtils.convert(getServletReqeust().getParameter("svId"),Integer.class);//服务器编号
		EsbServer server=serverService.getServerById(serverId);
		getRequest().put("server",server);
		return "server_view";
	}
	
	/**
	 * 根据软件ID获得该软件ID部署在哪些服务器上
	 */
	public String getServerBySoft()
	{
		int softId=(Integer)ConvertUtils.convert(getServletReqeust().getParameter("sid"),Integer.class);
		List<EsbServer> list=serverService.getServerBySoftId(softId,false);	//查询出可用的服务器
		int total=serverService.getServerBySoftCount(softId,false);
		JSONObject jsonData=JsonUtil.toJsonStringFilterPropter(list, total);
		setJsonData(jsonData);
		return JSON;		
	}
	
	/**
	 * 检查服务器IP是否重复
	 * @author 胡礼波
	 * 2012-12-14 下午03:40:27
	 * @return
	 */
	public String checkIPExist()
	{
		String ip=getServletReqeust().getParameter("code");
		boolean flag=false;
		try {
			flag = serverService.isExistIp(ip);
		} catch (RuntimeException e) {
			logger.error("检查服务器IP失败，"+e);
		}
		writeData(flag);
		return null;
	}
	
}
