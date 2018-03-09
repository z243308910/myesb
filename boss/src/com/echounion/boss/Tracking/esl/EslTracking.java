package com.echounion.boss.Tracking.esl;

import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.util.NodeList;
import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.entity.dto.TrackingInfo;

public class EslTracking extends AbstractTrack {

	private Logger logger=Logger.getLogger(EslTracking.class);
	
	@Override
	public TrackingInfo doTrack(String url,Map<String,Object> params,boolean isPost){
		HttpClient client =getHttpClient();
		String stype=params.get(TrackingAdapter.STYPE).toString();
		params=assembleSelfParams(params);
		TrackingInfo info =new TrackingInfo();
		try
		{
			HttpUriRequest httpSubmit=getRequestSubmit(url, params, isPost);
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			if(stype.equals(TrackingAdapter.STYPE_CONTAINER))
			{
				info=getContainerInfo(entity);
			}
			else
			{
				info=getBillBookingInfo(entity);
			}
			
		}catch (Exception e) {
			logger.error("ESL 数据采集出错"+e);
		}
		return info;
	}
	
	private TrackingInfo getContainerInfo(HttpEntity entity)
	{
		return getBillBookingInfo(entity);
	}
	
	private TrackingInfo getBillBookingInfo(HttpEntity entity)
	{
		TrackingInfo info =null;
		info= new TrackingInfo(7); // 总信息
		info.addHeadTitle("Date");info.addHeadTitle("Service");info.addHeadTitle("Vessel");
		info.addHeadTitle("Voyage");info.addHeadTitle("Bound");info.addHeadTitle("Movement");
		info.addHeadTitle("Location");
		info.setTitle("ESL Tracking Result");
		try
		{
			if (entity != null) {
				String data = EntityUtils.toString(entity);
				Parser parser = new Parser(data);
				AndFilter filter = getAndFilter("div", "style", "float:left;width:200px;margin-left:114px;");
				NodeList nodeList = parser.parse(filter);
				
				String documentRefNo=null;
				String containerNo=null;
				if(nodeList!=null)
				{
					documentRefNo=((Div)nodeList.elementAt(0)).toPlainTextString();
					documentRefNo=documentRefNo.replaceAll("&nbsp;","");
					documentRefNo=documentRefNo.replaceAll("Booking No","");
					documentRefNo=documentRefNo.replaceAll(":","");
				}
				
				filter = getAndFilter("input", "id", "containerNo1");
				parser = new Parser(data);
				nodeList = parser.parse(filter);
				if(nodeList!=null)
				{
					containerNo=((InputTag)nodeList.elementAt(0)).getAttribute("value");
				}
				
				HttpClient client =getHttpClient();
				Map<String,Object> params=new HashMap<String, Object>();
				params.put("containerNo", containerNo);
				params.put("documentRefNo",documentRefNo);
				HttpUriRequest httpSubmit=getRequestSubmit("http://www.emiratesline.com/cargotracking/cargomovementitem.html", params,true);
				HttpResponse response = client.execute(httpSubmit);
				entity = response.getEntity();
	
				data = EntityUtils.toString(entity);
				parser = new Parser(data);
				filter = getAndFilter("table", "class", "cargomovementitemdetail");
				nodeList = parser.parse(filter);
				
				if (nodeList.size() > 0) {
					
					try
					{
						NodeList threeNodeList=nodeList.elementAt(0).getChildren();
						threeNodeList=filterNode(threeNodeList, TableRow.class);
						for (int i=0;i<threeNodeList.size();i++) {		//遍历每行
							NodeList tempNodeList=threeNodeList.elementAt(i).getChildren();	//行的列
							if(i>0)
							{
								tempNodeList=filterNode(tempNodeList, TableColumn.class);
								info.addRowDataList(getDataList(cutNodeList(0, tempNodeList)));							
							}
						}
					}catch (Exception e) {
						e.printStackTrace();
					}				
				}
			}
		}catch (Exception e) {
			logger.error("采集ESL信息出错"+e);
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
			params.put("record", value);
			params.put("trackingType","any");
		}
		else if(type.equals(TrackingAdapter.STYPE_BOOKING))			//订餐
		{
			params.put("record", value);
			params.put("trackingType","any");
		}else if(type.equals(TrackingAdapter.STYPE_CONTAINER))		//container
		{
			params.put("record", value);
			params.put("trackingType","any");
		}
		return params;
	}
}
