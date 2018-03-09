package com.echounion.boss.Tracking;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.echounion.boss.Tracking.acl.ACLTracking;
import com.echounion.boss.Tracking.anl.ANLTracking;
import com.echounion.boss.Tracking.anl.mobile.ANLMobileTracking;
import com.echounion.boss.Tracking.apl.APLTracking;
import com.echounion.boss.Tracking.apl.mobile.APLMobileTracking;
import com.echounion.boss.Tracking.ck.CKTracking;
import com.echounion.boss.Tracking.cma.CMATracking;
import com.echounion.boss.Tracking.cma.mobile.CMAMobileTracking;
import com.echounion.boss.Tracking.cnc.mobile.CNCMobileTracking;
import com.echounion.boss.Tracking.csav.CSAVTracking;
import com.echounion.boss.Tracking.cscl.CSCLTracking;
import com.echounion.boss.Tracking.dys.DYSTracking;
import com.echounion.boss.Tracking.emc.EMCTracking;
import com.echounion.boss.Tracking.esl.EslTracking;
import com.echounion.boss.Tracking.esl.mobile.ESLMobileTracking;
import com.echounion.boss.Tracking.fesco.FESCOTracking;
import com.echounion.boss.Tracking.gss.GSSTracking;
import com.echounion.boss.Tracking.has.HASTracking;
import com.echounion.boss.Tracking.hjs.HJSTracking;
import com.echounion.boss.Tracking.hmm.HMMTracking;
import com.echounion.boss.Tracking.jinjiang.JINJIANGTracking;
import com.echounion.boss.Tracking.kline.KLineTracking;
import com.echounion.boss.Tracking.kline.mobile.KlineMobileTrackig;
import com.echounion.boss.Tracking.kmtc.KMTCTracking;
import com.echounion.boss.Tracking.lt.LTTracking;
import com.echounion.boss.Tracking.matson.MATSONTracking;
import com.echounion.boss.Tracking.mcc.MCCTracking;
import com.echounion.boss.Tracking.msc.MSCTracking;
import com.echounion.boss.Tracking.msk.MskTracking;
import com.echounion.boss.Tracking.msk.mobile.MSKMobileTracking;
import com.echounion.boss.Tracking.msl.MSLTracking;
import com.echounion.boss.Tracking.namsung.NAMSUNGTracking;
import com.echounion.boss.Tracking.nds.NDSTracking;
import com.echounion.boss.Tracking.nordan.NORDANTracking;
import com.echounion.boss.Tracking.nss.NSSTracking;
import com.echounion.boss.Tracking.nyk.NYKTracking;
import com.echounion.boss.Tracking.nyk.mobile.NYKMobileTracking;
import com.echounion.boss.Tracking.pcl.PCLTracking;
import com.echounion.boss.Tracking.pil.PILTracking;
import com.echounion.boss.Tracking.pil.mobile.PILMobileTracking;
import com.echounion.boss.Tracking.rcl.RCLTracking;
import com.echounion.boss.Tracking.saf.SafTracking;
import com.echounion.boss.Tracking.saf.mobile.SAFMobileTracking;
import com.echounion.boss.Tracking.sinokor.SINOKORTracking;
import com.echounion.boss.Tracking.sitc.SITCTracking;
import com.echounion.boss.Tracking.snl.SNLTracking;
import com.echounion.boss.Tracking.stx.STXTracking;
import com.echounion.boss.Tracking.tsline.TSLINETracking;
import com.echounion.boss.Tracking.uasc.UASCTracking;
import com.echounion.boss.Tracking.uasc.mobile.UASCMobileTracking;
import com.echounion.boss.Tracking.whl.WHLTracking;
import com.echounion.boss.Tracking.yml.YMLTracking;
import com.echounion.boss.Tracking.zim.ZIMTracking;
import com.echounion.boss.Tracking.zim.mobile.ZIMMobileTracking;
import com.echounion.boss.cargosmart.service.ICsTrackingService;
import com.echounion.boss.common.constant.Constant;
import com.echounion.boss.common.json.JsonUtil;
import com.echounion.boss.common.util.ThreadLocalUtil;
import com.echounion.boss.entity.TrackingHistory;
import com.echounion.boss.entity.dto.Caller;
import com.echounion.boss.entity.dto.TrackingInfo;

