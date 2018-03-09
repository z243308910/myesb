package com.echounion.boss.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.junit.Test;

import com.echounion.boss.Tracking.vessel.VesselAdapter;
import com.echounion.boss.Tracking.vessel.VesselTracking;
import com.echounion.boss.entity.dto.TrackingInfo;

public class TestChuanqiTracking {

	@Test
	public void testTracking()
	{
		String url = "http://www.shippingazette.com/imp_exp/voyage_list.asp";
		Map<String,Object> params=new HashMap<String, Object>();
		params.put(VesselAdapter.POL, "90205000463");
		TrackingInfo  info = new VesselTracking().doTrack(url, params, false);
		System.out.println(info);
	}
	
	public static void main(String[] args) throws Exception, IOException {
		
		HttpUriRequest httpSubmit=null;
		boolean isPost=false;
		String url1 = "http://www.shippingazette.com/imp_exp/voyage_list.asp";
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("from_port_id", "90205001169");
		params.put("to_port_id", "90205001169");
		params.put("range", 200);
		params.put("is_export", 1);
		params.put("encode","gb");
		
		if(!isPost)				//Get提交
		{
			httpSubmit= new HttpGet(getGetURL(url1,params));
		}
		else					//Post提交
		{
			HttpPost httpPost=new HttpPost(url1);
			httpPost.setEntity(getPostForm(params));
			httpSubmit=httpPost;
		}
		httpSubmit.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,10*1000);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(httpSubmit);
		HttpEntity entity = response.getEntity();
		BufferedReader reader=new BufferedReader(new InputStreamReader(entity.getContent(),"GBK"));
		String data=null;
		StringBuffer sb=new StringBuffer();
		while((data=reader.readLine())!=null)
		{
			sb.append(data);
		}
		Parser parser = new Parser(sb.toString());
		NodeFilter filter = new TagNameFilter("table");
		NodeList nodeList = parser.parse(filter);
		System.out.println(nodeList.toHtml());
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
	
	public static AndFilter getAndFilter(String tagName, String attributeName,
			String attributeValue) {
		AndFilter filter =null;
		if(tagName==null)
		{
			filter=new AndFilter();
		}
		else if(attributeName==null)
		{
			filter = new AndFilter(new NodeFilter[]{new TagNameFilter(tagName)});	
		}
		else
		{
			filter = new AndFilter(new TagNameFilter(tagName),new HasAttributeFilter(attributeName, attributeValue));
		}
		return filter;
	}
}
