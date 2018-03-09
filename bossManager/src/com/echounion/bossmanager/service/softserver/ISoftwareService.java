package com.echounion.bossmanager.service.softserver;

import java.util.List;

import com.echounion.bossmanager.entity.EsbSoftWare;

/**
 * 软件业务接口
 * @author 胡礼波
 * 2012-11-6 下午07:19:39
 */
public interface ISoftwareService {

	/**
	 * 根据ID查询对应的软件 如果ID集合为空则查询所有的软件集合
	 * @author 胡礼波
	 * 2012-11-6 下午07:20:02
	 * @return
	 */
	public List<EsbSoftWare> getSoftwareList(Integer... softId);
	
	
	/**
	 * 查询服务器上所部署的软件集合
	 * @author 胡礼波
	 * 2012-11-9 上午10:38:49
	 * @param serverId
	 * @return
	 */
	public List<EsbSoftWare> getSoftwareByServerId(int serverId);
	
	/**
	 * 获得指定服务器所部署软件的数量
	 * @author 胡礼波
	 * 2012-11-9 上午10:44:06
	 * @param serverId
	 * @return
	 */
	public int getServerSoftCount(int serverId);
	
	/**
	 * 获得软件的总和
	 * @author 胡礼波
	 * 2012-11-6 下午07:20:15
	 * @return
	 */
	public int getCount();
	
	/**
	 * 添加软件
	 * @author 胡礼波
	 * 2013-7-18 下午4:56:03
	 * @param soft
	 * @param serviceDirId 服务接口ID集合
	 * @return
	 */
	public int addSoftware(EsbSoftWare soft,Integer...serviceDirId);
	
	/**
	 * 修改软件
	 * @author 胡礼波
	 * 2012-11-6 下午07:20:53
	 * @param soft
	 * @return
	 */
	public int editSoftware(EsbSoftWare soft,Integer...dirIds);
	
	
	/**
	 * 删除软件
	 * @author 胡礼波
	 * 2012-11-6 下午07:21:18
	 * @param softIds
	 * @return
	 */
	public int delSoftware(Integer...softId);
	
	/**
	 * 根据Id获得软件
	 * @author 胡礼波
	 * 2012-11-7 上午09:35:55
	 * @param sid
	 * @return
	 */
	public EsbSoftWare getSoftwareById(int  softId);
	
	/**
	 * 更新软件状态
	 * @author 胡礼波
	 * 2012-11-7 上午10:24:34
	 * @param sid
	 * @param opName
	 * @param value
	 * @return
	 */
	public int editSoftwareStatus(int sid,String opName,boolean value);

	/**
	 * 查看软件代号是否存在
	 *@author
	 * 2013-7-22 下午6:38:05
	 * @param softCode
	 * @return
	 */
	public boolean softCodeExist(String softCode);
}
