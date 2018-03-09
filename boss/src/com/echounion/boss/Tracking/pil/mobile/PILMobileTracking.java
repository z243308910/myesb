package com.echounion.boss.Tracking.pil.mobile;

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
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.entity.dto.TrackingInfo;

public class PILMobileTracking extends AbstractTrack{

	private Logger logger=Logger.getLogger(PILMobileTracking.class);
	
	
	@Override
	public TrackingInfo doTrack(String url, Map<String, Object> params, boolean isPost){
		HttpClient client =getHttpsClient();
		params=assembleSelfParams(params);
		TrackingInfo info = new TrackingInfo();
		try
		{
			HttpUriRequest httpSubmit=getRequestSubmit(url, params, isPost);
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			info= getContainerInfo(entity);
		}catch (Exception e) {
			logger.error("PIL 数据采集出错"+e);
		}
		return info;
	}
	
	private TrackingInfo getContainerInfo(HttpEntity entity)
	{
		TrackingInfo info =null;
		info= new TrackingInfo(6); // 总信息
		info.setTitle("PIL Tracking Result");
		try
		{
		if (entity != null) {
			JSONObject obj = JSON.parseObject(EntityUtils.toString(entity), JSONObject.class);
			JSONObject data = obj.getJSONObject("data");
			if(!(null==data||data.isEmpty()))
			{
				String containers = data.getString("containers");
				Parser parser = new Parser(containers);
				NodeList nodeList = parser.parse(getAndFilter("tr","class","titles"));
				Node[] columns = nodeList.elementAt(0).getChildren().toNodeArray();
				for(Node column:columns)
				{
					info.addHeadTitle(filterHtml(column.toPlainTextString()));
				}
				parser = new Parser(containers);
				nodeList = parser.parse(new NodeClassFilter(TableTag.class));
				Node[] containerTabs = nodeList.toNodeArray();
				Node[] rows = null;
				for(Node contrainer:containerTabs)
				{
					rows = contrainer.getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableRow.class)).toNodeArray();
					for(int i=1; i<rows.length;i++)
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
			logger.error("采集PIL信息出错"+e);
		}
		return info;
	}

	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) {
		Map<String,Object> params=new HashMap<String, Object>();
		String value=String.valueOf(map.get(TrackingAdapter.SVALUE));
		params.put("ref_num", value);
		params.put("fn","get_tracktrace_container");
		params.put("searchby","cn");
		return params;
	}

}
