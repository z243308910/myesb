package com.echounion.boss.Tracking.whl;

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
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.util.NodeList;

import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.entity.dto.TrackingInfo;

public class WHLTracking extends AbstractTrack {

	private Logger logger=Logger.getLogger(WHLTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url,Map<String,Object> params,boolean isPost){
		HttpClient client =getHttpClient();
		params=assembleSelfParams(params);
		HttpUriRequest httpSubmit=getRequestSubmit(url, params, isPost);
		TrackingInfo info =null;
		try
		{
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
		
			if (entity != null) {
				String data = EntityUtils.toString(entity);
				Parser parser = new Parser(data);
				AndFilter filter = getAndFilter("table", "width", "100%");
				NodeList nodeList = parser.parse(filter);
				if (nodeList.size() > 0) {
					System.out.println(nodeList.toHtml());
					info = getTrackingInfo(nodeList);
					info.addSubTrackingInfo(getSubTracking(nodeList,5,"CONTAINER INFORMATION"));
					info.addSubTrackingInfo(getSubTracking(nodeList,6,"B/L INFORMATION"));
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("WHL 数据采集出错"+e);
		}
		return info;
	}
	
	private TrackingInfo getTrackingInfo(NodeList nodeList) {
	
		TrackingInfo info = new TrackingInfo(8); // 总信息
		info.setTitle("WHL Tracking Result");
		nodeList = nodeList.elementAt(4).getChildren(); // 获得第5个table
		Node[] rows = nodeList.extractAllNodesThatMatch(new NodeClassFilter(TableRow.class)).toNodeArray();
		Node[] columns =null;
		TableColumn column = null;
		for(int i=0;i<rows.length;i++)
		{
			columns = rows[i].getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableColumn.class)).toNodeArray();
			for (int j=0;j<columns.length;j++) 
			{	
				column = (TableColumn)columns[j];
				if(i==0)
				{
					info.addHeadTitle(filterHtml(column.toPlainTextString()));
				}
				else
				{
					if("over_blue01".equals(column.getAttribute("class")))
					{
						nodeList = column.getChildren().extractAllNodesThatMatch(new NodeClassFilter(LinkTag.class));
						if(nodeList.size()>0)
						{
							info.addRowData(filterHtml(nodeList.elementAt(0).toPlainTextString()));
						}
					}
					else
					{
						info.addRowData(filterHtml(column.toPlainTextString()));
					}
				}
			}
		}
		return info;
	}
	
	private TrackingInfo getSubTracking(NodeList nodeList,int index,String infoTitle)
	{
		if(nodeList.size()<index)
		{
			return null;
		}
		TrackingInfo info = new TrackingInfo(2);
		info.setTitle(infoTitle);
		nodeList = nodeList.elementAt(index).getChildren();
		Node[] rows = nodeList.extractAllNodesThatMatch(new NodeClassFilter(TableRow.class)).toNodeArray();
		Node[] columns =null;
		for(int i=1;i<rows.length;i++)
		{
			columns = rows[i].getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableColumn.class)).toNodeArray();
			for(Node column:columns)
			{
				info.addRowData(filterHtml(column.toPlainTextString()));
			}
		}
		return info;
	}

	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) {
		Map<String,Object> params=new HashMap<String, Object>();
		String value=String.valueOf(map.get(TrackingAdapter.SVALUE));
		params.put("REF_NO1", value);
		return params;
	}
}
