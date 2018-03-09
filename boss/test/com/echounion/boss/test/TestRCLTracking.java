package com.echounion.boss.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.Tracking.acl.ACLTracking;
import com.echounion.boss.Tracking.cscl.CSCLTracking;
import com.echounion.boss.Tracking.dys.DYSTracking;
import com.echounion.boss.Tracking.esl.EslTracking;
import com.echounion.boss.Tracking.gss.GSSTracking;
import com.echounion.boss.Tracking.has.HASTracking;
import com.echounion.boss.Tracking.hjs.HJSTracking;
import com.echounion.boss.Tracking.hmm.HMMTracking;
import com.echounion.boss.Tracking.kmtc.KMTCTracking;
import com.echounion.boss.Tracking.lt.LTTracking;
import com.echounion.boss.Tracking.matson.MATSONTracking;
import com.echounion.boss.Tracking.mcc.MCCTracking;
import com.echounion.boss.Tracking.msk.MskTracking;
import com.echounion.boss.Tracking.msl.MSLTracking;
import com.echounion.boss.Tracking.nds.NDSTracking;
import com.echounion.boss.Tracking.nordan.NORDANTracking;
import com.echounion.boss.Tracking.nss.NSSTracking;
import com.echounion.boss.Tracking.pcl.PCLTracking;
import com.echounion.boss.Tracking.pil.PILTracking;
import com.echounion.boss.Tracking.rcl.RCLTracking;
import com.echounion.boss.Tracking.sinokor.SINOKORTracking;
import com.echounion.boss.Tracking.sitc.SITCTracking;
import com.echounion.boss.Tracking.snl.SNLTracking;
import com.echounion.boss.Tracking.stx.STXTracking;
import com.echounion.boss.Tracking.whl.WHLTracking;
import com.echounion.boss.Tracking.yml.YMLTracking;
import com.echounion.boss.common.json.JsonUtil;
import com.echounion.boss.entity.dto.TrackingInfo;

public class TestRCLTracking {
	
	@Test
	public void TestWHLTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		map.put(TrackingAdapter.SVALUE,"0354A07505");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_BILL);
/*		map.put(TrackingAdapter.SVALUE,"TGHU2909290");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_CONTAINER);*/
		String url="http://web.wanhai.com/index_whl.jsp?web=whlwww&file_num=15040&i_url=whlwww/cargoTrackList.jsp";
		TrackingInfo info = new WHLTracking().doTrack(url,map, true);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}

	public void TestSINOKORTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		/*map.put(TrackingAdapter.SVALUE,"SNKO024121202001");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_BILL);*/
		map.put(TrackingAdapter.SVALUE,"CAXU6524863");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_CONTAINER);
		String url="http://www.sinokor.co.kr/service/cntrTrack/cntr_tracking.asp";
		TrackingInfo info = new SINOKORTracking().doTrack(url,map, true);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}
	
	public void TestSITCTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		map.put(TrackingAdapter.SVALUE,"TEMU6453289");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_CONTAINER);
		/*map.put(TrackingAdapter.SVALUE,"SITGSHNOK02662");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_BILL);*/
		String url="http://www.sitcline.com/track/biz/trackCargoTrack.do";
		TrackingInfo info = new SITCTracking().doTrack(url,map, true);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}
	

	public void TestMATSONTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		map.put(TrackingAdapter.SVALUE,"MATU251213");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_CONTAINER);
		/*map.put(TrackingAdapter.SVALUE,"MATS8636879000");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_BILL);*/
		String url="https://www.matson.com/vcsc/VisibilityController";
		TrackingInfo info = new MATSONTracking().doTrack(url,map, true);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}
	
	
	public void TestESLTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		map.put(TrackingAdapter.SVALUE,"EPIRCHNTAO144476");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_BILL);
		/*map.put(TrackingAdapter.SVALUE,"AAAU2003129");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_CONTAINER);*/
		String url="http://www.emiratesline.com/cargotracking/cargomovement.html";
		TrackingInfo info = new EslTracking().doTrack(url,map, true);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}
	
	public void TestCSCLTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		map.put(TrackingAdapter.SVALUE,"CSVLKZSA0182");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_BILL);
	/*	map.put(TrackingAdapter.SVALUE,"CCLU7174140");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_CONTAINER);*/
		String url="http://222.66.158.204/cargo_track/cargo_track_rst_pre.jsp";
		TrackingInfo info = new CSCLTracking().doTrack(url,map, true);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}
	
	public void TestMSKTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
