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
import com.echounion.bossmanager.common.json.JsonUtil;
import com.echounion.bossmanager.common.util.DataConvertUtils;
import com.echounion.bossmanager.entity.EsbServiceDir;
import com.echounion.bossmanager.entity.EsbServiceDirParam;
import com.echounion.bossmanager.entity.EsbSoftWare;
import com.echounion.bossmanager.service.softserver.IServiceDirService;
import com.echounion.bossmanager.service.softserver.ISoftwareService;

/**
 * 软件服务Action
 * @author 胡礼波
 * 2012-11-8 上午09:47:38
 */
@Controller("ServiceDirAction")
@Scope("prototype")
public class ServiceDirAction extends BaseAction {

	/**
	 * @author 胡礼波
	 * 2013-7-16 下午2:19:35
	 */
	private static final long serialVersionUID = 2741850145561162767L;
	
	private Logger logger = Logger.getLogger(ServiceDirAction.class);
	
	@Autowired
	private IServiceDirService serviceDirService;
	@Autowired
	private ISoftwareService softwareService;
	
	private EsbServiceDir serviceDir;
	
	private List<EsbServiceDirParam> params;
	
	/**
	 * 获得所有的服务列表---服务目录调用
	 * @author 胡礼波
	 * 2013-7-19 上午11:31:24
	 * @return
	 */
	public String getServiceDirList()
	{
		instantPage();
		List<EsbSoftWare> softList=softwareService.getSoftwareList();
		int total=softwareService.getCount();
		List<EsbServiceDir> dirList=null;
		JSONObject jsonObj=null;
		JSONArray jsonArray=new JSONArray();
		for (EsbSoftWare soft: softList) {
			jsonObj=new JSONObject();
			jsonObj.put("id",soft.getId()+100);
			jsonObj.put("serviceCode",soft.getSoftCode());
			jsonObj.put("serviceName",soft.getSoftName());
			jsonObj.put("serviceUrl","");
			jsonObj.put("methodId", "");
			jsonObj.put("remark",soft.getRemark());				//获得主对象
			
			dirList=serviceDirService.getServiceDirBySoftId(soft.getId());
			
			jsonObj.put("children",JsonUtil.toJsonStringFilterPropterForArray(dirList));
			jsonArray.add(jsonObj);
		}
		JSONObject data=JsonUtil.toJsonStringFilterPropter(jsonArray, total);
		setJsonData(data);
		return JSON;
	}
	
	/**
	 * 根据软件ID获取相应的服务列表
	 *@author
	 * 2013-7-17 下午6:41:48
	 * @return
	 */
	public String getServiceBySoftId()
	{
		instantPage();
		int softId = (Integer)ConvertUtils.convert(getServletReqeust().getParameter("softId"),Integer.class);
		List<EsbServiceDir> list = serviceDirService.getServiceDirBySoftId(softId);
		int total=serviceDirService.getServiceDirCount(softId);
		JSONObject jsonData = JsonUtil.toJsonStringFilterPropter(list, total);
		setJsonData(jsonData);
		return JSON;
	}
	
	/**
	 * 获得服务接口
	 * @author 胡礼波
	 * 2013-7-18 下午6:29:31
	 * @return
	 */
	public String getServiceDirById()
	{
		String dataStr=getServletReqeust().getParameter("data");
		String datas[]=StringUtils.split(dataStr,",");
		Integer[] ids=DataConvertUtils.convertIntegerByString(datas);
		List<EsbServiceDir> list =serviceDirService.getServiceDirById(ids);
		JSONArray jsonData = JsonUtil.toJsonStringFilterPropterForArray(list);
		writeData(jsonData);
		return null;
	}
	
	/**
	 * 删除服务接口
	 * @author 胡礼波
	 * 2013-7-18 下午6:23:10
	 * @return
	 */
	public String delServiceDir()
	{
		String dataStr=getServletReqeust().getParameter("data");
		String datas[]=StringUtils.split(dataStr,",");
		Integer[] ids=DataConvertUtils.convertIntegerByString(datas);
		int count=serviceDirService.delServiceDirById(ids);
		writeData(count);
		return null;
	}
	
	/**
	 * 添加服务接口
	 * @author 胡礼波
	 * 2013-7-18 上午10:06:40
	 * @return
	 */
	public String addServiceDir()
	{
		int result=0;
		try
		{
		 result=serviceDirService.addServiceDir(serviceDir,params);
		}catch (Exception e) {
			e.printStackTrace();
		}
		writeData(result>0?JsonUtil.toJsonString(serviceDir):null);
		return null;
	}
	
	/**
	 * 添加服务接口
	 * @author 胡礼波
	 * 2013-7-18 上午10:06:40
	 * @return
	 */
	public String addServiceDirForGrid()
	{
		int result=0;
		try
		{
		 result=serviceDirService.addServiceDir(serviceDir,params);
		}catch (Exception e) {
			e.printStackTrace();
		}
		writeData(result>0?Constant.OP_SUCCESS:Constant.OP_FAIL);
		return null;
	}

	public List<EsbServiceDirParam> getParams() {
		return params;
	}

	public void setParams(List<EsbServiceDirParam> params) {
		this.params = params;
	}
		
	public EsbServiceDir getServiceDir() {
		return serviceDir;
	}

	public void setServiceDir(EsbServiceDir serviceDir) {
		this.serviceDir = serviceDir;
	}
	
	/**
	 * 查看服务代号是否存在
	 *@author 张霖
	 * 2013-7-22 下午6:33:40
	 * @return
	 */
	public String checkServiceCodeExist()
	{
		boolean flag=false;
		String serviceCode=getServletReqeust().getParameter("code");
		try
		{
			flag=serviceDirService.serviceCodeExist(serviceCode);
		}
		catch (Exception e) {
			logger.error("检查软件代号失败，"+e);
		}
		writeData(flag);
		return null;
	}
	
	/**
	 * 查看服务信息
	 *@author 张霖
	 * 2013-7-24 下午5:15:28
	 * @return
	 */
	public String viewServiceDir()
	{
		int serviceId=DataConvertUtils.convertInteger(getServletReqeust().getParameter("serviceId"));
		serviceDir=serviceDirService.getDirById(serviceId);
		getRequest().put("serviceDir",serviceDir);
		return "dir_view";
	}
	
	/**
	 * 编辑服务信息
	 *@author 张霖
	 * 2013-7-24 下午6:18:19
	 * @return
	 */
	public String editServiceDir()
	{
		int result=0;
		try
		{
		 result=serviceDirService.editServiceDir(serviceDir);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("编辑服务信息出错："+e);
		}
		writeData(result>0?Constant.OP_SUCCESS:Constant.OP_FAIL);
		return null;
	}
	
}
