package com.echounion.boss.Tracking.cnc.mobile;

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
import org.htmlparser.tags.TableHeader;
import org.htmlparser.tags.TableRow;
import org.htmlparser.util.NodeList;

import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.entity.dto.TrackingInfo;

public class CNCMobileTracking extends AbstractTrack{


	private Logger logger=Logger.getLogger(CNCMobileTracking.class);
	
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
			info = getContainerInfo(EntityUtils.toString(entity));
		}catch (Exception e) {
			logger.error("PIL 数据采集出错"+e);
		}
		return info;
	}
	
	private TrackingInfo getContainerInfo(String data) throws Exception
	{
		TrackingInfo info = new TrackingInfo(5);
		Parser parser = new Parser(data);
		NodeList nodeList = parser.parse(getAndFilter("table","id","container-moves"));
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
		return info;
	}
	
	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) {
		Map<String,Object> params=new HashMap<String, Object>();
		String value=String.valueOf(map.get(TrackingAdapter.SVALUE));
		params.put("SearchBy","Container");
		params.put("Reference",value);
		return params;
	}

}
