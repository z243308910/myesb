package com.echounion.bossmanager.action.softserver;

import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONArray;
import com.echounion.bossmanager.action.BaseAction;
import com.echounion.bossmanager.common.constant.Constant;
import com.echounion.bossmanager.common.json.JsonUtil;
import com.echounion.bossmanager.common.util.DataConvertUtils;
import com.echounion.bossmanager.entity.EsbServiceDirParam;
import com.echounion.bossmanager.service.softserver.IDirParamsService;

/**
 * 参数Action
 * @author 张霖
 * 2013-7-23 下午5:46:28
 */
@Controller("DirParamsAction")
@Scope("prototype")
public class DirParamsAction extends BaseAction{

	/**
	 *  @author 张霖
	 *  2013-7-23  下午5:50:12
	 */
	private static final long serialVersionUID = -7293455977407963576L;

	@Autowired
	private IDirParamsService dirParamsService;
	private Logger logger = Logger.getLogger(DirParamsAction.class);
	
	private EsbServiceDirParam serviceDirParam;
	
	
	public EsbServiceDirParam getServiceDirParam() {
		return serviceDirParam;
	}

	public void setServiceDirParam(EsbServiceDirParam serviceDirParam) {
		this.serviceDirParam = serviceDirParam;
	}

	/**
	 * 根据服务接口ID获取相应的参数
	 *@author 张霖
	 * 2013-7-23 下午6:03:57
	 * @return
	 */
	public String getDirParamsByServiceDirId()
	{
		Integer serviceDirId = (Integer)ConvertUtils.convert(getServletReqeust().getParameter("serviceDirId"),Integer.class);
		List<EsbServiceDirParam> list = dirParamsService.getDirParamsByServiceId(serviceDirId);
		JSONArray jsonData = JsonUtil.toJsonStringFilterPropterForArray(list);
		writeData(jsonData);
		return null;
	}
	
	/**
	 * 查看服务参数信息
	 *@author 张霖
	 * 2013-7-25 上午9:25:35
	 * @return
	 */
	public String viewServicedirParam()
	{
		int paramId=DataConvertUtils.convertInteger(getServletReqeust().getParameter("paramId"));
		serviceDirParam=dirParamsService.getDirParamById(paramId);
		getRequest().put("serviceDirParam",serviceDirParam);
		return "param_view";
	}
	
	/**
	 *添加参数信息
	 *@author 张霖
	 * 2013-7-25 下午2:26:43
	 * @return
	 */
	public String addDirParam()
	{
		int count = 0 ;
		try
		{
				count=dirParamsService.addParams(serviceDirParam);
			}catch (Exception e) {
				e.printStackTrace();
				logger.error("添加服务参数失败!"+e);
			}
		writeData(count>0?Constant.OP_SUCCESS:Constant.OP_FAIL);
		return null;
	}
	
	/**
	 * 编辑服务参数信息
	 *@author 张霖
	 * 2013-7-25 下午2:43:45
	 * @return
	 */
	public String editDirParam()
	{
		int result = 0;
		try{
			EsbServiceDirParam[] paramsList = {serviceDirParam};
			result = dirParamsService.editParams(paramsList);
		}
		catch(Exception e)
		{
			logger.error("编辑参数信息出错："+e);
		}
		writeData(result>0?Constant.OP_SUCCESS:Constant.OP_FAIL);
		return null;
	}
	
	/**
	 * 删除指定服务参数
	 *@author
	 * 2013-7-25 下午5:27:37
	 * @return
	 */
	public String delDirParams()
	{
		String dataStr=getServletReqeust().getParameter("data");
		String datas[]=StringUtils.split(dataStr,",");
		Integer[] ids=(Integer[])ConvertUtils.convert(datas,Integer.class);
		int count=dirParamsService.delParams(ids);
		writeData(count);
		return null;
	}
}
