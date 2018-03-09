package com.echounion.boss.Tracking.tsline;

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
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.util.NodeList;

import com.echounion.boss.Tracking.AbstractTrack;
import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.entity.dto.TrackingInfo;

public class TSLINETracking extends AbstractTrack {

	private Logger logger=Logger.getLogger(TSLINETracking.class);
	
	@Override
	public TrackingInfo doTrack(String url, Map<String, Object> params,boolean isPost){
		HttpClient client =getHttpClient();
		params=assembleSelfParams(params);
		HttpUriRequest httpSubmit=getRequestSubmit(url, params, isPost);
		TrackingInfo info =null;
		try
		{
			HttpResponse response = client.execute(httpSubmit);
			HttpEntity entity = response.getEntity();
			info= new TrackingInfo(8); // 总信息
			info.setTitle("TSLINE Tracking Result");
			if (entity != null) {
				String data = EntityUtils.toString(entity);
				Parser parser = new Parser(data);
				AndFilter filter = getAndFilter("table", "id", "DataGrid1");
				NodeList nodeList = parser.parse(filter);
				if (nodeList.size() > 0) {					
					NodeList firstNodeList= nodeList.elementAt(0).getChildren();		//获得所有的tr
					firstNodeList=filterNode(firstNodeList, TableRow.class);
					Node[] nodes=firstNodeList.toNodeArray();
					Node tempNode=null;
					NodeList tempNodeList=null;
					for(int i=0;i<nodes.length;i++)
					{
						tempNode=nodes[i];
						if(i==0)			//标题
						{
							tempNodeList=filterNode(tempNode.getChildren(),TableColumn.class);
							info.setHeadTitles(getTitles(cutNodeList(0, info.getDataColumnSize(),tempNodeList)));							
						}else
						{
							tempNodeList=filterNode(tempNode.getChildren(),TableColumn.class);
							info.addRowDataList(getDataList(cutNodeList(0, info.getDataColumnSize(),tempNodeList)));							
						}
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("TSLINE 数据采集出错"+e);
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
			params.put("TextBox1", value);
		}
		else if(type.equals(TrackingAdapter.STYPE_BOOKING))			//订餐
		{
			params.put("TextBox1", value);
		}else if(type.equals(TrackingAdapter.STYPE_CONTAINER))		//container
		{
			params.put("TextBox1", value);
		}
		params.put("Button1", "Query");
		params.put("__VIEWSTATE", "dDwtNzAzOTEwOTU0O3Q8O2w8aTwxPjs+O2w8dDw7bDxpPDc+O2k8OT47aTwxMT47PjtsPHQ8QDA8cDxwPGw8UGFnZUNvdW50O18hSXRlbUNvdW50O18hRGF0YVNvdXJjZUl0ZW1Db3VudDtEYXRhS2V5czs+O2w8aTwxPjtpPDE+O2k8MT47bDw+Oz4+Oz47Ozs7Ozs7OztAMDxAMDxwPGw8SGVhZGVyVGV4dDtEYXRhRmllbGQ7U29ydEV4cHJlc3Npb247UmVhZE9ubHk7PjtsPENvbnRhaW5lck51bWJlcjtDb250YWluZXJOdW1iZXI7Q29udGFpbmVyTnVtYmVyO288Zj47Pj47Ozs7PjtAMDxwPGw8SGVhZGVyVGV4dDtEYXRhRmllbGQ7U29ydEV4cHJlc3Npb247UmVhZE9ubHk7PjtsPEFjdGl2ZURhdGU7QWN0aXZlRGF0ZTtBY3RpdmVEYXRlO288Zj47Pj47Ozs7PjtAMDxwPGw8SGVhZGVyVGV4dDtEYXRhRmllbGQ7U29ydEV4cHJlc3Npb247UmVhZE9ubHk7PjtsPERlcG90O0RlcG90O0RlcG90O288Zj47Pj47Ozs7PjtAMDxwPGw8SGVhZGVyVGV4dDtEYXRhRmllbGQ7U29ydEV4cHJlc3Npb247UmVhZE9ubHk7PjtsPFBvcnROYW1lO1BvcnROYW1lO1BvcnROYW1lO288Zj47Pj47Ozs7PjtAMDxwPGw8SGVhZGVyVGV4dDtEYXRhRmllbGQ7U29ydEV4cHJlc3Npb247UmVhZE9ubHk7PjtsPFN0YXR1cztTdGF0dXM7U3RhdHVzO288Zj47Pj47Ozs7PjtAMDxwPGw8SGVhZGVyVGV4dDtEYXRhRmllbGQ7U29ydEV4cHJlc3Npb247UmVhZE9ubHk7PjtsPFRyYWZmaWM7VHJhZmZpYztUcmFmZmljO288Zj47Pj47Ozs7PjtAMDxwPGw8SGVhZGVyVGV4dDtEYXRhRmllbGQ7U29ydEV4cHJlc3Npb247UmVhZE9ubHk7PjtsPEJMTk87QkxOTztCTE5PO288Zj47Pj47Ozs7PjtAMDxwPGw8SGVhZGVyVGV4dDtEYXRhRmllbGQ7U29ydEV4cHJlc3Npb247UmVhZE9ubHk7PjtsPFVOQkxOT1c7VU5CTE5PVztVTkJMTk9XO288Zj47Pj47Ozs7Pjs+Oz47bDxpPDA+Oz47bDx0PDtsPGk8MT47PjtsPHQ8O2w8aTwwPjtpPDE+O2k8Mj47aTwzPjtpPDQ+O2k8NT47aTw2PjtpPDc+Oz47bDx0PHA8cDxsPFRleHQ7PjtsPFRTTFUwNTA2NzUwOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwyMDEzLTAxLTIyIDEzOjUyOjAwICAgICA7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPFNLMDAxOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDxTSEVLT1UsIENISU5BOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDxGdWxsIHR1cm4gaW4gQ1ktb3V0Ym91bmQgOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47Pj47Pj47Pj47dDxwPHA8bDxUZXh0Oz47bDxcZTs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8VFNMVTA1MDY3NTAgZmluZCBhYm92ZSBpbmZvcm1hdGlvbjs+Pjs+Ozs+Oz4+Oz4+Oz4bDuXlFbS6rpV3L4gHZdCxxXUBAw==");
		return params;
	}

}
