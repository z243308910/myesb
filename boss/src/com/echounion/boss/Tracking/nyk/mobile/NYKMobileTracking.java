package com.echounion.boss.Tracking.nyk.mobile;

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
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.util.NodeList;

import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.entity.dto.TrackingInfo;

public class NYKMobileTracking extends AbstractTrack{

	private Logger logger=Logger.getLogger(NYKMobileTracking.class);
	
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
			info = getContraInfo(EntityUtils.toString(entity));
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("NYK Mobile 数据采集出错"+e);
		}
		return info;
	}
	
	private TrackingInfo getContraInfo(String data) throws Exception
	{
		TrackingInfo info = new TrackingInfo();
		Parser parser = new Parser(data);
		NodeList nodeList = parser.parse(getAndFilter("table","class","results"));
		Node[] rows = nodeList.elementAt(0).getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableRow.class)).toNodeArray();
		TableRow row = null;
		Node[] columns = null;
		for(int i=4;i<rows.length;i++)
		{
			row = (TableRow)rows[i];
			columns = row.getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableColumn.class)).toNodeArray();
			if("ctTableHeader".equals(row.getAttribute("class")))
			{
				info = new TrackingInfo(4);
				for(Node column:columns)
				{
					info.addHeadTitle(filterHtml(column.toPlainTextString()));
				}
			}
			else
			{
				for(Node column:columns)
				{
					info.addRowData(filterHtml(column.toPlainTextString()));
				}
			}			
		}
		return info;
	}
	
	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) {
		Map<String,Object> params=new HashMap<String, Object>();
		String value=String.valueOf(map.get(TrackingAdapter.SVALUE));
		params.put("searchText", value);
		params.put("fromCT", true);
		params.put("ctReset", true);
		return params;
	}

}
