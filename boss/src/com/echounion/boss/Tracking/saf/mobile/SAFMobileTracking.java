package com.echounion.boss.Tracking.saf.mobile;

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
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableHeader;
import org.htmlparser.tags.TableRow;
import org.htmlparser.util.NodeList;

import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.entity.dto.TrackingInfo;

public class SAFMobileTracking extends AbstractTrack{

	private Logger logger=Logger.getLogger(SAFMobileTracking.class);
	
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
			info = getContainerInfo(EntityUtils.toString(entity));
		}catch (Exception e) {
			logger.error("SAF Mobile 数据采集出错"+e);
		}
		return info;
	}
	
	private TrackingInfo getContainerInfo(String data) throws Exception
	{
		TrackingInfo info = new TrackingInfo(5);
		Parser parser = new Parser(data);
		NodeList nodeList = parser.parse(getAndFilter("div","id","containerNumber_0_readsection"));
		if(nodeList.size()>0)
		{
			LinkTag linkTag =(LinkTag)nodeList.elementAt(0).getChildren().extractAllNodesThatMatch(new NodeClassFilter(LinkTag.class)).elementAt(0);
			String containerDetails = linkTag.getAttribute("onclick");
			containerDetails = containerDetails.substring(containerDetails.indexOf("(")+1,containerDetails.lastIndexOf(")"));
			containerDetails = containerDetails.replaceAll("'", "");
			String cntrNos[] = containerDetails.split(",");
			String url = "http://mysaf2.safmarine.com/wps/portal/!ut/p/c5/04_SB8K8xLLM9MSSzPy8xBz9CP0os3gjAxMjAwt3S18zy2A3A09vTxOXYMsgAyAAykciyfu6-TobeJr4GRhZePkYGliYEKPbAAdwNCCgOxzkWty2Oxvjlwe5DiSPx34_j_zcVP2C3NAIg8yAdAC7Jr4w/dl3/d3/L0lDU0lKSmdrS0NsRUpDZ3BSQ1NBL29Ob2dBRUlRaGpFS0lRQUJHY1p3aklDa3FTaFNOQkFOYUEhIS80QzFiOVdfTnIwZ0RFU1pJSlJERVNaTUpRaUlrZmchIS83XzIwNDIwOEc5TTY5U0YwSUtJNERTOVIwMDAzLzc5REtuNDA3MDAwMS9pYm0uaW52LzI1NTI0NjU2NjE1OC9qYXZheC5wb3J0bGV0LmFjdGlvbi91blJlZ1RyYW5zcG9ydFBsYW5BY3Rpb24!/";
			Map<String,Object> params=new HashMap<String, Object>();
			params.put("searchType", "Container");
			params.put("containerNumber", cntrNos[0]);
			params.put("shipmentId", cntrNos[1]);
			HttpClient client =getHttpClient();
			HttpUriRequest httpSubmit=getRequestSubmit(url, params, true);
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			data = EntityUtils.toString(entity);
			parser = new Parser(data);
			nodeList = parser.parse(getAndFilter("table","class","results"));
			if(nodeList.size()>0)
			{
				Node[] rows = nodeList.elementAt(0).getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableRow.class)).toNodeArray();
				Node[] columns = null;
				for(int i=0;i<rows.length;i++)
				{
					if(i==0)
					{
						columns = rows[i].getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableHeader.class)).toNodeArray();
						for(Node colunm:columns)
						{
							info.addHeadTitle(filterHtml(colunm.toPlainTextString()));
						}
					}
					else
					{
						columns = rows[i].getChildren().extractAllNodesThatMatch(new NodeClassFilter(TableColumn.class)).toNodeArray();
						for(Node colunm:columns)
						{
							info.addRowData(filterHtml(colunm.toPlainTextString()));
						}
					}
				}
			}
		}
		return info;
	}

	@Override
	public Map<String, Object> assembleSelfParams(Map<String, Object> map) {
		Map<String,Object> params=new HashMap<String, Object>();
		String value=String.valueOf(map.get(TrackingAdapter.SVALUE));
		params.put("linktype","unreg");
		params.put("queryorigin","Header");
		params.put("queryoriginauto","HeaderNonSecure");
		params.put("searchType","Container");
		params.put("searchNumberString",value);
		return params;
	}

}
