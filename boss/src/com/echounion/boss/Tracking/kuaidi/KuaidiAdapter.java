package com.echounion.boss.Tracking.kuaidi;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.echounion.boss.Tracking.ITrackingService;
import com.echounion.boss.common.constant.Constant;
import com.echounion.boss.common.json.JsonUtil;
import com.echounion.boss.common.util.ThreadLocalUtil;
import com.echounion.boss.entity.TrackingHistory;
import com.echounion.boss.entity.dto.Caller;
import com.echounion.boss.entity.dto.TrackingInfo;

/**
 * 采集适配器
 * @author 张霖
 * 2013-3-4 下午2:12:46
 */
@Component
public class KuaidiAdapter {

	@Autowired
	private ITrackingService trackingService;
	
	public static boolean POST_TYPE=true;		//POST提交
	public static boolean GET_TYPE=false;		//GET提交
	
	public static String STYPE="type";			//前端传入快递公司类型名称
	public static String SVALUE="code";		    //前端传入单号值
	private Logger logger=Logger.getLogger(KuaidiAdapter.class);
	
	/**
	 * 分配采集对象
	 * @author 张霖
	 * 2013-3-4 下午2:15:22
	 * @param request
	 * @return
	 */
	public String execute(HttpServletRequest request)
	{
		Map<String, Object> map = getRequestParams(request);
		
		String code=(String)map.get(STYPE);			//快递公司代号
		
		if(StringUtils.isEmpty(code))
		{
			logger.warn("快递公司代号为空!");
			return null;
		}
		TrackingInfo trackingInfo=null;
		String url="http://www.kuaidi100.com/query";
		trackingInfo=(new KuaidiTracking()).doTrack(url,map,GET_TYPE);
		
		String data=null;
		try
		{
			data=JsonUtil.toJsonStringFilterPropter(trackingInfo).toJSONString();
			TrackingHistory history=assembleHistory(request);
			history.setUrl(url);
			history.setContent(data);
			history.setStatus(Constant.SUCCESS);
			history.setSourceCode(code);
			trackingService.addTrackingHistory(history);
		}catch (Exception e) {
			logger.info("采集信息出错",e);
			data=JsonUtil.toJsonString(new TrackingInfo());
		}
		return data;
	}
	
	/**	
	 * 组装采集日志记录对象
	 * @author 胡礼波
	 * 2012-11-5 上午10:57:38
	 * @param request
	 * @return
	 */
	public TrackingHistory assembleHistory(HttpServletRequest request)
	{
		Caller caller=(Caller)ThreadLocalUtil.getData();
		TrackingHistory history=new TrackingHistory();
		history.setSoftId(caller.getSoftId());
		history.setTime(new Date());
		return history;
	}
	
	/**
	 * 获得请求的参数
	 * @author 胡礼波
	 * 2012-11-23 上午10:54:57
	 * @param request
	 * @return
	 */
	public Map<String,Object> getRequestParams(HttpServletRequest request)
	{
		String sType=request.getParameter(STYPE);			//采集快递公司类型 
		String sValue=request.getParameter(SVALUE);			//单号
		Map<String,Object> map=new HashMap<String, Object>();
		map.put(STYPE,sType);
		map.put(SVALUE,sValue);
		return map;
	}
}
