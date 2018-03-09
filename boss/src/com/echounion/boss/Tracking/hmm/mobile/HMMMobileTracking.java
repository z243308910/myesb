package com.echounion.boss.Tracking.hmm.mobile;

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

public class HMMMobileTracking extends AbstractTrack{

	private Logger logger=Logger.getLogger(HMMMobileTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url, Map<String, Object> params,boolean isPost){
		HttpClient client =getHttpClient();
		params = assembleSelfParams(params);
		TrackingInfo info =new TrackingInfo();
		try
		{
			HttpUriRequest httpSubmit = getRequestSubmit(url,params,isPost);
			httpSubmit.addHeader("Referer", "http://www.hmm.co.kr");		
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			if (entity != null) 
			{
				info = getContainerInfo(EntityUtils.toString(entity));
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("HMM Mobile 数据采集出错"+e);
		}
		return info;
	}
	
	private TrackingInfo getContainerInfo(String data) throws Exception 
	{
		TrackingInfo info = new TrackingInfo(5);
		info.setTitle("HMM Tracking Result");
		Parser parser = new Parser(data);
		NodeList nodeList = parser.parse(getAndFilter("table","class","business02 ty03"));
		if(nodeList.size()>3)
		{
			Node[] rows = nodeList.elementAt(3).getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableRow.class)).toNodeArray();
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
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) 
	{
		Map<String,Object> params=new HashMap<String, Object>();
		String value=String.valueOf(map.get(TrackingAdapter.SVALUE));
		params.put("numbers",value);
		return params;
	}

}
