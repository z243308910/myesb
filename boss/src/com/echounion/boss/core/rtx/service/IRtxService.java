package com.echounion.boss.core.rtx.service;


import com.echounion.boss.common.enums.RtxStatus;
import com.echounion.boss.core.rtx.Rtx;
import com.echounion.boss.entity.RtxHistory;

/**
 * RXT业务接口
 * @author 胡礼波
 * 2013-3-13 下午4:49:04
 */
public interface IRtxService {

	/**
	 * 发送提示消息
	 * @author 胡礼波
	 * 2013-3-13 下午4:57:09
	 * @param rtxDTO
	 */
	public RtxStatus send(Rtx rtx);
	
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
	public int editRtxHistoryStatus(int rtxId,int status);
	
	/**
	 * 根据RXTID查找对应的RTX
	 * @author 胡礼波
	 * 2013-3-22 上午10:32:41
	 * @param rtxId
	 * @return
	 */
	public RtxHistory getRtxHistory(int rtxId);
}
