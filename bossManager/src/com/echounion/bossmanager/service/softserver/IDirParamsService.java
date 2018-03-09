package com.echounion.bossmanager.service.softserver;

import java.util.List;

import com.echounion.bossmanager.entity.EsbServiceDirParam;

/**
 * 服务接口参数业务接口
 * @author 胡礼波
 * 2013-7-18 上午10:12:31
 */
public interface IDirParamsService {

	/**
	 * 添加接口参数
	 * @author 胡礼波
	 * 2013-7-18 上午10:13:15
	 * @param params
	 * @return
	 */
	public int addParams(EsbServiceDirParam... params);

	/**
	 * 根据服务ID获取参数列表
	 *@author
	 * 2013-7-23 下午5:58:12
	 * @param serviceId
	 * @return
	 */
	public List<EsbServiceDirParam> getDirParamsByServiceId(Integer serviceId);
	

	/**
	 * 编辑参数信息
	 *@author 张霖
	 * 2013-7-24 下午6:21:07
	 * @param paramsList
	 */
	public int editParams(EsbServiceDirParam[] paramsList);

	/**
	 * 查看服务参数信息
	 *@author 张霖
	 * 2013-7-25 上午9:27:57
	 * @param paramId
	 * @return
	 */
	public EsbServiceDirParam getDirParamById(int paramId);

	/**
	 * 删除指定服务参数
	 *@author 张霖
	 * 2013-7-25 下午5:28:57
	 * @param ids
	 * @return
	 */
	public int delParams(Integer[] ids);

}
