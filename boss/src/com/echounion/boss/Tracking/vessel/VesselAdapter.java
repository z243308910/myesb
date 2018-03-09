package com.echounion.boss.Tracking.vessel;

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
 * 2013-3-5 上午10:53:54
 */
@Component
public class VesselAdapter {

	@Autowired
	private ITrackingService trackingService;

	public static boolean POST_TYPE = true; // POST提交
	public static boolean GET_TYPE = false; // GET提交

	public static String POL = "pol"; 	   // 前端传入起运港值
	public static String POD = "pod";      // 前端传入目的港值

	private Logger logger = Logger.getLogger(VesselAdapter.class);

	/**
	 * 分配采集对象
	 * @author 张霖 
	 * 2013-3-5 上午10:54:11
	 * @param request
	 * @return
	 */
	public String execute(HttpServletRequest request) 
	{
		Map<String, Object> map = getRequestParams(request);
		String tCode = (String) map.get(POL); 
		String fCode = (String) map.get(POD);
		if (StringUtils.isEmpty(tCode) || StringUtils.isEmpty(fCode)) 
		{
			logger.warn("起运港或目的港为空!");
			return null;
		}
		TrackingInfo trackingInfo = null;
		String url ="http://www.shippingazette.com/imp_exp/voyage_list.asp";
		trackingInfo = (new VesselTracking()).doTrack(url,	map, POST_TYPE);
		String data = null;
		try {
			data = JsonUtil.toJsonStringFilterPropter(trackingInfo)
					.toJSONString();
			TrackingHistory history = assembleHistory(request);
			history.setUrl(url);
			history.setContent(data);
			history.setStatus(Constant.SUCCESS);
			history.setSourceCode(tCode);
			trackingService.addTrackingHistory(history);
		} catch (Exception e) {
			logger.info("采集信息出错" + e);
			data = JsonUtil.toJsonString(new TrackingInfo());
		}
		return data;
	}

	/**
	 * 组装采集日志记录对象
	 * @author 张霖 
	 * 2013-3-5 上午10:55:01
	 * @param request
	 * @return
	 */
	public TrackingHistory assembleHistory(HttpServletRequest request) {
		Caller caller=(Caller)ThreadLocalUtil.getData();
		TrackingHistory history = new TrackingHistory();
		history.setSoftId(caller.getSoftId());
		history.setTime(new Date());
		return history;
	}

	/**
	 * 获取请求参数
	 * @author 张霖 
	 * 2013-3-5 上午10:55:24
	 * @param request
	 * @return
	 */
	public Map<String, Object> getRequestParams(HttpServletRequest request) {
		String pol = request.getParameter(POL);   // 起运港id
		String pod = request.getParameter(POD);   // 目的港id
		String encode=request.getParameter("encode");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(POL, pol);
		map.put(POD, pod);
		map.put("range", 200);               //限定数据显示数据的范围
		map.put("is_export", 1);
		map.put("encode",encode); 			//页面编码
		return map;
	}
}
