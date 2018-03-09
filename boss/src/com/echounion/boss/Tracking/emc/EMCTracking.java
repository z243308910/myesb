package com.echounion.boss.Tracking.emc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

public class EMCTracking extends AbstractTrack {

	private Logger logger=Logger.getLogger(EMCTracking.class);
	
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
				if(params.get("SEL").equals("s_cntr"))			//箱
				{
					return getContainerInfo(parser);
				}
				else if(params.get("SEL").equals("bk"))
				{
					return getBookingInfo(parser);
				}else if(params.get("SEL").equals("s_bl"))
				{
					return getBillInfo(parser);
				}
			}
		}catch (Exception e) {
			logger.error("EMC数据采集出错"+e);
		}
		return info;
	}
	

	@Override
	public List<String> getDataList(NodeList nodeList) {
		List<String> list = new ArrayList<String>();
		TableColumn column=null;
		int colspan=0;
		for (Node node : nodeList.toNodeArray()) {
			if(node instanceof TableColumn)
			{
				column=(TableColumn)node;
				if(column.getAttribute("colspan")!=null)
				{
					colspan=Integer.parseInt(column.getAttribute("colspan"));
					for(int i=0;i<colspan;i++)
					{
						if(i==0)
						{
							list.add(filterHtml(node.toPlainTextString().trim()));
						}else
						{
							list.add("nbsp;");
						}
					}
				}else
				{
					list.add(filterHtml(node.toPlainTextString().trim()));
				}
			}
		}
		return list;
	}
	
	@Override
	public List<String> getTitles(NodeList nodeList) {
		List<String> list = new ArrayList<String>();
		TableColumn column=null;
		int colspan=0;
		for (Node node : nodeList.toNodeArray()) {
			if(node instanceof TableColumn)
			{
				column=(TableColumn)node;
				if(column.getAttribute("colspan")!=null)
				{
					colspan=Integer.parseInt(column.getAttribute("colspan"));
					for(int i=0;i<colspan;i++)
					{
						list.add("&nbsp;");
					}
				}else
				{
					list.add(filterHtml(node.toPlainTextString().trim()));
				}
			}
		}
		return list;
	}

	
	/**
	 * BillNo
	 * @author 胡礼波
	 * 2012-11-23 下午06:54:27
	 * @return
	 */
	public TrackingInfo getBillInfo(Parser parser)
	{
		TrackingInfo info =null;
		info= new TrackingInfo(5); // 总信息
		info.setTitle("EMC Tracking Result");
		try
		{
			AndFilter filter = getAndFilter("table", "bgcolor", "#999999");
			List<TrackingInfo> subList=new ArrayList<TrackingInfo>();
			TrackingInfo subTracking=null;						//子单
			NodeList nodeList = parser.parse(filter);
			if (nodeList.size() > 0) {
				
				Node tempNode=null;
				NodeList tdNodeList=null;
				try
				{
					NodeList firstTableNodeList=nodeList.elementAt(0).getChildren();	//获得第一个table
					NodeList firstTrNodeList=filterNode(firstTableNodeList,TableRow.class);	//获得tr类型的nodeList
					tdNodeList=filterNode(firstTrNodeList.elementAt(0).getChildren(),TableColumn.class);
					info.addHeadTitle(" ");info.addHeadTitle(" ");info.addHeadTitle(" ");info.addHeadTitle(" ");info.addHeadTitle(" ");
					info.addRowDataList(getDataList(cutNodeList(0,tdNodeList)));
				}catch (Exception e) {
					e.printStackTrace();
				}
				
				try
				{
					subTracking=new TrackingInfo(4);
					NodeList secondTableNodeList = nodeList.elementAt(1).getChildren(); // 获得第二个table
					NodeList secondTrNodeList = filterNode(secondTableNodeList, TableRow.class); // 获得tr类型的nodeList
					subTracking.addHeadTitle("");subTracking.addHeadTitle("");subTracking.addHeadTitle("");subTracking.addHeadTitle("");		
					for (int i=0;i<secondTrNodeList.size();i++) {
						tempNode=secondTrNodeList.elementAt(i);
						tdNodeList=filterNode(tempNode.getChildren(),TableColumn.class);
						if(i==0)
						{
							subTracking.setTitle(filterHtml(tempNode.toPlainTextString().trim()));		//设置子单标题
						}
						else if(i>0)
						{
						}
						subTracking.addRowDataList(getDataList(cutNodeList(0,tdNodeList)));
					}
					subList.add(subTracking);
				}catch (Exception e) {
					e.printStackTrace();
				}
				
				try
				{
					NodeList thridTableNodeList = nodeList.elementAt(2).getChildren(); // 获得第三个table
					NodeList thridTrNodeList = filterNode(thridTableNodeList, TableRow.class); // 获得tr类型的nodeList
					subTracking=new TrackingInfo(7);
					tempNode=null;
					tdNodeList=null;
					for (int i=0;i<thridTrNodeList.size();i++) {
						tempNode=thridTrNodeList.elementAt(i);
						tdNodeList=filterNode(tempNode.getChildren(),TableColumn.class);
						if(i==0)
						{
							subTracking.setTitle(tempNode.toPlainTextString().trim());		//设置子单标题
						}
						if(i==1)														//column列名
						{
							subTracking.setHeadTitles(getTitles(cutNodeList(0, subTracking.getDataColumnSize(),tdNodeList)));		
						}
						else if(i>1)
						{
							subTracking.addRowDataList(getDataList(cutNodeList(0,tdNodeList)));
						}
					}
					subList.add(subTracking);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			info.addSubTrackingInfo(subList);
		}catch (Exception e) {
			logger.error("采集EMC BILL NO 出错"+e);
		}
		return info;
	}
	
	/**
	 * BookingNo
	 * @author 胡礼波
	 * 2012-11-23 下午06:54:27
	 * @return
	 */
	public TrackingInfo getBookingInfo(Parser parser)
	{
		TrackingInfo info =null;
		info= new TrackingInfo(5); // 总信息
		info.setTitle("EMC Tracking Result");
		try
		{
			AndFilter filter = getAndFilter("table", "bgcolor", "#999999");
			List<TrackingInfo> subList=new ArrayList<TrackingInfo>();
			TrackingInfo subTracking=null;						//子单
			NodeList nodeList = parser.parse(filter);
			if (nodeList.size() > 0) {
				
				Node tempNode=null;
				NodeList tdNodeList=null;
				try
				{
					NodeList firstTableNodeList=nodeList.elementAt(0).getChildren();	//获得第一个table
					NodeList firstTrNodeList=filterNode(firstTableNodeList,TableRow.class);	//获得tr类型的nodeList
					tdNodeList=filterNode(firstTrNodeList.elementAt(0).getChildren(),TableColumn.class);
					info.addHeadTitle(" ");info.addHeadTitle(" ");info.addHeadTitle(" ");info.addHeadTitle(" ");info.addHeadTitle(" ");
					info.addRowDataList(getDataList(cutNodeList(0,tdNodeList)));
				}catch (Exception e) {
					e.printStackTrace();
				}
				
				try
				{
					subTracking=new TrackingInfo(7);
					NodeList secondTableNodeList = nodeList.elementAt(1).getChildren(); // 获得第二个table
					NodeList secondTrNodeList = filterNode(secondTableNodeList, TableRow.class); // 获得tr类型的nodeList
					for (int i=0;i<secondTrNodeList.size();i++) {
						tempNode=secondTrNodeList.elementAt(i);
						tdNodeList=filterNode(tempNode.getChildren(),TableColumn.class);
						if(i==0)
						{
							subTracking.setTitle(tempNode.toPlainTextString().trim());		//设置子单标题
						}
						if(i==1)														//column列名
						{
							subTracking.setHeadTitles(getTitles(cutNodeList(0, 7,tdNodeList)));		
						}
						else if(i>1)
						{
							subTracking.addRowDataList(getDataList(cutNodeList(0,7,tdNodeList)));
						}
					}
					subList.add(subTracking);
				}catch (Exception e) {
					e.printStackTrace();
				}
				
				try
				{
					NodeList threeTableNodeList = nodeList.elementAt(2).getChildren(); // 获得第三个table
					NodeList threeTrNodeList = filterNode(threeTableNodeList, TableRow.class); // 获得tr类型的nodeList
					subTracking=new TrackingInfo(4);
					tempNode=null;
					tdNodeList=null;
					for (int i=0;i<threeTrNodeList.size();i++) {
						tempNode=threeTrNodeList.elementAt(i);
						tdNodeList=filterNode(tempNode.getChildren(),TableColumn.class);
						if(i==0)
						{
							subTracking.setTitle(tempNode.toPlainTextString().trim());		//设置子单标题
						}
						if(i==1)														//column列名
						{
							subTracking.setHeadTitles(getTitles(cutNodeList(0, subTracking.getDataColumnSize(),tdNodeList)));		
						}
						else if(i>1)
						{
							subTracking.addRowDataList(getDataList(cutNodeList(0,tdNodeList)));
						}
					}
					subList.add(subTracking);
				}catch (Exception e) {
					e.printStackTrace();
				}
				
				try
				{
					NodeList fourTableNodeList = nodeList.elementAt(3).getChildren(); // 获得第三个table
					NodeList fourTrNodeList = filterNode(fourTableNodeList, TableRow.class); // 获得tr类型的nodeList
					subTracking=new TrackingInfo(5);
					tempNode=null;
					tdNodeList=null;
					for (int i=0;i<fourTrNodeList.size();i++) {
						tempNode=fourTrNodeList.elementAt(i);
						tdNodeList=filterNode(tempNode.getChildren(),TableColumn.class);
						if(i==0)
						{
							subTracking.setTitle(tempNode.toPlainTextString().trim());		//设置子单标题
						}
						if(i==1)														//column列名
						{
							subTracking.setHeadTitles(getTitles(cutNodeList(0, subTracking.getDataColumnSize(),tdNodeList)));		
						}
						else if(i>1)
						{
							subTracking.addRowDataList(getDataList(cutNodeList(0,tdNodeList)));
						}
					}
					subList.add(subTracking);
				}catch (Exception e) {
					e.printStackTrace();
				}
				
				try
				{
					NodeList fiveTableNodeList = nodeList.elementAt(4).getChildren(); // 获得第五个table
					NodeList fiveTrNodeList= filterNode(fiveTableNodeList, TableRow.class); // 获得tr类型的nodeList
					subTracking=new TrackingInfo(10);
					tempNode=null;
					tdNodeList=null;
					for (int i=0;i<fiveTrNodeList.size();i++) {
						tempNode=fiveTrNodeList.elementAt(i);
						tdNodeList=filterNode(tempNode.getChildren(),TableColumn.class);
						if(i==0)
						{
							subTracking.setTitle(tempNode.toPlainTextString().trim());		//设置子单标题
						}
						if(i==1)														//column列名
						{
							subTracking.setHeadTitles(getTitles(cutNodeList(0, subTracking.getDataColumnSize(),tdNodeList)));		
						}
						else if(i>1)
						{
							subTracking.addRowDataList(getDataList(cutNodeList(0,tdNodeList)));
						}
					}
					subList.add(subTracking);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			info.addSubTrackingInfo(subList);
		}catch (Exception e) {
			logger.error("采集EMC BOOKING NO 出错"+e);
		}
		return info;
	}
	
	/**
	 * Container信息
	 * @author 胡礼波
	 * 2012-11-23 下午05:14:21
	 * @param parser
	 * @return
	 */
	public TrackingInfo getContainerInfo(Parser parser)
	{
		TrackingInfo info =null;
		info= new TrackingInfo(6); // 总信息
		info.setTitle("EMC Tracking Result");
		try
		{
			AndFilter filter = getAndFilter("table", "bgcolor", "#999999");
			NodeList nodeList = parser.parse(filter);
			if (nodeList.size() > 0) {
				nodeList = nodeList.elementAt(0).getChildren(); // 获得第三个table
				nodeList = filterNode(nodeList, TableRow.class); // 获得tr类型的nodeList
				Node tempNode=null;
				NodeList tdNodeList=null;
				for (int i=0;i<nodeList.size();i++) {
					tempNode=nodeList.elementAt(i);
					tdNodeList=filterNode(tempNode.getChildren(),TableColumn.class);
					if(i==1)		//column列名
					{
						info.setHeadTitles(getTitles(cutNodeList(0, info.getDataColumnSize(),tdNodeList)));		
					}
					else if(i>1)
					{
					info.addRowDataList(getDataList(cutNodeList(0,tdNodeList)));
					}
				}
			}
		}catch (Exception e) {
			logger.error("采集MEC Continer信息异常"+e);
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
			params.put("SEL", "s_bl");
			params.put("TYPE", "BL");
			params.put("BL", value);
			params.put("NO", value);
		}
		else if(type.equals(TrackingAdapter.STYPE_BOOKING))			//订餐
		{
			params.put("SEL", "bk");
			params.put("TYPE", "BK");
			params.put("bkno", value);
			params.put("NO", value);
		}else if(type.equals(TrackingAdapter.STYPE_CONTAINER))		//container
		{
			params.put("SEL", "s_cntr");
			params.put("TYPE", "CNTR");
			params.put("CNTR", value);
			params.put("NO", value);
		}else
		{
			logger.warn("EMS search type is null!");
		}
		logger.info("EMC Search value is:"+value);
		return params;
	}
}
