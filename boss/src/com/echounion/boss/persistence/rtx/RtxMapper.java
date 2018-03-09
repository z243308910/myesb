package com.echounion.boss.persistence.rtx;

import java.util.List;
import java.util.Map;

import com.echounion.boss.entity.RtxHistory;

/**
 * Rtx数据接口
 * @author 胡礼波
 * 2013-3-22 上午10:13:10
 */
public interface RtxMapper {

	/**
	 * 添加RTX记录
	 * @author 胡礼波
	 * 2013-3-22 上午10:13:59
	 * @param rtx
	 * @return
	 */
	public int addRtxHistory(RtxHistory rtx);
	
	/**
	 * 更新RTX失败错误次数
	 * @author 胡礼波
	 * 2013-3-22 上午10:20:52
	 * @param rtxId
	 * @return
	 */
	public int editRtxHistoryErrorNum(int rtxId);
	
	/**
	 * 更新RTX历史记录的状态
	 * @author 胡礼波
	 *2013-3-22 上午10:20:52
	 * @param rtxId rtx历史记录
	 * @param status 状态之
	 * @return
	 */
	public int editRtxlHistoryStatus(Map<String,Object> data);
	
	/**
	 * 根据RXTID查找对应的RTX
	 * @author 胡礼波
	 * 2013-3-22 上午10:32:41
	 * @param rtxId
	 * @return
	 */
	public RtxHistory getRtxHistory(int rtxId);
	
	/**
	 * 根据不同参数查询
	 * @author 胡礼波
	 * 2013-3-25 下午1:37:39
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> getRtxHistoryByParam(Map<String,Object> params);
}
