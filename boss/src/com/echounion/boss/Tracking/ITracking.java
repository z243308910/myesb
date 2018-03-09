package com.echounion.boss.Tracking;

import java.util.List;

import org.htmlparser.util.NodeList;

/**
 * 数据采集接口 
 * @author 胡礼波
 * 2:16:55 PM Sep 27, 2012
 */
public interface ITracking {

	/**
	 * 采集数据标题
	 * @author 胡礼波
	 * 2:19:23 PM Sep 27, 2012 
	 * @return
	 */
	public List<String> getTitles(NodeList nodeList);
	
	/**
	 * 采集数据
	 * @author 胡礼波
	 * 2:20:15 PM Sep 27, 2012 
	 * @return
	 */
	public List<String> getDataList(NodeList nodeList);
}
