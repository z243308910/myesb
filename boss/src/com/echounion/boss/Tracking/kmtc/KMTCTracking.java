package com.echounion.boss.Tracking.kmtc;

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
import org.htmlparser.tags.OptionTag;
import org.htmlparser.tags.SelectTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableHeader;
import org.htmlparser.tags.TableRow;
import org.htmlparser.util.NodeList;

import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.common.util.DataConvertUtils;
import com.echounion.boss.entity.dto.TrackingInfo;

public class KMTCTracking extends AbstractTrack {

	private Logger logger=Logger.getLogger(KMTCTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url, Map<String, Object> params, boolean isPost) {
		HttpClient client =getHttpClient();
		params=assembleSelfParams(params);
		HttpUriRequest httpSubmit=getRequestSubmit(url, params, isPost);
		TrackingInfo info =null;
		try
		{
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			info= new TrackingInfo(6); // 总信息
			info.setTitle("KMTC Tracking Result");
			if (entity != null) {
				String data = EntityUtils.toString(entity);
				Parser parser = new Parser(data);
				AndFilter filter = getAndFilter("table","class","Board_row2");
				NodeList nodeList = parser.parse(filter);
				if(nodeList.size()>0)
				{
					Node[] titleRows = nodeList.elementAt(0).getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableRow.class)).toNodeArray();
					Node[] titleColumns = titleRows[0].getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableHeader.class)).toNodeArray();
					for(Node column:titleColumns)
					{
						info.addHeadTitle(filterHtml(column.toPlainTextString()));
					}
					Node[] dataRows = nodeList.elementAt(1).getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableRow.class)).toNodeArray();
					Node[] dataColumns = null;
					int[] rowSpans = new int[6*dataRows.length];
					for(int rowIndex=0;rowIndex<dataRows.length;rowIndex++)
					{
						dataColumns = dataRows[rowIndex].getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableColumn.class)).toNodeArray();
						int j = 0;
						for(int i=0;i<6;i++)
						{
							if(rowSpans[i]>rowIndex)
							{
								info.addRowData("");
								j++;
								continue;
							}
							TableColumn column = (TableColumn)dataColumns[i-j];
							if(i==2)
							{
								StringBuffer sb = new StringBuffer();
								NodeList cnNOList = column.getChildren().extractAllNodesThatMatch(new NodeClassFilter(SelectTag.class));
								if(cnNOList.size()>0)
								{
									OptionTag[] options = ((SelectTag)cnNOList.elementAt(0)).getOptionTags();
									for(OptionTag option:options)
									{
										
										sb.append(option.toPlainTextString()).append(",");
									}
									info.addRowData(sb.toString());
									continue;
								}
							}
							int rowSpan = DataConvertUtils.convertInteger(column.getAttribute("rowspan"));
							rowSpans[i-j]=rowSpan;
							info.addRowData(filterHtml(column.toPlainTextString()));
						}
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("KMTC 数据采集出错"+e);
		}
		return info;
	}

	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) {
		Map<String,Object> params=new HashMap<String, Object>();
		String type=String.valueOf(map.get(TrackingAdapter.STYPE));			//获得类型
		String value=String.valueOf(map.get(TrackingAdapter.SVALUE));
		if(type.equals(TrackingAdapter.STYPE_BILL))					//bill
		{		
			params.put("dt_knd", "BL");
		}
		else if(type.equals(TrackingAdapter.STYPE_BOOKING))		
		{
			params.put("dt_knd", "BK");
		}else if(type.equals(TrackingAdapter.STYPE_CONTAINER))		//container
		{
			params.put("dt_knd", "CN");
		}
		params.put("bl_no", value);
		return params;
	}

}