/*		map.put(TrackingAdapter.SVALUE,"CLKUABJ1300086");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_BILL);*/
		map.put(TrackingAdapter.SVALUE,"PONU0384452");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_CONTAINER);
		String url="http://my.maerskline.com/appmanager/maerskline/public?_nfpb=true&_windowLabel=portlet_trackSimple_1&portlet_trackSimple_1_actionOverride=/portlets/tracking3/track/trackSimple/trackSimple&_pageLabel=page_tracking3_trackSimple";
		TrackingInfo info = new MskTracking().doTrack(url,map, true);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}
	

	public void TestPILTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
/*		map.put(TrackingAdapter.SVALUE,"CLKUABJ1300086");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_BILL);*/
		map.put(TrackingAdapter.SVALUE,"PCIU2893617");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_CONTAINER);
		String url="https://www.pilship.com/shared/ajax/";
		TrackingInfo info = new PILTracking().doTrack(url,map, false);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}
	

	public void TestMCCTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		map.put(TrackingAdapter.SVALUE,"MCC378315");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_BILL);
		String url="http://iframe.mcc.com.sg/tracking/results/";
		TrackingInfo info = new MCCTracking().doTrack(url,map, false);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}
	
	public void TestHMMTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		map.put(TrackingAdapter.SVALUE,"BMOU4587330");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_CONTAINER);
		/*map.put(TrackingAdapter.SVALUE,"XGWB2613629");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_BOOKING);*/
		String url="http://www.hmm.co.kr/ebiz/track_trace/trackCTP.jsp";
		TrackingInfo info = new HMMTracking().doTrack(url,map, true);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void TestNDSTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		/*map.put(TrackingAdapter.SVALUE,"CRxu3271920");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_CONTAINER);*/
		map.put(TrackingAdapter.SVALUE,"GU1011634");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_BOOKING);
		String url="https://www.niledutch.com/en/e-services/quick-tracking-full-tracking.aspx";
		TrackingInfo info = new NDSTracking().doTrack(url,map, true);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}
	
	public void TestGSSTrackingInfo()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		map.put(TrackingAdapter.SVALUE,"12BGN0001");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_CONTAINER);
		String url="http://exn.griegstar.com/cargotracking/index.php";
		TrackingInfo info = new GSSTracking().doTrack(url,map, true);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}
	
	public void TestSNLTrackingInfo()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		/*map.put(TrackingAdapter.SVALUE,"SNL2YTJLP20010");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_BILL);*/
		
		map.put(TrackingAdapter.SVALUE,"CRXU3247918");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_CONTAINER);	
		String url="http://ebusiness.sinolines.com/ebusiness/cargotracking.aspx";
		TrackingInfo info = new SNLTracking().doTrack(url,map, true);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}
	
	public void TestNSSTrackingInfo()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		/*map.put(TrackingAdapter.SVALUE,"NSSLPBCYB6602016");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_BILL);*/
		map.put(TrackingAdapter.SVALUE,"CAXU4628869");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_CONTAINER);
		String url="http://www.namsung.co.kr/kor/eservice/trace.ns.tsp";
		TrackingInfo info = new NSSTracking().doTrack(url,map, true);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}
	
	public void TestHJSTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		/*map.put(TrackingAdapter.SVALUE,"CHI306760400");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_BILL);*/
		map.put(TrackingAdapter.SVALUE,"TRLU8917919");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_CONTAINER);		
		String url="http://www.hanjin.com/hanjin/CUP_HOM_3301GS.do";
		TrackingInfo info = new HJSTracking().doTrack(url,map, true);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}

	public void TestMSLTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		map.put(TrackingAdapter.SVALUE,"SHTCQM194F145");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_BILL);
		/*map.put(TrackingAdapter.SVALUE,"MSLU2120646");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_CONTAINER);*/
		String url="http://www.mssco.net/Eip/UserControl/SetClientInfo.aspx";
		TrackingInfo info = new MSLTracking().doTrack(url,map, true);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}
	
	public void TestKMTCTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		map.put(TrackingAdapter.SVALUE,"TAO0577723");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_BILL);
		/*map.put(TrackingAdapter.SVALUE,"CN01137345");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_BOOKING);
		map.put(TrackingAdapter.SVALUE,"GESU2370483");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_CONTAINER);*/
		String url="http://www.ekmtc.com/CCIT100/searchContainerList.do";		
		TrackingInfo info = new KMTCTracking().doTrack(url,map, false);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}
	

	public void TestPCLTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		map.put(TrackingAdapter.SVALUE,"GESU5834382");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_CONTAINER);
		/*map.put(TrackingAdapter.SVALUE,"JT727EN06");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_BILL);*/
		String url="http://www.pancon.co.kr/pcl/tracking/bl";		
		TrackingInfo info = new PCLTracking().doTrack(url,map, false);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);	
	}
	

	public void TestNORDANTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		/*map.put(TrackingAdapter.SVALUE,"NODU2732684");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_CONTAINER);
		String url="http://66.0.33.170/cgi-bin/WEQ001.PGM";*/
		map.put(TrackingAdapter.SVALUE,"S134STCBAL01");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_BILL);
		String url="http://66.0.33.170/cgi-NU/WCS001.PGM";		
		TrackingInfo info = new NORDANTracking().doTrack(url,map, true);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);	
	}
	
	public void TestLTTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		/*map.put(TrackingAdapter.SVALUE,"EGHU1019744");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_CONTAINER);*/
		map.put(TrackingAdapter.SVALUE,"050200199494");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_BOOKING);
		String url="http://www.shipmentlink.com/servlet/TDB1_CargoTracking.do";
		TrackingInfo info = new LTTracking().doTrack(url,map, true);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}
	

	public void TestHASTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		map.put(TrackingAdapter.SVALUE,"CRSU1417021");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_CONTAINER);
	/*	map.put(TrackingAdapter.SVALUE,"HASL04QUD2EAC51");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_BILL);*/
		String url="http://ebiz.heung-a.co.kr/chinese/cargotrace.cfm";
		TrackingInfo info = new HASTracking().doTrack(url,map, true);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}
	

	public void testDYSTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		map.put(TrackingAdapter.SVALUE,"PCSLNIL066120609");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_BILL);
		String url="http://www.pcsline.co.kr/eng/ebusiness/ebusiness01.asp";
		TrackingInfo info = new DYSTracking().doTrack(url,map, true);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}
	
	
	public void testACLTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		map.put(TrackingAdapter.SVALUE,"GCNU4668351");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_BILL);
		String url="http://www.aclcargo.com/trackCargo.php";
		TrackingInfo info = new ACLTracking().doTrack(url,map, true);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}


	public void testYMLTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();	
		
	 	map.put(TrackingAdapter.SVALUE,"YMLU6810620");
	 	map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_CONTAINER);
		String url="http://www.yangming.com/english/ASP/e-service/track_trace/CTCONNECT.ASP";
		/*map.put(TrackingAdapter.SVALUE,"Z230290539");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_BILL);
		String url="http://www.yangming.com/english/ASP/e-service/track_trace/blconnect.asp";*/
		TrackingInfo info = new YMLTracking().doTrack(url,map, true);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}
	

	public void testSTXTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		map.put(TrackingAdapter.SVALUE,"STXU4512893");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_CONTAINER);	
	/*	map.put(TrackingAdapter.SVALUE,"TYO140200051");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_BILL);	*/
		String url="http://container.panocean.com/HP2401/hp2401List.stx";
		TrackingInfo info = new STXTracking().doTrack(url,map, true);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}
	

	public void testRCLTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		/*map.put(TrackingAdapter.SVALUE,"HKGCW14001243");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_BILL);*/
		map.put(TrackingAdapter.SVALUE,"TGHU6516809");
		map.put(TrackingAdapter.STYPE,TrackingAdapter.STYPE_CONTAINER);
		String url="http://www.rclgroup.com/CargoTracking.asp";
		TrackingInfo info = new RCLTracking().doTrack(url,map, true);
		String str = JsonUtil.toJsonString(info);
		System.out.println(str);
	}
}
