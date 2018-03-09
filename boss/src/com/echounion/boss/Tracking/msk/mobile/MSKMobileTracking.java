package com.echounion.boss.Tracking.msk.mobile;

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
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.util.NodeList;

import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.entity.dto.TrackingInfo;

public class MSKMobileTracking extends AbstractTrack{

	private Logger logger=Logger.getLogger(MSKMobileTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url, Map<String, Object> params, boolean isPost) {
		HttpClient client = getHttpClient();
		params=assembleSelfParams(params);
		HttpUriRequest httpSubmit=getRequestSubmit(url, params, isPost);
		TrackingInfo info =null;
		try
		{
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				Parser parser = new Parser(EntityUtils.toString(entity));
				NodeList nodeList = parser.parse(getAndFilter("tr","id","overview_row_1"));
				if(nodeList.size()>0)
				{
					nodeList = nodeList.elementAt(0).getChildren().elementAt(3).getChildren().extractAllNodesThatMatch(new NodeClassFilter(LinkTag.class));
					String linkUrl = ((LinkTag)nodeList.elementAt(0)).getAttribute("href");
					return getContainerInfo(linkUrl);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("MSK Mobile 数据采集出错"+e);
		}
		return info;
	}
	
	public TrackingInfo getContainerInfo(String linkUrl) throws Exception
	{
		TrackingInfo info = new TrackingInfo(5); 
		info.setTitle("Container details");
		HttpClient client = getHttpClient();
		HttpUriRequest httpSubmit = getRequestSubmit(linkUrl, new HashMap<String, Object>(), false);
		HttpResponse response = client.execute(httpSubmit);
		HttpEntity entity = response.getEntity();
		if(entity != null)
		{
			Parser parser = new Parser(EntityUtils.toString(entity));
			NodeList nodeList = parser.parse(getAndFilter("table","class","lstBox"));
			if(nodeList.size()>1)
			{
				Node[] rows = nodeList.elementAt(1).getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableRow.class)).toNodeArray();
				Node[] columns = null;
				for(int i=1;i<rows.length;i++)
				{
					columns = rows[i].getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableColumn.class)).toNodeArray();
					for(Node column:columns)
					{
						if(i==1)
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
		return info;
	}

	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) {
		Map<String,Object> params=new HashMap<String, Object>();
		String value=String.valueOf(map.get(TrackingAdapter.SVALUE));
		params.put("portlet_trackSimple_1wlw-select_key:{pageFlow.trackSimpleForm.type}", "CONTAINERNUMBER");
		params.put("portlet_trackSimple_1{pageFlow.trackSimpleForm.numbers}", value);
		params.put("portlet_trackSimple_1wlw-select_key:{pageFlow.trackSimpleForm.type}OldValue","true");
		return params;
	}

}
