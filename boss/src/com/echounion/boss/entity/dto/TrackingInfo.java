package com.echounion.boss.entity.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

/**
 * 跟踪查询实体类DTO
 * @author 胡礼波
 * 2:45:09 PM Sep 27, 2012
 */
public class TrackingInfo {

	private int columnSize=0;	//列的数目
	private int index=0;	//控制元素的索引
	private String title; // 存放主标题
	private List<String> headTitles; // 存放标题名称-----相当于列表的列名
	private List<String[]> dataList = null; // 数据集合--相当于一张表的数据
	private List<TrackingInfo> subTrackingInfo;		//解决主单下还包含子单的情况

	public TrackingInfo()
	{
		dataList = new ArrayList<String[]>();
		headTitles = new ArrayList<String>();
		subTrackingInfo = new ArrayList<TrackingInfo>();
	}
	
	/**
	 * 设置表格的头部名称--即列名
	 * @author 胡礼波
	 * 2:47:01 PM Sep 27, 2012 
	 * @param titles
	 */
	public void setHeadTitles(List<String> titles) {
		this.headTitles = titles;
		if(titles!=null)
		{
		this.columnSize=titles.size();
		}
	}

	/**
	 * 添加数据---为表格每行添加数据
	 * @author 胡礼波
	 * 2:47:10 PM Sep 27, 2012 
	 * @param dataList
	 */
	public void addRowDataList(List<String> dataList) {
		int index=0;
		String datas[] = null;
		for(String data:dataList)
		{
			if(index % getDataColumnSize()==0)		//添加数据每length个数据为一行
			{
				datas=new String[getDataColumnSize()];
				index=0;
				this.dataList.add(datas);
			}
			datas[index]=data;
			index++;
		}
	}

	public TrackingInfo(int length) {
		columnSize=length;
		dataList = new ArrayList<String[]>();
		headTitles = new ArrayList<String>();
		subTrackingInfo = new ArrayList<TrackingInfo>();
	}

	/**
	 * 添加列名
	 * @author 胡礼波
	 * 2:50:41 PM Sep 27, 2012 
	 * @param title
	 */
	public void addHeadTitle(String title) {
		if(title==null)
		{
			title=" ";
		}
		headTitles.add(title);
	}

	/**
	 * 为每行添加数据
	 * @author 胡礼波
	 * 2:51:37 PM Sep 27, 2012 
	 * @param data
	 */
	public void addRowData(String data) {
		String datas[] = null;
		if(index % getDataColumnSize()==0)						//达到每行所允许的列数目----则重新添加一行
		{
			datas =new String[getDataColumnSize()];
			dataList.add(datas);
			index=0;
		}
		else												//没有达到每行所允许的列数目----则获得最后一个元素并添加数据到最后一个元素中
		{
			datas=dataList.get(dataList.size()-1);			//
		}
		datas[index]=data;
		index++;
	}
	
	/**
	 * 添加子单
	 * @author 胡礼波
	 * 2:52:04 PM Sep 27, 2012 
	 * @param subTrackingInfo
	 */
	public void addSubTrackingInfo(List<TrackingInfo> subTrackingInfo) {
		if(CollectionUtils.isEmpty(getSubTrackingInfo()))
		{
				this.subTrackingInfo=new ArrayList<TrackingInfo>();
		}
		getSubTrackingInfo().addAll(subTrackingInfo);
	}
	
	public void addSubTrackingInfo(TrackingInfo subTrackingInfo) {
		if(CollectionUtils.isEmpty(getSubTrackingInfo()))
		{
				this.subTrackingInfo = new ArrayList<TrackingInfo>();
		}
		getSubTrackingInfo().add(subTrackingInfo);
	}
	
	
	public List<String> getHeadTitles() {
		return headTitles;
	}

	public int getDataColumnSize() // 获得每行的列数目
	{
		return columnSize;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<TrackingInfo> getSubTrackingInfo() {
		return subTrackingInfo;
	}

	public List<String[]> getDataList() {
		return dataList;
	}
}
