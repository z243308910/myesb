package com.echounion.boss.Tracking.nds;

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

public class NDSTracking extends AbstractTrack{

	private Logger logger = Logger.getLogger(NDSTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url, Map<String, Object> params, boolean isPost) {
		HttpClient client =getHttpClient();
		params=assembleSelfParams(params);
		TrackingInfo info = null;
		try
		{
			HttpUriRequest httpSubmit=getRequestSubmit(url,params, isPost);
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			info= new TrackingInfo(7);
			info.setTitle("NDS Tracking Result");
			if (entity != null) {
				String data = EntityUtils.toString(entity);
				Parser parser = new Parser(data);
				NodeList nodeList = parser.parse(getAndFilter("table","id","trackTraceResultsTable"));
				if(nodeList.size()>0)
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
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("NDS 数据采集出错"+e);
		}
		return info;
	}

	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) {
		Map<String,Object> params=new HashMap<String, Object>();
		String type=String.valueOf(map.get(TrackingAdapter.STYPE));			//获得类型
		String value=String.valueOf(map.get(TrackingAdapter.SVALUE));
	
		if(type.equals(TrackingAdapter.STYPE_CONTAINER))		//container
		{
			params.put("Criteria.ContainerNumber", value);
		}
		else
		{
			params.put("Criteria.BookingNumber", value);
		}
		return params;
	}

}
