package com.echounion.boss.Tracking.hmm;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableHeader;
import org.htmlparser.tags.TableRow;
import org.htmlparser.util.NodeList;

import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.common.json.JsonUtil;
import com.echounion.boss.entity.dto.TrackingInfo;

public class HMMTracking extends AbstractTrack {

	private Logger logger=Logger.getLogger(HMMTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url, Map<String, Object> params,boolean isPost){
		HttpClient client =getHttpClient();
		String stype=params.get(TrackingAdapter.STYPE).toString();
		String value=assembleSelfParamsValue(params);
		TrackingInfo info =new TrackingInfo();
		try
		{
			HttpUriRequest httpSubmit=getRequestSubmit(url,value,isPost,stype);
			httpSubmit.setHeader("Referer", "http://www.hmm.co.kr");		
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			info= new TrackingInfo(); // 总信息
			info.setTitle("HMM Tracking Result");
			if (entity != null) {
				String data = EntityUtils.toString(entity);
				info=getBill(data, info);
			}
		}catch (Exception e) {
			logger.error("HMM 数据采集出错"+e);
		}
		logger.info("HMM抓取完成！"+JsonUtil.toJsonString(info));
		return info;
	}

	public TrackingInfo getBill(String data,TrackingInfo info)throws Exception
	{
		AndFilter filter = getAndFilter("table", "class", "business02 ty03");
		Parser parser = new Parser(data);
		NodeList nodeList = parser.parse(filter);
		if(nodeList!=null)
		{
			List<TrackingInfo> subInfoList=new ArrayList<>();
			TrackingInfo subInfo=null;
			for(int i=0;i<nodeList.size();i++)
			{
				NodeList trNodeList = filterNode(nodeList.elementAt(i).getChildren(), TableRow.class);				// 获得tr类型的nodeList
				Node trNode=null;
				subInfo=new TrackingInfo();
				subInfoList.add(subInfo);
				for(int j=0;j<trNodeList.size();j++)										//遍历tr行
				{
					trNode=trNodeList.elementAt(j);
					if(i==0)							//主单
					{
						if(j==0)
						{
							NodeList thNodeList=filterNode(trNode.getChildren(), TableHeader.class);
							info.setHeadTitles(getTitles(cutNodeList(0,thNodeList)));					
						}else
						{
							NodeList tdNodeList=filterNode(trNode.getChildren(), TableColumn.class);
							info.addRowDataList(getDataList(cutNodeList(0,tdNodeList)));					
						}
					}else
					{
						switch(i)
						{
						case 1:
							subInfo.setTitle("Current Location");
							break;
						case 2:
							subInfo.setTitle("Vessel Movement");
							break;
						case 3:
							subInfo.setTitle("Shipment Progress");
							break;
						}
						if(j==0)
						{
							NodeList thNodeList=filterNode(trNode.getChildren(), TableHeader.class);
							subInfo.setHeadTitles(getTitles(cutNodeList(0,thNodeList)));					
						}else
						{
							NodeList tdNodeList=filterNode(trNode.getChildren(), TableColumn.class);
							subInfo.addRowDataList(getDataList(cutNodeList(0,tdNodeList)));					
						}
					}
				}
			}
			info.addSubTrackingInfo(subInfoList);
		}
		return info;
	}
	
	public HttpUriRequest getRequestSubmit(String url,String value,boolean isPost,String stype)
	{
		HttpUriRequest httpSubmit=null;
		HttpPost httpPost=new HttpPost(url);
		httpPost.setEntity(getPostForm(value,stype));
		httpSubmit=httpPost;
		httpSubmit.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,10*1000);
		return httpSubmit;
	}
	
	public StringEntity getPostForm(String value,String stype)
	{
		List<NameValuePair> list=new ArrayList<NameValuePair>();
		if(stype.equals(TrackingAdapter.STYPE_CONTAINER))
		{
			for(int i=2;i<=24;i++)
			{
				list.add(new BasicNameValuePair("numbers",""));
			}
			list.set(11,new BasicNameValuePair("numbers",value));
		}else
		{
			list.add(new BasicNameValuePair("numbers",value));
		}
		StringEntity entity=null;
		try {
			entity = new UrlEncodedFormEntity(list);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	public String assembleSelfParamsValue(Map<String, Object> map) {
		String value=String.valueOf(map.get(TrackingAdapter.SVALUE));
		return value;
	}

	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) {
		return null;
	}

}
