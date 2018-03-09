package com.echounion.boss.Tracking.zim.mobile;

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

public class ZIMMobileTracking extends AbstractTrack{

	Logger logger=Logger.getLogger(ZIMMobileTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url, Map<String, Object> params, boolean isPost) {
		HttpClient client =getHttpClient();
		params=assembleSelfParams(params);
		TrackingInfo info =new TrackingInfo();
		try
		{
			HttpUriRequest httpSubmit=getRequestSubmit(url, params, isPost);
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			info=getContainerInfo(entity);
		}catch (Exception e) {
			logger.error("ZIM 数据采集出错"+e);
		}
		return info;
	}

	private TrackingInfo getContainerInfo(HttpEntity entity)
	{
		TrackingInfo info =null;
		info= new TrackingInfo(4); // 总信息
		info.setTitle("ZIM Tracking Result");
		try
		{
			if (entity != null) {
				String data = EntityUtils.toString(entity);
				Parser parser = new Parser(data);
				AndFilter filter = getAndFilter("table", "id", "ctl00_SPWebPartManager1_g_0728e18d_b0eb_4993_ae27_14cdcc027dc9_ctl00_TraceShipment1_ContainerSearchRoutingDetails_gridContainerRoutingDetails");
				NodeList nodeList = parser.parse(filter);
				if (nodeList.size() > 0) {
					Node[] rows= nodeList.elementAt(0).getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableRow.class)).toNodeArray();
					Node[] columns = null;
					for(int i=0;i<rows.length;i++)
					{
						if(i==0)
						{
							columns =rows[i].getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableHeader.class)).toNodeArray();
							for(Node column:columns)
							{
								info.addHeadTitle(filterHtml(column.toPlainTextString()));
							}	
						}
						else
						{
							columns =rows[i].getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableColumn.class)).toNodeArray();						
							for(Node column:columns)
							{
								info.addRowData(filterHtml(column.toPlainTextString()));
							}											
						}						
					}	
				}
			}
		}catch (Exception e) {
			logger.error("采集ZIM信息出错"+e);
		}
		return info;
	}
	
	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) {
		Map<String,Object> params=new HashMap<String, Object>();
		String value=String.valueOf(map.get(TrackingAdapter.SVALUE));
		params.put("searchvalue1", value);
		params.put("searchtype","3");
		return params;
	}

}
