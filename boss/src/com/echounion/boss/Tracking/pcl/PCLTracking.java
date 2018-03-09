package com.echounion.boss.Tracking.pcl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.util.NodeList;

import com.alibaba.fastjson.JSON;
import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.entity.dto.TrackingInfo;

public class PCLTracking extends AbstractTrack {

	private Logger logger=Logger.getLogger(PCLTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url, Map<String, Object> params, boolean isPost){
		HttpClient client =getHttpClient();
		params=assembleSelfParams(params);
		HttpUriRequest httpSubmit=getRequestSubmit(url, params, isPost);
		httpSubmit.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		TrackingInfo info =null;
		try
		{
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			info= new TrackingInfo(10); // 总信息
			info.setTitle("PCL Tracking Result");
			if (entity != null) {
				String data = EntityUtils.toString(entity);
				Parser parser = new Parser(data);
				AndFilter filter = getAndFilter("table");
				NodeList nodeList = parser.parse(filter);
				if(nodeList.size()>0)
				{
					Node[] rows = nodeList.elementAt(0).getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableRow.class)).toNodeArray();
					Node[] columns = null;
					for(Node row:rows)
					{
						columns = row.getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableColumn.class)).toNodeArray();
						for(int i=0;i<columns.length;)
						{
							info.addHeadTitle(filterHtml(columns[i++].toPlainTextString()));
							info.addRowData(filterHtml(columns[i++].toPlainTextString()));
						}
					}
				}
				info.getSubTrackingInfo().add(getSubTrackingInfo(data));
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("PCL 数据采集出错"+e);
		}
		return info;
	}
	
	private TrackingInfo getSubTrackingInfo(String data) throws Exception 
	{
		TrackingInfo info = new TrackingInfo(3); // 总信息
		Parser parser = new Parser(data);
		NodeFilter filter = new NodeClassFilter(LinkTag.class);
		NodeList cnNoList = parser.extractAllNodesThatMatch(filter);
		if(cnNoList.size()>0){
			String cnNo = filterHtml(cnNoList.elementAt(0).toPlainTextString());
			String params = data.substring(data.indexOf("$.param({")+8, data.indexOf("})")+1);
			params = params.replaceFirst("cntrNo", "Test!@").replaceFirst("cntrNo", "'"+cnNo+"'").replaceFirst("Test!@", "cntrNo");
			HttpClient client = getHttpClient();
			HttpUriRequest httpSubmit=getRequestSubmit("http://www.pancon.co.kr/pcl/tracking/movement", JSON.parseObject(params), false);
			httpSubmit.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			info= new TrackingInfo(10); // 总信息
			info.setTitle("PCL Tracking Result");
			if (entity != null) {
				data = EntityUtils.toString(entity);
				parser = new Parser(data);
				filter = getAndFilter("table");
				NodeList nodeList = parser.parse(filter);
				if(nodeList.size()>0)
				{
					Node[] rows = nodeList.elementAt(0).getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableRow.class)).toNodeArray();
					Node[] columns = null;
					for(int i=0;i<rows.length;i++)
					{
						columns = rows[i].getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableColumn.class)).toNodeArray();
						for(Node column:columns)
						{
							if(i==0)
							{
								info.addHeadTitle(filterHtml(column.toPlainTextString()));
							}
							else
							{
								info.addRowData(filterHtml(column.toPlainTextString()));
							}
						}
					}
				}
			}
		}
		return info;
	}

	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) {
		Map<String,Object> params=new HashMap<String, Object>();
		String type=String.valueOf(map.get(TrackingAdapter.STYPE));			//获得类型
		String value=String.valueOf(map.get(TrackingAdapter.SVALUE));
		if(type.equals(TrackingAdapter.STYPE_BILL))					//bill
		{
			params.put("st", "blNo");
		}
		else if(type.equals(TrackingAdapter.STYPE_BOOKING))		
		{
			params.put("st", "bookingNo");
		}else if(type.equals(TrackingAdapter.STYPE_CONTAINER))		//container
		{
			params.put("st", "containerNo");
		}
		params.put("sv", value);
		return params;
	}

}
