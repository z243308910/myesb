package com.echounion.boss.Tracking.msc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.SimpleNodeIterator;

import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.entity.dto.TrackingInfo;

public class MSCTracking extends AbstractTrack {

	private Logger logger=Logger.getLogger(MSCTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url,Map<String,Object> params,boolean isPost){
		HttpClient client =getHttpClient();
		params=assembleSelfParams(params);
		TrackingInfo info =new TrackingInfo();
		try
		{
			HttpUriRequest httpSubmit=getRequestSubmit(url, params, isPost);
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				if(params.containsKey("ContainerNumber"))
				{
					return getContainerInfo(entity,params.get("ContainerNumber").toString());
				}
				else
				{
					
				}
			}
		}catch (Exception e) {
			logger.error("MSC 数据采集出错"+e);
		}
		return info;
	}

	private TrackingInfo getContainerInfo(HttpEntity entity,String containerNo)throws Exception
	{
		TrackingInfo info =new TrackingInfo(1);
		info.addHeadTitle("Container Number");
		info.addRowData(containerNo);
		SAXParserFactory factory=SAXParserFactory.newInstance();
		SAXParser saxParser=factory.newSAXParser();
		MSCXmlHanlder hanlder=new MSCXmlHanlder();
		saxParser.parse(entity.getContent(),hanlder);
		String data=hanlder.getContent().toString();

		Parser htmlParser = new Parser(data);
		AndFilter filter = getAndFilter("table", "cellspacing", "5");
		NodeList nodeList = htmlParser.parse(filter);		//获得table集合
		SimpleNodeIterator nodeIt=nodeList.elements();
		Node tempNodeTable=null;
		List<TrackingInfo> subList=new ArrayList<TrackingInfo>();
		TrackingInfo subInfo=null;
		while(nodeIt.hasMoreNodes()) {			//遍历table
			tempNodeTable=nodeIt.nextNode();
			Node tempNodeTr=null;
			NodeList nodeTrList=tempNodeTable.getChildren();
			SimpleNodeIterator nodeTrIt=nodeTrList.elements();	//遍历tr
			subInfo=new TrackingInfo(5);
			while(nodeTrIt.hasMoreNodes())
			{
				tempNodeTr=nodeTrIt.nextNode();
				if(!(tempNodeTr instanceof TableRow))
				{
					continue;
				}
				Node[] tdNodes=tempNodeTr.getChildren().toNodeArray();
				for(int i=0;i<tdNodes.length;i++)
				{
					if(!(tdNodes[i] instanceof TableColumn)){continue;}
					String text=filterHtml(tdNodes[i].toPlainTextString());
					if(text.equals(":")){continue;}
					if(i==0)		//标题
					{
						subInfo.addHeadTitle(text);
					}else if(i==3)	//内容
					{
						subInfo.addRowData(text);
					}
				}
			}
			subList.add(subInfo);
		}
		info.addSubTrackingInfo(subList);
		return info;
	}
	
	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) {
		Map<String,Object> params=new HashMap<String, Object>();
		String type=String.valueOf(map.get(TrackingAdapter.STYPE));			//获得类型
		String value=String.valueOf(map.get(TrackingAdapter.SVALUE));
		if(type.equals(TrackingAdapter.STYPE_BILL))					//bill
		{
			params.put("ContainerNumber", value);
		}
		else if(type.equals(TrackingAdapter.STYPE_BOOKING))			//订餐
		{
			params.put("ContainerNumber", value);
		}else if(type.equals(TrackingAdapter.STYPE_CONTAINER))		//container
		{
			params.put("ContainerNumber", value);
		}
		return params;
	}
}
