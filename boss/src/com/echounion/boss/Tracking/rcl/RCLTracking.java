package com.echounion.boss.Tracking.rcl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;

import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.common.json.JsonUtil;
import com.echounion.boss.entity.dto.TrackingInfo;

public class RCLTracking extends AbstractTrack{

	private Logger logger=Logger.getLogger(RCLTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url, Map<String, Object> params, boolean isPost){
		HttpClient client =getHttpClient();
		params=assembleSelfParams(params);
		TrackingInfo info =new TrackingInfo();
		try
		{
			HttpUriRequest httpSubmit=getRequestSubmit(url, params, isPost);
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			info=getTrackInfo(entity);			
		}catch (Exception e) {
			logger.error("RCL 数据采集出错"+e);
		}
		logger.debug(JsonUtil.toJsonString(info));
		return info;
	}
	
	private TrackingInfo getTrackInfo(HttpEntity entity)throws Exception
	{
		TrackingInfo info =null;
		info= new TrackingInfo(3);
		info.setTitle("RCL Tracking Result");
		if (entity != null) {
			String data = EntityUtils.toString(entity);
			Parser parser = new Parser(data);
			AndFilter filter = getAndFilter("div", "class", "blueDiv");
			NodeList nodeList = parser.parse(filter);
			if (nodeList.size() > 0) {			
				getBillBookingInfo(info, nodeList);
				getContainerInfo(info, nodeList);
			}
		}
		return info;
	}

	private void getContainerInfo(TrackingInfo info, NodeList nodeList) {
		NodeList cnNodeList  = nodeList.elementAt(1).getChildren();
		cnNodeList = filterNode(cnNodeList, TableTag.class);				
		NodeList rowList = cnNodeList.elementAt(0).getChildren();
		rowList = filterNode(rowList, TableRow.class);
		Node[] rowNodes = rowList.toNodeArray();
		NodeList columnList = null;
		Node[] columnNodes = null;
		TrackingInfo subInfo = new TrackingInfo(3);
		NodeList titlecolumnList = rowNodes[1].getChildren();
		Node[] titlecolumns = titlecolumnList.toNodeArray();
		for(Node titlecolumn:titlecolumns)
		{
			subInfo.addHeadTitle(filterHtml(titlecolumn.toPlainTextString()));
		}
		for(int i=2;i<rowNodes.length;i++)
		{
			columnList=rowNodes[i].getChildren();	//行的列
			columnList=filterNode(columnList, TableColumn.class);
			columnNodes = columnList.toNodeArray();
			for(Node column:columnNodes)
			{
				subInfo.addRowData(filterHtml(column.toPlainTextString()));
			}
		}
		info.getSubTrackingInfo().add(subInfo);	
	}

	private void getBillBookingInfo(TrackingInfo info, NodeList nodeList) {
		NodeList blNodeList = nodeList.elementAt(0).getChildren();
		blNodeList = filterNode(blNodeList, TableTag.class);
		NodeList rowList = blNodeList.elementAt(0).getChildren();
		rowList = filterNode(rowList, TableRow.class);
		Node[] rowNodes = rowList.toNodeArray();
		NodeList columnList = null;
		Node[] columnNodes = null;
		for(int j=2;j<rowNodes.length;j++)
		{
			columnList=rowNodes[j].getChildren();	//行的列
			columnList=filterNode(columnList, TableColumn.class);
			columnNodes = columnList.toNodeArray();
			for(int i=0;i<columnNodes.length;)
			{
				info.addHeadTitle(filterHtml(columnNodes[i++].toPlainTextString()));
				info.addRowData(filterHtml(columnNodes[i++].toPlainTextString()));
			}
		}
	}
	
	

	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) {
		Map<String,Object> params=new HashMap<String, Object>();
		String type=String.valueOf(map.get(TrackingAdapter.STYPE));			//获得类型
		String value=String.valueOf(map.get(TrackingAdapter.SVALUE));
		if(type.equals(TrackingAdapter.STYPE_BILL))					//bill
		{
			params.put("TxtCrgId", value);
			params.put("searchby","bl");
		}
		else if(type.equals(TrackingAdapter.STYPE_BOOKING))			//订舱
		{
			params.put("TxtCrgId", value);
			params.put("searchby","br");
		}else if(type.equals(TrackingAdapter.STYPE_CONTAINER))		//container
		{
			params.put("TxtCrgId", value);
			params.put("searchby","cn");
		}
		return params;
	}

}
