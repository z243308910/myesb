package com.echounion.boss.persistence.esb;

import java.util.List;
import com.echounion.boss.entity.dto.Provider;

/**
 * 服务接口数据接口
 * @author 胡礼波
 * 2013-7-11 下午1:54:11
 */
public interface EsbServiceDirMapper {

	/**
	 * 获得服务接口目录相关信息
	 * @author 胡礼波
	 * 2013-7-11 下午1:54:50
	 * @return
	 */
	public List<Provider> getServiceDirInfo();
}
