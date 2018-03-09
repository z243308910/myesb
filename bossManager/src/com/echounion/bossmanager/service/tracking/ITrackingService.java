package com.echounion.bossmanager.service.tracking;

import java.util.List;

import com.echounion.bossmanager.entity.TrackingHistory;

/**
 * 数据采集业务接口
 * @author 胡礼波
 * 2012-11-5 上午11:52:47
 */
public interface ITrackingService {

	/**
	 * 获得采集日志集合
	 * @author 胡礼波
	 * 2012-11-5 上午11:54:28
	 * @return
	 */
	public List<TrackingHistory> getTrackingHistory();
	
	/**
	 * 返回日志总记录数
	 * @author 胡礼波
	 * 2012-11-5 下午12:05:28
	 * @return
	 */
	public int getCount();
	
	/**
	 * 删除采集记录
	 * @author 胡礼波
	 * 2012-11-5 下午01:10:05
	 * @param ids
	 * @return
	 */
	public int delTrackingHistory(Integer[] ids);
}
