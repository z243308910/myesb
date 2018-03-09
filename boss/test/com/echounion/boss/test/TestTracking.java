package com.echounion.boss.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.Tracking.anl.ANLTracking;
import com.echounion.boss.Tracking.cma.CMATracking;
import com.echounion.boss.Tracking.emc.EMCTracking;
import com.echounion.boss.Tracking.esl.EslTracking;
import com.echounion.boss.Tracking.hmm.HMMTracking;
import com.echounion.boss.Tracking.mcc.MCCTracking;
import com.echounion.boss.Tracking.nyk.NYKTracking;
import com.echounion.boss.Tracking.saf.SafTracking;
import com.echounion.boss.Tracking.uasc.UASCTracking;
import com.echounion.boss.Tracking.zim.ZIMTracking;
import com.echounion.boss.entity.dto.TrackingInfo;

public class TestTracking {

	@Test
	public void testTracking()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("stype",TrackingAdapter.STYPE_BILL);
		map.put("svalue","MCC333217");
		TrackingInfo Info=new MCCTracking().doTrack("https://www.pilship.com/pilweb/eservices/trackresult.jsp",map, true);
		System.out.println(Info);
	}
	
	public static void main(String[] args) throws Exception, IOException {
		
		HttpUriRequest httpSubmit=null;
		boolean isPost=true;
		String url="http://www.hmm.co.kr/ebiz/track_trace/trackCTP.jsp";
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("numbers","XGWB2613629");
		
		if(!isPost)				//Get提交
		{
			httpSubmit= new HttpGet(getGetURL(url,params));
		}
		else					//Post提交
		{
			HttpPost httpPost=new HttpPost(url);
			httpPost.setEntity(getPostForm(params));
			httpSubmit=httpPost;
		}
		httpSubmit.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,10*1000);
//		httpSubmit.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//		httpSubmit.setHeader("Accept-Language","zh-CN,zh;q=0.8");
//		httpSubmit.setHeader("Cache-Control","max-age=0");
//		httpSubmit.setHeader("Connection","keep-alive");
//		httpSubmit.setHeader("Cookie","saf-ip-address=183.37.232.227; Saf_Language_Global=zh; sessionid=8E7FF96E459D307248D0F0B0799EBEB42B5EF9A920B073C34E3EB526E956EC6884C03409148B9520F0B070B070B070B070B070B070B070B070B070B0; JSESSIONID=0000wJxMq7WpUeVINQfys40q-nH:181drp8g1; mysaf22PortalselectedServer=mysaf22Portal1; JSESSIONIDWCMDK=0000inQ5PZQtR6ccTG5Op-qVIOS:181otqr51; mysaf2wpsselectedServer=mysaf2wcm1; language=en_US; user_name=null; __utma=261924497.1727289134.1387875091.1387964417.1390440525.3; __utmb=261924497.9.10.1390440525; __utmc=261924497; __utmz=261924497.1387875091.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __utma=204121423.111677598.1387875081.1387964407.1390440464.3; __utmb=204121423.11.10.1390440464; __utmc=204121423; __utmz=204121423.1387875081.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); portalFlyoutIsOpen=null; portalOpenFlyout=null");
//		httpSubmit.setHeader("Host","www.hmm.co.kr");
		httpSubmit.setHeader("Referer","http://www.hmm.co.kr/");
		httpSubmit.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1573.2 Safari/537.36");

	
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(httpSubmit);
		HttpEntity entity = response.getEntity();
		String data = EntityUtils.toString(entity);
		System.out.println(data);
		client.getConnectionManager().shutdown();
	}
	
	
	public static String getGetURL(String url,Map<String,Object> params)
	{
		StringBuilder sb=new StringBuilder(url);
		sb.append("?");
		for (String key:params.keySet()) {
			sb.append(key);
			sb.append("=");
			sb.append(params.get(key));
			sb.append("&");
		}
		return sb.toString();
	}
	
	public static StringEntity getPostForm(Map<String,Object> params)
	{
		List<NameValuePair> list=new ArrayList<NameValuePair>();
		for (String key:params.keySet()) {
			list.add(new BasicNameValuePair(key,String.valueOf(params.get(key))));			
		}
		StringEntity entity=null;
		try {
			entity = new UrlEncodedFormEntity(list);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return entity;
	}
}