/**
 * 采集适配器
 * @author 胡礼波
 * 2012-11-3 下午04:44:59
 */
@Component
public class TrackingAdapter {

	@Autowired
	private ITrackingService trackingService;
	@Autowired
	private ICsTrackingService csTrackingService;
	
	public static boolean POST_TYPE=true;		//POST提交
	public static boolean GET_TYPE=false;		//GET提交
	
	public static final String STYPE_CONTAINER="container";	//前端传入container类型
	public static final String STYPE_BILL="bill";			//前端传入bill类型
	public static final String STYPE_BOOKING="booking";	//前端传入booking类型
	public static String STYPE="stype";			//前端传入类型名称
	public static String SVALUE="svalue";		//前端传入值
	private Logger logger=Logger.getLogger(TrackingAdapter.class);
	
	/**
	 * 分配采集对象
	 * @author 胡礼波
	 * 2012-11-3 下午04:45:48
	 * @param request
	 * @return
	 */
	public String execute(HttpServletRequest request)
	{

		TrackingInfo trackingInfo=null;
		Map<String,Object> param=getRequestParams(request);
		trackingInfo=csTrackingService.getTrackingInfo(String.valueOf(param.get(STYPE)),String.valueOf(param.get(SVALUE)));
		if(null!=null)			//cs查不到去船公司查询
		{
			
		}
		else
		{
			trackingInfo=companyTracking(request);
		}
		
		String data=null;
		try
		{
			data=JsonUtil.toJsonStringFilterPropter(trackingInfo).toJSONString();
			TrackingHistory history=assembleHistory(request);
			history.setUrl("local");
			history.setContent(data);
			history.setStatus(Constant.SUCCESS);
			history.setSourceCode("cargosmart");
			trackingService.addTrackingHistory(history);
		}catch (Exception e) {
			logger.info("采集信息出错"+e);
			data=JsonUtil.toJsonString(new TrackingInfo());
		}
		return data;
	}
	
	private TrackingInfo companyTracking(HttpServletRequest request)
	{
		String code=request.getParameter("sc");			//船公司代号
		String source = request.getParameter("source");
		Map<String,Object> param = getRequestParams(request);
		if(StringUtils.isEmpty(code))
		{
			logger.warn("船公司代号为空!");
			return null;
		}
		TrackingInfo trackingInfo=null;
		if("mobile".equalsIgnoreCase(source))
		{
			trackingInfo = mobileTracking(code, param);
		}
		else
		{
			trackingInfo = webTracking(code, param);
		}
		String data=null;
		try
		{
			data=JsonUtil.toJsonStringFilterPropter(trackingInfo).toJSONString();
			TrackingHistory history=assembleHistory(request);
			history.setUrl(null);
			history.setContent(data);
			history.setStatus(Constant.SUCCESS);
			history.setSourceCode(code);
			trackingService.addTrackingHistory(history);
		}catch (Exception e) {
			e.printStackTrace();
			logger.info("采集信息出错"+e);
			data=JsonUtil.toJsonString(new TrackingInfo());
		}
		return trackingInfo;
	}
	
