package com.echounion.boss.Tracking.cscl.mobile;

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

public class CSCLMobileTracking extends AbstractTrack{
	
	private Logger logger=Logger.getLogger(CSCLMobileTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url, Map<String, Object> params, boolean isPost){
		HttpClient client = getHttpClient();
		params = assembleSelfParams(params);
		TrackingInfo info =new TrackingInfo();
		try
		{
			HttpUriRequest httpSubmit=getRequestSubmit(url, params, isPost);
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			if(entity != null)
			{
				info = getContainerInfo(EntityUtils.toString(entity));
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("CSCL Mobile Tracking 数据采集出错:"+e);
		}
		return info;
	}

	private TrackingInfo getContainerInfo(String data) throws Exception 
	{
		TrackingInfo info = new TrackingInfo(5);
		Parser parser = new Parser(data);
		AndFilter filter = getAndFilter("table", "bordercolor", "#999999");
		NodeList nodeList = parser.parse(filter);
		if(nodeList.size()>0)
		{
			Node[] rows = nodeList.elementAt(4).getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableRow.class)).toNodeArray();
			Node[] columns = null;
			for(int i=0;i<rows.length;i++)
			{
				columns = rows[i].getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableColumn.class)).toNodeArray();
				for(Node column:columns)
				{
					if(i==0)
					{
						info.addHeadTitle(filterHtml(column.toPlainTextString()));
					}else
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
		String value = String.valueOf(map.get(TrackingAdapter.SVALUE));
		params.put("tf_bl_no", value);
		params.put("tr_num", "unit_no");
		return params;
	}

}
