package com.echounion.boss.core.history.service;

import java.util.List;

import com.echounion.boss.entity.dto.DataHistoryDTO;

/**
 * 业务数据记录接口
 * @author 胡礼波
 * 2013-3-25 下午1:50:21
 */
public interface IDataHistoryService {

	/**
	 * 根据业务类型和业务号查询对应的历史记录
	 * @author 胡礼波
	 * 2013-3-25 下午1:55:55
	 * @param btype
	 * @param bcode
	 * @return
	 */
	public List<DataHistoryDTO> getInfoHistory(String btype,String bcode);
}
