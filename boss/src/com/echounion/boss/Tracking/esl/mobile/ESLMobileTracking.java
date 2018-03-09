package com.echounion.boss.Tracking.esl.mobile;

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
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableHeader;
import org.htmlparser.tags.TableRow;
import org.htmlparser.util.NodeList;

import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.entity.dto.TrackingInfo;

public class ESLMobileTracking extends AbstractTrack{

	private Logger logger=Logger.getLogger(ESLMobileTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url, Map<String, Object> params, boolean isPost){
		String containerNo = String.valueOf(params.get(TrackingAdapter.SVALUE));
		HttpClient client = getHttpClient();
		params = assembleSelfParams(params);
		TrackingInfo info =new TrackingInfo();
		try
		{
			HttpUriRequest httpSubmit=getRequestSubmit(url, params, isPost);
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String data = EntityUtils.toString(entity);
				Parser parser = new Parser(data);
				AndFilter filter = getAndFilter("div", "style", "float:left;width:200px;margin-left:114px;");
				NodeList nodeList = parser.parse(filter);
				String documentRefNo = null;
				if(nodeList!=null)
				{
					documentRefNo= nodeList.elementAt(0).toPlainTextString();
					documentRefNo=documentRefNo.replaceAll("&nbsp;","");
					documentRefNo=documentRefNo.replaceAll("Booking No","");
					documentRefNo=documentRefNo.replaceAll(":","");
				}
				info = getContainerInfo(documentRefNo,containerNo);
			}
		}catch (Exception e) {
			logger.error("ESL Mobile 数据采集出错"+e);
		}
		return info;
	}
	
	private TrackingInfo getContainerInfo(String documentRefNo,String containerNo) throws Exception
	{
		TrackingInfo info =null;
		info= new TrackingInfo(7);
		info.setTitle("ESL Tracking Result");		
		HttpClient client = getHttpClient();
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("containerNo", containerNo);
		params.put("documentRefNo",documentRefNo);
		params.put("language","ENG");
		HttpUriRequest httpSubmit=getRequestSubmit("http://www.emiratesline.com/cargotracking/cargomovementitem.html", params,true);
		HttpResponse response = client.execute(httpSubmit);
		HttpEntity entity = response.getEntity();
		String data = EntityUtils.toString(entity);
		System.out.println(data);
		Parser parser = new Parser(data);
		AndFilter filter = getAndFilter("table", "class", "cargomovementitemdetail");
		NodeList nodeList = parser.parse(filter);
		if (nodeList.size() > 0) 
		{
			Node[] rows = nodeList.elementAt(0).getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableRow.class)).toNodeArray();
			Node[] columns = null;
			for(int i=0;i<rows.length;i++)
			{
				if(i==0)
				{
					columns = rows[i].getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableHeader.class)).toNodeArray();
					for(Node column:columns)
					{
						info.addHeadTitle(filterHtml(column.toPlainTextString()));
					}
				}
				else
				{
					columns = rows[i].getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableColumn.class)).toNodeArray();
					for(Node column:columns)
					{
						info.addRowData(filterHtml(column.toPlainTextString()));
					}
				}
			}
		}
		return info;
	}

	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) {
		Map<String,Object> params=new HashMap<String, Object>();
		String value=String.valueOf(map.get(TrackingAdapter.SVALUE));
		params.put("record", value);
		params.put("trackingType","any");		
		return params;
	}
}
