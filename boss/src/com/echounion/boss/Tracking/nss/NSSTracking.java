package com.echounion.boss.Tracking.nss;

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
import org.htmlparser.tags.TableRow;
import org.htmlparser.util.NodeList;

import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.entity.dto.TrackingInfo;

public class NSSTracking extends AbstractTrack{

	Logger logger = Logger.getLogger(NSSTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url, Map<String, Object> params, boolean isPost) {
		HttpClient client =getHttpClient();
		params=assembleSelfParams(params);
		TrackingInfo info =new TrackingInfo(7);
		try
		{
			HttpUriRequest httpSubmit=getRequestSubmit(url, params, isPost);
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			if(entity!=null)
			{
				String data = EntityUtils.toString(entity);
				data = data.replaceAll("<th>", "<td>").replaceAll("</th>", "</td>");
				AndFilter filter = getAndFilter("table", "frame", "below");
				Parser parser = new Parser(data);
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
							info.addRowData(filterHtml(column.toPlainTextString()));
						}
					}
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("NSS 数据采集出错"+e);
		}
		return info;
	}

	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) {
		Map<String,Object> params=new HashMap<String, Object>();
		String type=String.valueOf(map.get(TrackingAdapter.STYPE));	//获得类型
		String value=String.valueOf(map.get(TrackingAdapter.SVALUE));
		if(type.equals(TrackingAdapter.STYPE_CONTAINER))		//container
		{
			params.put("stype","c");
			params.put("cntr_no",value);
		}
		else
		{
			params.put("stype","b");
			params.put("blNo",value);
		}
		return params;
	}

}
