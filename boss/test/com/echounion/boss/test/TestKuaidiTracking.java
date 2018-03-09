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

import com.echounion.boss.Tracking.kuaidi.KuaidiTracking;
import com.echounion.boss.common.json.JsonUtil;
import com.echounion.boss.entity.dto.TrackingInfo;

public class TestKuaidiTracking {
	
		@Test
		public void testKuaidiTracking()
		{
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("type", "shentong");
			map.put("code","668031148649");
			
			String url="http://www.kuaidi100.com/query";
		
			TrackingInfo info=new KuaidiTracking().doTrack(url,map, true);
			String str = JsonUtil.toJsonString(info);
			System.out.println(str);
		}
		
		public static void main(String[] args) throws Exception, IOException {
			
			HttpUriRequest httpSubmit=null;
			boolean isPost=true;
			String url="http://www.kuaidi100.com/query";
			Map<String,Object> params=new HashMap<String, Object>();
			params.put("type","shentong");
			params.put("postid", "668031148649");
			
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

