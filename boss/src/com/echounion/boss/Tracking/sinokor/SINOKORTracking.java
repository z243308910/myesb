package com.echounion.boss.Tracking.sinokor;

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
import com.echounion.boss.common.util.DataConvertUtils;
import com.echounion.boss.entity.dto.TrackingInfo;

public class SINOKORTracking extends AbstractTrack {

	private Logger logger=Logger.getLogger(SINOKORTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url, Map<String, Object> params,boolean isPost){
		String type=String.valueOf(params.get(TrackingAdapter.STYPE));			//获得类型
		HttpClient client =getHttpClient();
		params=assembleSelfParams(params);
		TrackingInfo info =new TrackingInfo();
		try
		{
			HttpUriRequest httpSubmit=getRequestSubmit(url,params, isPost);
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			info= new TrackingInfo(); // 总信息
			if (entity != null) {
				String data = EntityUtils.toString(entity);
				if(type.equals(TrackingAdapter.STYPE_CONTAINER))
				{
					info = getContainerInfo(data);
				}
				else
				{
					info = getBLTrackingInfo(data);
					info.addSubTrackingInfo(getBLCargoStatusInfo(data));
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("SINOKOR 数据采集出错"+e);
		}
		return info;
	}
	
	private TrackingInfo getContainerInfo(String data) throws Exception
	{
		TrackingInfo info =new TrackingInfo(4);
		info.setTitle("SINOKOR Tracking Result");
		Parser parser = new Parser(data);
		NodeList nodelist = parser.parse(getAndFilter("table","borderColorLight","#a1c5b9"));
		if(nodelist.size()>0)
		{
			Node[] rows = nodelist.elementAt(0).getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableRow.class)).toNodeArray();
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
		return info;
	}
	
	
	private TrackingInfo getBLTrackingInfo(String data) throws Exception
	{
		TrackingInfo info =new TrackingInfo(8);
		Parser parser = new Parser(data);
		info.setTitle("SINOKOR Tracking Result");
		NodeList nodelist = parser.parse(getAndFilter("table","bgcolor","d8d6ba"));
		if(nodelist.size()>0)
		{
			info.addHeadTitle("SINOKOR Tracking Result");
			Node[] rows = nodelist.elementAt(0).getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableRow.class)).toNodeArray();
			Node[] columns = null;
			for(int i=1;i<rows.length;i++)
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
	
	private TrackingInfo getBLCargoStatusInfo(String data) throws Exception
	{
		TrackingInfo info =new TrackingInfo(5);
		Parser parser = new Parser(data);
		info.setTitle("SINOKOR Cargo Status");
		NodeList nodelist = parser.parse(getAndFilter("table", "bgcolor","d8d6ba"));
		if(nodelist.size()>0)
		{
			Node[] rows = nodelist.elementAt(2).getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableRow.class)).toNodeArray();
			Node[] columns = null;
			TableColumn tabColumn = null;
			int[] rowSpans= new int[5];
			int rowSpan = 0;
			for(int i=1;i<rows.length;i++)
			{
				columns = rows[i].getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableColumn.class)).toNodeArray();
				for(int j=0;j<columns.length;j++)
				{
					if(rowSpans[j]>0)
					{
						info.addRowData("");
						rowSpans[j]=rowSpans[j]-1;
					}
					tabColumn = (TableColumn)columns[j];
					if(i==1)
					{
						info.addHeadTitle(filterHtml(tabColumn.toPlainTextString()));
					}
					else
					{
						info.addRowData(filterHtml(tabColumn.toPlainTextString()));
					}
					rowSpan = DataConvertUtils.convertInteger(tabColumn.getAttribute("ROWSPAN"));
					if(rowSpan>0)
					{
						rowSpans[j] = rowSpan-1;
					}
				}	
			}
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
			params.put("blno_cntr", value);
			params.put("gb", "CNTR");
		}
		else					//bill
		{
			params.put("blno_cntr", value);
			params.put("gb", "BL");
		}
		return params;
	}

}
