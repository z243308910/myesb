package com.echounion.boss.Tracking;

import java.util.Date;

import com.echounion.boss.entity.TrackingHistory;
import com.echounion.boss.entity.dto.Tracktor;

/**
 * 采集器业务接口
 * @author 胡礼波
 * 2012-11-3 下午05:01:50
 */
public interface ITrackingService {
	/**
	 * 根据采集器代号查找对应的采集器
	 * @author 胡礼波
	 * 2012-11-3 下午05:23:50
	 * @param code
	 * @return
	 */
	public Tracktor getTracktor(String code);
	
	/**
	 * 添加采集日志记录
	 * @author 胡礼波
	 * 2012-11-5 上午10:43:31
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
