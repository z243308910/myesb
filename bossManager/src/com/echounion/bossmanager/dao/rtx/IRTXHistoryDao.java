package com.echounion.bossmanager.dao.rtx;

import java.util.List;
import java.util.Map;

import com.echounion.bossmanager.dao.IBaseDao;
import com.echounion.bossmanager.entity.RTXHistory;

/**
 * RTX记录数据接口
 * @author 张霖
 * 2013-3-25 上午11:03:29
 */
public interface IRTXHistoryDao extends IBaseDao<RTXHistory>{

	/**
	 * 根据不同条件查询rtx记录
	 * @author 胡礼波
	 * 2013-5-7 下午3:26:47
	 * @param params
	 * @return
	 */
	public List<RTXHistory> getAll(Map<String, Object> params) ;
	
	/**
	 * 根据不同条件查询rtx记录总和
	 * @author 胡礼波
	 * 2013-5-7 下午3:26:58
	 * @param params
	 * @return
	 */
	public int getCount(Map<String, Object> params);
}
