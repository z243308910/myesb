package com.echounion.bossmanager.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.echounion.bossmanager.common.constant.Constant;
import com.echounion.bossmanager.common.enums.ProtocolType;


/**
 * 网络工具类
 * @author 胡礼波
 * 2012-11-20 上午11:05:26
 */
public class NetUtil {

	private static Logger logger=Logger.getLogger(NetUtil.class);
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
		if(!ValidateUtil.isUrl(urlStr))
		{
			logger.warn("不合法的URL地址:"+urlStr);
			return null;
		}
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(HTTP.CONTENT_ENCODING,Constant.ENCODEING_UTF8);
		HttpPost httpPost=new HttpPost(urlStr);
		if(!MapUtils.isEmpty(paramsMap))
		{
		httpPost.setEntity(getPostForm(paramsMap));
		}
		logger.info(httpPost.getRequestLine());
		HttpResponse response = client.execute(httpPost);
		HttpEntity entity = response.getEntity();
		String data = EntityUtils.toString(entity,Constant.ENCODEING_UTF8);
		EntityUtils.consume(entity);
		client.getConnectionManager().shutdown();
		return data;
	}
	
	
	/**
	 * POST方法组装参数---作废的方法
	 * @author 胡礼波
	 * 2012-11-23 上午11:10:41
	 * @param params
	 * @return
	 */
	@Deprecated
	public static MultipartEntity getPostForm1(Map<String,Object> params)
	{
		MultipartEntity entity=new MultipartEntity();
		try {
			for (String key:params.keySet()) {
				if(params.get(key) instanceof File)			//文件类型
				{
					entity.addPart("dataFile",new FileBody((File)params.get(key)));
				}
				else
				{
					StringBody sb=new StringBody(String.valueOf(params.get(key)),Charset.forName(Constant.ENCODEING_UTF8));
					entity.addPart(key,sb);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
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
	 * @return
	 */
	public static String getClientIp(HttpServletRequest request)
	{
		if(request==null)
		{
			return null;
		}
		String ip=null;
		try
		{
			ip=request.getHeader("x-forwarded-for");
			if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				 ip = request.getHeader("Proxy-Client-IP");
			}
			if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				 ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			     ip = request.getRemoteAddr();
			}
		}catch (Exception e) {
			logger.warn("获取客户端IP失败",e);
		}
		return ip;
	}
	
	/**
	 * 根据协议组装协议地址
	 * @author 胡礼波
	 * 2013-7-23 下午5:13:36
	 * @param type
	 * @param ip
	 * @param port
	 * @return
	 */
	public static String getProtolUrl(ProtocolType type,String ip,int port)
	{
		if(type==ProtocolType.HTTP)
		{
			return "http://"+ip+":"+port;
		}
		return null;
	}
}
