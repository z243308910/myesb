package com.echounion.boss.persistence.tracking;

import java.util.Date;

import com.echounion.boss.entity.TrackingHistory;

/**
 * 采集记录日志数据接口
 * @author 胡礼波
 * 2012-11-5 上午10:44:34
 */
public interface TrackingHistoryMapper {

	/**
	 * 保存采集日志记录
	 * @author 胡礼波
	 * 2012-11-5 上午10:45:03
	 * @param history
	 */
	public void addTrackingHistory(TrackingHistory history);
	
	/**
	 * 检查一段时间内的跟踪查询总量
	 * @author 胡礼波
	 * Nov 30, 2012 12:04:48 PM
	 * @param begin
	 * @param end
	 * @return
	 */
	public int getCountByTime(Date begin, Date end);
}