	private TrackingInfo webTracking(String code,Map<String,Object> param)
	{
		TrackingInfo trackingInfo = null;
		String url = null;
		if(code.equals("ANL"))					//ANL船公司
		{
			trackingInfo=(new ANLTracking()).doTrack("http://www.anl.com.au/ebusiness/tracking/search",param,GET_TYPE);
		}
		else if(code.equals("CMA") || code.equals("DELMAS"))
		{
			trackingInfo=(new CMATracking()).doTrack("http://www.cma-cgm.com/ebusiness/tracking/search",param,GET_TYPE);
		}
		else if(code.equals("EMC"))
		{
			trackingInfo=(new EMCTracking()).doTrack("http://www.shipmentlink.com/servlet/TDB1_CargoTracking.do",param,POST_TYPE);			
		}
		else if(code.equals("WHL"))
		{
			trackingInfo=(new WHLTracking()).doTrack("http://web.wanhai.com/index_whl.jsp?web=whlwww&file_num=15040&i_url=whlwww/cargoTrackList.jsp",param,POST_TYPE);			
		}
		else if(code.equals("ZIM"))
		{	
			trackingInfo=(new ZIMTracking()).doTrack("http://www.zim.com/pages/findcontainer.aspx",param,GET_TYPE);
		}
		else if(code.equals("PIL"))
		{
			trackingInfo=(new PILTracking()).doTrack("https://www.pilship.com/shared/ajax/",param,POST_TYPE);
		}
		else if(code.equals("APL"))
		{
			Map<String,Object> map=param;
			url=(map.get(STYPE).toString().equals(STYPE_CONTAINER))?"http://homeport.apl.com/gentrack/containerTrackingPopup.do":"http://homeport.apl.com/gentrack/bodyframeRouting.do";				
			trackingInfo=(new APLTracking()).doTrack(url,param,POST_TYPE);
		}else if(code.equals("MSC"))
		{
			trackingInfo=(new MSCTracking()).doTrack("http://wcf.mscgva.ch/publicasmx/Tracking.asmx/GetRSSTrackingByContainerNumber",param,GET_TYPE);			
		}else if(code.equals("NYK"))
		{
			trackingInfo=(new NYKTracking()).doTrack("http://www2.nykline.com/ct/chContainerSearch.nyk?lang=zh&country=CHN",param,POST_TYPE);
		}else if(code.equals("HMM"))
		{
			trackingInfo=(new HMMTracking()).doTrack("http://www.hmm.co.kr/ebiz/track_trace/trackCTP.jsp", param,POST_TYPE);
		}else if(code.equals("UASC"))
		{
			trackingInfo=(new UASCTracking()).doTrack("http://uasconline.uasc.net/TrackingResults",param, POST_TYPE);
		}else if(code.equals("TSLINE"))
		{
			trackingInfo=(new TSLINETracking()).doTrack("http://www.tslines.com/TCT1/TCT1Query.aspx", param, POST_TYPE);
		}else if(code.equals("CSCL"))
		{
			trackingInfo=(new CSCLTracking()).doTrack("http://222.66.158.204/cargo_track/cargo_track_rst_pre.jsp", param, POST_TYPE);
		}else if(code.equals("MCC"))
		{
			trackingInfo=(new MCCTracking()).doTrack("http://iframe.mcc.com.sg/tracking/results/", param, GET_TYPE);
		}else if(code.equals("SITC"))
		{
			trackingInfo=(new SITCTracking()).doTrack("http://www.sitcline.com/track/biz/trackCargoTrack.do",param,POST_TYPE);
		}else if(code.equals("JINJIANG"))
		{
			Map<String,Object> map=param;
			url=(!map.get(STYPE).toString().equals(STYPE_CONTAINER))?"http://www.jjshipping.com/khfw_khcx_list4_ctn.do":"http://www.jjshipping.com/khfw_khcx_list4_bill.do";			
			trackingInfo=(new JINJIANGTracking()).doTrack("",param,POST_TYPE);
		}else if(code.equals("FESCO"))
		{
			trackingInfo=(new FESCOTracking()).doTrack("http://www.fesco-china.cn/eneyd/service-search.aspx",param,POST_TYPE);
		}else if(code.equals("NAMSUNG"))
		{
			trackingInfo=(new NAMSUNGTracking()).doTrack("http://www.namsung.co.kr/eng/eservice/trace.ns.tsp",param,POST_TYPE);
		}else if(code.equals("MATSON"))
		{
			trackingInfo=(new MATSONTracking()).doTrack("https://www.matson.com/vcsc/VisibilityController",param,POST_TYPE);
		}
		else if(code.equals("SINOKOR"))
		{
			trackingInfo=(new SINOKORTracking()).doTrack("http://www.sinokor.co.kr/service/cntrTrack/cntr_tracking.asp",param,POST_TYPE);			
		}else if(code.equals("CSAV"))
		{
			trackingInfo=(new CSAVTracking()).doTrack("http://www.csavnorasia.com/rastreo/rastreo.nsf/yourtrace",param,GET_TYPE);
		}else if(code.equals("KLINE"))
		{			
			trackingInfo=(new KLineTracking()).doTrack("http://ecom.kline.com/tracking", param, POST_TYPE);
		}
		else if(code.equals("MSK"))
		{
			trackingInfo=(new MskTracking()).doTrack("http://my.maerskline.com/appmanager/maerskline/public?_nfpb=true&_windowLabel=portlet_trackSimple_1&portlet_trackSimple_1_actionOverride=/portlets/tracking3/track/trackSimple/trackSimple&_pageLabel=page_tracking3_trackSimple", param, POST_TYPE);
		}
		else if(code.equals("ESL"))
		{
			trackingInfo=(new EslTracking()).doTrack("http://www.emiratesline.com/cargotracking/cargomovement.html", param, POST_TYPE);			
		}
		else if(code.equals("SAF"))
		{
			trackingInfo=(new SafTracking()).doTrack("http://mysaf2.safmarine.com/wps/portal/Safmarine/etrackUnregistered",param,GET_TYPE);
		}
		else if(code.equals("CK"))
		{
			trackingInfo=(new CKTracking()).doTrack("http://www.ckline.co.kr/korea/service/se03.ck",param, POST_TYPE);
		}
		else if(code.equals("GSS"))
		{
			trackingInfo=(new GSSTracking()).doTrack("http://exn.griegstar.com/cargotracking/index.php",param,POST_TYPE);
		}
		else if(code.equals("SNL"))
		{
			trackingInfo=(new SNLTracking()).doTrack("http://ebusiness.sinolines.com/ebusiness/cargotracking.aspx",param,POST_TYPE);
		}
		else if(code.equals("NSS"))
		{
			trackingInfo=(new NSSTracking()).doTrack("http://www.namsung.co.kr/kor/eservice/trace.ns.tsp",param,POST_TYPE);
		}
		else if(code.equals("HJS"))
		{
			trackingInfo=(new HJSTracking()).doTrack("http://www.hanjin.com/hanjin/CUP_HOM_3301GS.do",param,POST_TYPE);	
		}
		else if(code.equals("MSL"))
		{
			trackingInfo=(new MSLTracking()).doTrack("http://www.mssco.net/Eip/UserControl/SetClientInfo.aspx",param,POST_TYPE);
		}
		else if(code.equals("KMTC"))
		{
			trackingInfo=(new KMTCTracking()).doTrack("http://www.ekmtc.com/CCIT100/searchContainerList.do",param,GET_TYPE);
		}
		else if(code.equals("PCL"))
		{
			trackingInfo=(new PCLTracking()).doTrack("http://www.pancon.co.kr/pcl/tracking/bl",param,GET_TYPE);
		}
		else if(code.equals("NORDAN"))
		{
			url=(param.get(STYPE).toString().equals(STYPE_CONTAINER))?"http://66.0.33.170/cgi-bin/WEQ001.PGM":"http://66.0.33.170/cgi-NU/WCS001.PGM";
			trackingInfo=(new NORDANTracking()).doTrack(url,param,POST_TYPE);
		}
		else if(code.equals("LT"))
		{
			trackingInfo=(new LTTracking()).doTrack("http://www.shipmentlink.com/servlet/TDB1_CargoTracking.do",param,POST_TYPE);
		}
		else if(code.equals("HAS"))
		{
			trackingInfo=(new HASTracking()).doTrack("http://ebiz.heung-a.co.kr/chinese/cargotrace.cfm",param,POST_TYPE);
		}
		else if(code.equals("DYS"))
		{
			trackingInfo=(new DYSTracking()).doTrack("http://www.pcsline.co.kr/eng/ebusiness/ebusiness01.asp",param,POST_TYPE);
		}
		else if(code.equals("ACL"))
		{
			trackingInfo=(new ACLTracking()).doTrack("http://www.aclcargo.com/trackCargo.php",param,POST_TYPE);
		}
		else if(code.equals("YML"))
		{
			url=(param.get(STYPE).toString().equals(STYPE_CONTAINER))?"http://www.yangming.com/english/ASP/e-service/track_trace/CTCONNECT.ASP":"http://www.yangming.com/english/ASP/e-service/track_trace/blconnect.asp";	
			trackingInfo=(new YMLTracking()).doTrack(url,param,POST_TYPE);
		}
		else if(code.equals("STX"))
		{
			trackingInfo=(new STXTracking()).doTrack("http://container.panocean.com/HP2401/hp2401List.stx",param,POST_TYPE);
		}
		else if(code.equals("RCL"))
		{
			trackingInfo=(new RCLTracking()).doTrack("http://www.rclgroup.com/CargoTracking.asp",param,POST_TYPE);
		}
		else if(code.equals("NDS"))
		{
			trackingInfo=(new NDSTracking()).doTrack("https://www.niledutch.com/en/e-services/quick-tracking-full-tracking.aspx",param,POST_TYPE);
		}
		else
		{
			logger.error("Web 暂不支持船公司："+code);
		}
		return trackingInfo;
	}
	
