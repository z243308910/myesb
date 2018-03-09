package com.echounion.boss.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.echounion.boss.common.constant.Constant;

/**
 * 网络工具类
 * @author 胡礼波
 * 2012-11-20 上午11:05:26
 */
public class NetUtil {

	/**
	 * 执行网络操作
	 * @author 胡礼波
	 * 2012-11-20 上午11:07:38
	 * @param url
	 * @return
	 * @throws MalformedURLException 
	 */
	public static String doNet(String urlStr,Map<String,Object> paramsMap)throws IOException
	{
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(HTTP.CONTENT_ENCODING,Constant.ENCODEING_UTF8);
		HttpPost httpPost=new HttpPost(urlStr);
		httpPost.setEntity(getPostForm(paramsMap));
		HttpResponse response = client.execute(httpPost);
		HttpEntity entity = response.getEntity();
		String data = EntityUtils.toString(entity,Constant.ENCODEING_UTF8);
		return data;
	}
	
	/**
	 *  执行网络操作
	 * @author 胡礼波
	 * 2013-3-17 下午2:39:59
	 * @param urlStr
	 * @param paramsMap
	 * @return
	 * @throws IOException
	 */
	public static String doNetByGet(String urlStr,Map<String,Object> paramsMap)throws IOException
	{
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(HTTP.CONTENT_ENCODING,Constant.ENCODEING_UTF8);
		HttpGet httpGet=new HttpGet(getGetURL(urlStr,paramsMap));
		HttpResponse response = client.execute(httpGet);
		HttpEntity entity = response.getEntity();
		String data = EntityUtils.toString(entity,Constant.ENCODEING_UTF8);
		return data;
	}
	
	/**
	 * Get提交组装URL
	 * @author 胡礼波
	 * 2013-3-17 下午2:42:42
	 * @param url
	 * @param params
	 * @return
	 */
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
	
	/**
	 * POST方法组装参数
	 * @author 胡礼波
	 * 2012-11-23 上午11:10:41
	 * @param params
	 * @return
	 */
	public static StringEntity getPostForm(Map<String,Object> params)
	{
		List<NameValuePair> list=new ArrayList<NameValuePair>();
		for (String key:params.keySet()) {
			list.add(new BasicNameValuePair(key,String.valueOf(params.get(key))));			
		}
		StringEntity entity=null;
		try {
			entity = new UrlEncodedFormEntity(list,Constant.ENCODEING_UTF8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	
	/**
	 * 采用JDK的 HTTPCONNECTION远程调用
	 * @author 胡礼波
	 * 2012-11-24 下午04:07:03
	 * @param urlStr
	 * @param paramsMap
	 * @return
	 * @throws IOException
	 */
	@Deprecated
	public static String doNetByHttpConnection(String urlStr,Map<String,Object> paramsMap)throws IOException
	{
		URL url=new URL(urlStr);
		HttpURLConnection httpUrlConn=(HttpURLConnection)url.openConnection();
		httpUrlConn.setRequestMethod("POST");
		httpUrlConn.setDoInput(true);
		httpUrlConn.setDoOutput(true);
		if(MapUtils.isNotEmpty(paramsMap))
		{
			Iterator<String> it=paramsMap.keySet().iterator();
			StringBuilder sbparams=new StringBuilder();
			String key=null;
			while(it.hasNext())
			{
				key=it.next();
				sbparams.append(key+"=");
				sbparams.append(URLEncoder.encode(paramsMap.get(key).toString(),Constant.ENCODEING_UTF8));
				sbparams.append("&");
			}
			sbparams=sbparams.deleteCharAt(sbparams.length()-1);
			byte[] paramsbyte=sbparams.toString().getBytes();
			httpUrlConn.getOutputStream().write(paramsbyte,0,paramsbyte.length);
			httpUrlConn.getOutputStream().flush();
			httpUrlConn.getOutputStream().close();
		}
		BufferedReader reader=new BufferedReader(new InputStreamReader(httpUrlConn.getInputStream()));
		String data=null;
		StringBuffer sb=new StringBuffer();
		while((data=reader.readLine())!=null)
		{
			sb.append(data);
		}
		reader.close();
		httpUrlConn.disconnect();
		return sb.toString();
	}
	
	
	/**
	 * 获得客户端请求的IP
	 * @param request
	 * @author 胡礼波
	 * 2012-10-28 下午5:03:48
	 * @return
	 */
	public static String getClientIp(HttpServletRequest request)
	{
		String ip=request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			 ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			 ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		     ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 获得当前线程中的IP地址
	 * @author 胡礼波
	 * 2013-2-28 下午5:03:48
	 * @return
	 */
	public static String getThreadLocalIp()
	{
		try
		{
			Object obj=ThreadLocalUtil.getData();
			if(obj!=null)
			{
				return String.valueOf(obj);
			}
		}
		finally
		{
			ThreadLocalUtil.remove();
		}
		return null;
	}
}
