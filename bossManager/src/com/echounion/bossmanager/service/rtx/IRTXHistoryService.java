package com.echounion.bossmanager.service.rtx;

import java.util.List;

import com.echounion.bossmanager.entity.RTXHistory;

/**
 *  RTX历史记录业务接口
 * @author 张霖
 * 2013-3-25 上午11:01:18
 */
public interface IRTXHistoryService {

	/**
	 * 获取RTX所有记录列表
	 * @author 张霖
	 * 2013-3-25 上午10:35:03
	 * @return
	 */
	public List<RTXHistory> getRTXHistory(RTXHistory rtxHistory);

	/**
	 * 查看记录的总数
	 * @author 张霖 
	 * 2013-3-25 上午10:35:27
	 * @return
	 */
	public int getCount(RTXHistory rtxHistory) ;

	/**
	 * 删除邮件记录
	 * @author 张霖
	 * 2013-3-25 下午1:44:34
	 * @param ids
	 * @return
	 */
	public int delRtxHistory(Integer[] ids);

}
