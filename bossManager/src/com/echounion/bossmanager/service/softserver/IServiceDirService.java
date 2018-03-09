package com.echounion.bossmanager.service.softserver;

import java.util.List;

import com.echounion.bossmanager.entity.EsbServiceDir;
import com.echounion.bossmanager.entity.EsbServiceDirParam;

/**
 * 软件服务业务接口
 * @author 胡礼波
 * 2012-11-8 上午09:31:20
 */
public interface IServiceDirService {

	/**
	 * 添加软件服务项
	 * @author 胡礼波
	 * 2012-11-14 下午12:03:52
	 * @param softService
	 * @return
	 */
	public int addServiceDir(EsbServiceDir softService,List<EsbServiceDirParam> params);
	
	/**
	 * 编辑软件服务项
	 * @author 胡礼波
	 * 2012-11-14 下午12:04:28
	 * @param softService
	 * @return
	 */
	public int editServiceDir(EsbServiceDir softService);
	
	/**
	 * 根据软件ID查找该软件提供的服务
	 * @author 胡礼波
	 * 2012-11-8 上午11:03:40
	 * @param softId
	 * @return
	 */
	public List<EsbServiceDir> getServiceDirBySoftId(int softId);
	
	/**
	 * 获得服务接口下的服务接口总和
	 * @author 胡礼波
	 * 2013-7-19 上午9:41:02
	 * @param softId 软件ID
	 * @return
	 */
	public int getServiceDirCount(int softId);
	
	
	/**
	 * 根据服务ID查找服务列表
	 * @author 胡礼波
	 * 2012-11-13 上午10:54:15
	 * @param serviceId
	 * @return
	 */
	public List<EsbServiceDir> getServiceDirById(Integer... serviceId);
	
	/**
	 * 根据软件ID删除对应的软件接口
	 * @author 胡礼波
	 * 2013-7-16 下午2:06:16
	 * @param softId
	 * @return
	 */
	public int delServiceDirBySoftId(Integer...softId);
	
	/**
	 * 删除服务接口数据 根据服务接口ID
	 * @author 胡礼波
	 * 2013-7-18 下午6:24:32
	 * @param dirId
	 * @return
	 */
	public int delServiceDirById(Integer...dirId);

	/**
	 * 检查服务代号是否存在
	 *@author 张霖
	 * 2013-7-22 下午6:45:49
	 * @param serviceCode
	 * @return
	 */
	public boolean serviceCodeExist(String serviceCode);
	
	/** 
	 * 根据服务ID获取服务信息
	 *@author 张霖
	 * 2013-7-24 下午5:21:25
	 * @return
	 */
	public EsbServiceDir getDirById(int serviceId);
}
