package com.echounion.boss.persistence.esb;

import java.util.List;

import com.echounion.boss.entity.EsbSoftWare;
import com.echounion.boss.entity.dto.Caller;

/**
 * SASS软件数据接口
 * @author 胡礼波
 * 2012-11-5 下午02:22:05
 */
public interface EsbSoftWareMapper {

	/**
	 * 根据软件编号查找对应的软件基本信息
	 * @author 胡礼波
	 * 2012-11-3 下午01:53:49
	 * @param softId
	 * @return
	 */
	public EsbSoftWare getSoftWareById(int softId);
	
	/**
	 * 得到所有的调用方
	 * @author 胡礼波
	 * 2013-7-25 上午11:17:05
	 * @return
	 */
	public List<Caller> getCallers();
	
}
