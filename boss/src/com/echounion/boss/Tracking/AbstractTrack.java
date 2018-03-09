package com.echounion.boss.Tracking;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.SimpleNodeIterator;

import com.echounion.boss.common.util.FileUtil;
import com.echounion.boss.entity.dto.TrackingInfo;

/**
 * 采集数据抽象类
 * @author 胡礼波
 * 2:21:58 PM Sep 27, 2012
 */
public abstract class AbstractTrack implements ITracking {

	/**
	 * 执行采集数据
	 * @author 胡礼波
	 * 2:21:55 PM Sep 27, 2012 
	 * @param isPost是否是post提交 true为post提交 false为get提交
	 * @param params 提交的参数
	 * @return
	 */
	public abstract TrackingInfo doTrack(String url,Map<String,Object> params, boolean isPost)throws Exception;
	
	/**
	 * 添加过滤条件,得到过滤掉指定标签属性的对象
	 * @author 胡礼波
	 * @param tagName 标签名称 example:table
	 * @param attributeName 属性名称 example:class
	 * @param attributeValue 属性值 example: .clr
	 * @return
	 */
	public AndFilter getAndFilter(String tagName, String attributeName,
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
	
	
	/**
	 * 添加过滤条件,得到过滤掉指定标签属性的对象
	 * @author 胡礼波
	 * @param tagName 标签名称 example:table
	 * */
	public AndFilter getAndFilter(String tagName)
	{
		return getAndFilter(tagName,null,null);
	}
	
	/**
	 * 过滤Node获得指定类型的NodeList
	 * @author 胡礼波
	 * 2:42:21 PM Sep 27, 2012 
	 * @param nodeList
	 * @param classes 指定类型的Node
	 * @return
	 */
	public NodeList filterNode(NodeList nodeList, Class<?> classes) {
		if(nodeList==null)
		{
			return nodeList;
		}
		NodeList list = new NodeList();
		
		SimpleNodeIterator it = nodeList.elements();
		Node node = null;
		while (it.hasMoreNodes()) {
			node = it.nextNode();
			if (node.getClass() == classes) {
				list.add(node);
			}
		}
		return list;
	}

	/**
	 * 组装标题
	 * @author 胡礼波
	 * 2:41:45 PM Sep 27, 2012 
	 * @param nodeList 同种类型的Node
	 * @return Node中的Text文本值的List集合
	 */
	public List<String> getTitles(NodeList nodeList) {
		List<String> list = new ArrayList<String>();
		for (Node node : nodeList.toNodeArray()) {
			String text = node.toPlainTextString().trim();
			text=filterHtml(text);
			list.add(text);
		}
		return list;
	}

	/**
	 * 组装数据
	 * @author 胡礼波
	 * 2:41:02 PM Sep 27, 2012 
	 * @param nodeList 同种类型的Node
	 * @return Node中的Text文本值 的list集合
	 */
	public List<String> getDataList(NodeList nodeList) {
		List<String> list = new ArrayList<String>();
		Node node = null;
		SimpleNodeIterator it = nodeList.elements();
		while (it.hasMoreNodes()) {
			node = it.nextNode();
			String text =null;
			if(node==null)
			{
				text="";
			}
			else
			{
			text= node.toPlainTextString().trim();
			}
			text=filterHtml(text);
			list.add(text);
		}
		return list;
	}
	
	public String filterHtml(String data)
	{
		String reg=FileUtil.isWindows()? "\r\n":"\n";
		Pattern pattern=Pattern.compile(reg);
		Matcher matcher=pattern.matcher(data);
		while(matcher.find())
		{
			data=matcher.replaceAll("");
		}
		data=data.replaceAll("\t","");
		data=data.replaceAll("&nbsp;","");
		return data.trim();
	}
	
	
	/**
	 * 切分NodeList
	 * 
	 * @param length
	 *            切多少个
	 * @param index
	 *            从第几个索引开始，截取length个对象组成nodeList对象
	 * @param nodeList
	 * @return
	 */
	public NodeList cutNodeList(int index, int length, NodeList nodeList) {
		NodeList list = new NodeList();
		Node node = null;
		for (int i = index; i < nodeList.size(); i++) {
			node = nodeList.elementAt(i);
			list.add(node);
			if (list.size() >= length) {
				break;
			}
		}
		return list;
	}

	/**
	 * 切分NodeList
	 * 
	 * @param index
	 *            从第几个索引开始,截取从这个索引以后的所有对象
	 * @param nodeList
	 * @return
	 */
	public NodeList cutNodeList(int index, NodeList nodeList) {
		NodeList list = new NodeList();
		Node node = null;
		for (int i = index; i < nodeList.size(); i++) {
			node = nodeList.elementAt(i);
			if (node == null) {
				i--;
				continue;
			}
			list.add(node);
		}
		return list;
	}
	
	/**
	 * 获得相应的请求对象
	 * @author 胡礼波
	 * 2012-11-23 上午11:20:47
	 * @param url
	 * @param params
	 * @param isPost
	 * @return
	 */
	public HttpUriRequest getRequestSubmit(String url,Map<String,Object> params,boolean isPost)
	{
		HttpUriRequest httpSubmit=null;
		
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
		return httpSubmit;
	}
	
	public HttpUriRequest getRequestSubmit(String url,boolean isPost)
	{
		return getRequestSubmit(url,new HashMap<String, Object>(), isPost);
	}
	
	/**
	 * 获得HttpClient
	 * @author 胡礼波
	 * Dec 7, 2012 10:52:20 AM
	 * @return
	 */
	public HttpClient getHttpClient()
	{
		HttpClient client = new DefaultHttpClient();
		return client;
	}
	
	 /**
     * 获取可信任https链接，以避免不受信任证书出现peer not authenticated异常
     * @param base
     * @return
     */
    @SuppressWarnings("deprecation")
	public static HttpClient getHttpsClient() {
        try {
        	HttpClient base = new DefaultHttpClient();
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] xcs,String string) { }
                public void checkServerTrusted(X509Certificate[] xcs, String string) {}
                public X509Certificate[] getAcceptedIssuers() {return null;}
            };
            ctx.init(null, new TrustManager[] {tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = base.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", ssf, 443));
            return new DefaultHttpClient(ccm, base.getParams());
        } catch (Exception e) {
        	e.printStackTrace();
            return new DefaultHttpClient();
        }
    }
	
	/**
	 * GET方法把参数拼接到URL中
	 * @author 胡礼波
	 * 2012-11-3 下午05:52:32
	 * @param params 参数
	 * @return
	 */
    
	public String getGetURL(String url,Map<String,Object> params)
	{
		StringBuilder sb=new StringBuilder(url);
		sb.append("?");
		try {
			for (String key:params.keySet()) {
				sb.append(key);
				sb.append("=");				
				sb.append(URLEncoder.encode(params.get(key).toString(),"UTF-8"));
				sb.append("&");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String str = sb.toString();
		return str.substring(0,str.length()-1);
	}
	
	
	/**
	 * POST方法组装参数
	 * @author 胡礼波
	 * 2012-11-23 上午11:10:41
	 * @param params
	 * @return
	 */
	public StringEntity getPostForm(Map<String,Object> params)
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
	
	/**
	 * 根据传入的参数重新组装成各个公司自己的参数格式
	 * @author 胡礼波
	 * 2012-11-23 上午11:39:15
	 * @param map
	 * @return
	 */
	public abstract Map<String,Object> assembleSelfParams(Map<String,Object> map);
}
