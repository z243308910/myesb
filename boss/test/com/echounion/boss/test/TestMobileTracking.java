package com.echounion.boss.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.Tracking.anl.mobile.ANLMobileTracking;
import com.echounion.boss.Tracking.apl.mobile.APLMobileTracking;
import com.echounion.boss.Tracking.cma.mobile.CMAMobileTracking;
import com.echounion.boss.Tracking.cnc.mobile.CNCMobileTracking;
import com.echounion.boss.Tracking.esl.mobile.ESLMobileTracking;
import com.echounion.boss.Tracking.hmm.mobile.HMMMobileTracking;
import com.echounion.boss.Tracking.kline.mobile.KlineMobileTrackig;
import com.echounion.boss.Tracking.msk.mobile.MSKMobileTracking;
import com.echounion.boss.Tracking.nyk.mobile.NYKMobileTracking;
import com.echounion.boss.Tracking.pil.mobile.PILMobileTracking;
import com.echounion.boss.Tracking.saf.mobile.SAFMobileTracking;
import com.echounion.boss.Tracking.uasc.mobile.UASCMobileTracking;
import com.echounion.boss.Tracking.zim.mobile.ZIMMobileTracking;
import com.echounion.boss.common.json.JsonUtil;
import com.echounion.boss.entity.dto.TrackingInfo;

public class TestMobileTracking{


	public void testKLineMobileTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		map.put(TrackingAdapter.SVALUE,"TCKU378982-4");
		String url="http://ecom.kline.com/Tracking/Search/FindShipment";
		TrackingInfo info = new KlineMobileTrackig().doTrack(url,map, true);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}
	

	public void testCMAMobileTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		map.put(TrackingAdapter.SVALUE,"ECMU4566729");
		String url="http://www.cma-cgm.com/ebusiness/tracking/search";
		TrackingInfo info = new CMAMobileTracking().doTrack(url,map, true);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}
	

	public void testAPLMobileTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		map.put(TrackingAdapter.SVALUE,"TGHU486854");
		String url="http://homeport.apl.com/gentrack/containerTrackingPopup.do";
		TrackingInfo info = new APLMobileTracking().doTrack(url,map, true);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}
	
	public void testPILMobileTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		map.put(TrackingAdapter.SVALUE,"PCIU2893617");
		String url="https://www.pilship.com/shared/ajax/";
		TrackingInfo info = new PILMobileTracking().doTrack(url,map, true);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}
	
	public void testMSKMobileTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		map.put(TrackingAdapter.SVALUE,"PONU0384452");
		String url="http://my.maerskline.com/appmanager/maerskline/public?_nfpb=true&_windowLabel=portlet_trackSimple_1&portlet_trackSimple_1_actionOverride=/portlets/tracking3/track/trackSimple/trackSimple&_pageLabel=page_tracking3_trackSimple";
		TrackingInfo info = new MSKMobileTracking().doTrack(url,map, true);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}
	

	public void testESLMobileTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		map.put(TrackingAdapter.SVALUE,"AAAU2003129");
		String url="http://www.emiratesline.com/cargotracking/cargomovement.html";
		TrackingInfo info = new ESLMobileTracking().doTrack(url,map, true);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}
	
	public void testUASCMobileTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		map.put(TrackingAdapter.SVALUE,"UACU3178760");
		String url="http://uasconline.uasc.net/Tracking-ContainerDetails";
		TrackingInfo info = new UASCMobileTracking().doTrack(url,map, true);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}

	
	public void testZIMMobileTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		map.put(TrackingAdapter.SVALUE,"ZCSU2695057");
		String url="http://www.zim.com/pages/findcontainer.aspx";
		TrackingInfo info = new ZIMMobileTracking().doTrack(url,map, false);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}
	
	@Test
	public void testANLMobileTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		map.put(TrackingAdapter.SVALUE,"CMAU1178373");
		String url="http://www.anl.com.au/ebusiness/tracking/search";
		TrackingInfo info = new ANLMobileTracking().doTrack(url,map, true);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}
	
	public void testNYKMobileTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		map.put(TrackingAdapter.SVALUE,"NYKU2631914");
		String url = "http://www2.nykline.com/ct/containerSearch.nyk?lang=en&country=USA";
		TrackingInfo info = new NYKMobileTracking().doTrack(url,map, true);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}
	
	
	public void testHMMMobileTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		map.put(TrackingAdapter.SVALUE,"XGWB2613629");
		String url = "http://www.hmm.co.kr/ebiz/track_trace/trackCTP.jsp";
		TrackingInfo info = new HMMMobileTracking().doTrack(url,map, true);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}
	
	
	public void testSAFMoblieTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		map.put(TrackingAdapter.SVALUE,"MRKU8619192");
		String url = "http://mysaf2.safmarine.com/wps/portal/Safmarine/etrackUnregistered";
		TrackingInfo info = new SAFMobileTracking().doTrack(url,map, true);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}
	

	public void testCNCMobileTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		map.put(TrackingAdapter.SVALUE,"ECMU4566729");
		String url = "http://www.cnc-ebusiness.com/ebusiness/tracking/search";
		TrackingInfo info = new CNCMobileTracking().doTrack(url,map, true);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}
	
}