	private TrackingInfo mobileTracking(String code,Map<String,Object> param)
	{
		TrackingInfo trackingInfo = null;
		if(code.equals("KLine"))
		{
			trackingInfo = (new KlineMobileTrackig()).doTrack("http://ecom.kline.com/Tracking/Search/FindShipment",param,POST_TYPE);
		}
		else if(code.equals("CMA"))
		{
			trackingInfo = (new CMAMobileTracking()).doTrack("http://www.cma-cgm.com/ebusiness/tracking/search",param,POST_TYPE);
		}
		else if(code.equals("APL"))
		{
			trackingInfo = (new APLMobileTracking()).doTrack("http://homeport.apl.com/gentrack/containerTrackingPopup.do",param,POST_TYPE);
		}
		else if(code.equals("PIL"))
		{
			trackingInfo = (new PILMobileTracking()).doTrack("https://www.pilship.com/shared/ajax/",param,POST_TYPE);
		}
		else if(code.equals("MSK"))
		{
			trackingInfo = (new MSKMobileTracking()).doTrack("http://my.maerskline.com/appmanager/maerskline/public?_nfpb=true&_windowLabel=portlet_trackSimple_1&portlet_trackSimple_1_actionOverride=/portlets/tracking3/track/trackSimple/trackSimple&_pageLabel=page_tracking3_trackSimple",param,POST_TYPE);		
		}
		else if(code.equals("ESL"))
		{
			trackingInfo = (new ESLMobileTracking()).doTrack("http://www.emiratesline.com/cargotracking/cargomovement.html",param,POST_TYPE);
		}
		else if(code.equals("UASC"))
		{
			trackingInfo = (new UASCMobileTracking()).doTrack("http://uasconline.uasc.net/Tracking-ContainerDetails",param,POST_TYPE);
		}
		else if(code.equals("ZIM"))
		{
			trackingInfo = (new ZIMMobileTracking()).doTrack("http://www.zim.com/pages/findcontainer.aspx",param,GET_TYPE);
		}
		else if(code.equals("ANL"))
		{
			trackingInfo = (new ANLMobileTracking()).doTrack("http://www.anl.com.au/ebusiness/tracking/search",param,POST_TYPE);
		}
		else if(code.equals("NYK"))
		{
			trackingInfo = (new NYKMobileTracking()).doTrack("http://www2.nykline.com/ct/containerSearch.nyk?lang=en&country=USA",param,POST_TYPE);
		}
		else if(code.equals("HMM"))
		{
			trackingInfo = (new NYKMobileTracking()).doTrack("http://www.hmm.co.kr/ebiz/track_trace/trackCTP.jsp",param,POST_TYPE);
		}
		else if(code.equals("SAF"))
		{
			trackingInfo = (new SAFMobileTracking()).doTrack("http://mysaf2.safmarine.com/wps/portal/Safmarine/etrackUnregistered",param,POST_TYPE);
		}
		else if(code.equals("CNC"))
		{
			trackingInfo = new CNCMobileTracking().doTrack("http://www.cnc-ebusiness.com/ebusiness/tracking/search",param, POST_TYPE);
		}
		else
		{
			logger.error("Mobile 暂不支持船公司："+code);
		}
		return trackingInfo;
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
		TrackingHistory history = new TrackingHistory();
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
		String sType=request.getParameter(STYPE);			//采集类型 bill no，container no，booking no
		String sValue=request.getParameter(SVALUE);		//单号
		Map<String,Object> map=new HashMap<String, Object>();
		map.put(STYPE,sType);
		map.put(SVALUE,sValue);
		return map;
	}
}
