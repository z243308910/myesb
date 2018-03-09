package com.echounion.boss.core.esb.service;

import java.util.List;
import com.echounion.boss.entity.dto.Caller;
import com.echounion.boss.entity.dto.Provider;
import com.echounion.boss.entity.dto.Record;

/**
 *	ESB业务接口
 * @author 胡礼波
 * 2012-11-3 下午01:53:11
 */
public interface IEsbService {

	/**
	 * 调用远程服务
	 * @author 胡礼波
	 * 2013-7-9 下午6:15:51
	 * @param provider 服务提供者
	 * @return
	 */
	public Record invokeService(Provider provider);
	
	/**
	 * 得到所有的服务提供方信息
	 * @author 胡礼波
	 * 2013-7-11 下午3:27:32
	 */
	public List<Provider> getProviders();
	
	/**
	 * 得到所有的调用方信息
	 * @author 胡礼波
	 * 2013-7-25 上午11:10:22
	 * @return
	 */
	public List<Caller> getCallers();
	
	/**
	 * 获得所有的服务器IP
	 * @author 胡礼波
	 * 2013-7-25 下午2:31:09
	 * @return
	 */
	public List<String> getIp();
}
