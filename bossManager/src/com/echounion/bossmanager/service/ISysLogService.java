package com.echounion.bossmanager.service;

import java.util.List;
import com.echounion.bossmanager.entity.SysLog;

/**
 * BOSS日志记录业务接口
 * @author 胡礼波
 * 2012-11-1 上午10:20:37
 */
public interface ISysLogService {

	/**
	 * 获得所有的日志记录
	 * @author 胡礼波
	 * 2012-11-1 上午10:25:58
	 * @return
	 */
	public List<SysLog> getAllLog();
	
	/**
	 * 删除日志
	 * @author 胡礼波
	 * 2012-11-1 上午10:26:48
	 * @param logIds
	 * @return
	 */
	public int delLog(Integer[] logIds);
	
	/**
	 * 
	 * @author 胡礼波
	 * 2012-11-1 上午10:27:12
	 * @return
	 */
	public int getCountLog();
}
