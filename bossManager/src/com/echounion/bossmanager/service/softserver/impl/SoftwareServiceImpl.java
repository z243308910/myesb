package com.echounion.bossmanager.service.softserver.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.echounion.bossmanager.common.enums.ProtocolType;
import com.echounion.bossmanager.common.security.Boss;
import com.echounion.bossmanager.common.security.annotation.ActionModel;
import com.echounion.bossmanager.common.util.ContextUtil;
import com.echounion.bossmanager.common.util.NetUtil;
import com.echounion.bossmanager.common.util.StringUtil;
import com.echounion.bossmanager.dao.softserver.ISoftwareDao;
import com.echounion.bossmanager.entity.EsbServer;
import com.echounion.bossmanager.entity.EsbServiceDir;
import com.echounion.bossmanager.entity.SysUser;
import com.echounion.bossmanager.entity.EsbSoftWare;
import com.echounion.bossmanager.service.softserver.IServerService;
import com.echounion.bossmanager.service.softserver.IServiceDirService;
import com.echounion.bossmanager.service.softserver.ISoftwareService;

@Service
@Transactional(propagation=Propagation.SUPPORTS)
@ActionModel(description="软件管理")
public class SoftwareServiceImpl implements ISoftwareService {

	@Autowired
	private ISoftwareDao softDao;
	@Autowired
	private IServerService serverService;
	
	@Autowired
	private IServiceDirService serviceDir;
	
	@Transactional
	@ActionModel(description="添加软件")
	public int addSoftware(EsbSoftWare soft,Integer...serviceDirIds) {
		checkSoftware(soft);
		int count=softDao.getCountByProperty("softCode",soft.getSoftCode());
		Assert.isTrue(count<=0,"存在相同的软件代号，保存失败!");
		SysUser admin=ContextUtil.getContextLoginUser();
		Assert.notNull(admin);
		soft.setCreateDate(new Date());
		soft.setCreator(admin.getUserName());
		if(StringUtils.isBlank(soft.getSoftUrl()) && soft.getServerId()!=0)	//如果访问地址为空，并且绑定了服务器 则默认为 服务器对应的协议地址
		{
			EsbServer server=serverService.getServerById(soft.getServerId());
			Assert.notNull(server,"软件关联的服务器为空!");
			String ips[] = server.getServerIp().split(",");
			String ip = ips[0];
			soft.setSoftUrl(NetUtil.getProtolUrl(ProtocolType.getProcolType(soft.getSoftProtocol()),ip,soft.getSoftPort()));	
		}		
		int result=softDao.saveObject(soft);
		if(result > 0)
		{
			if(soft.getServerId()!=0)
			{
				String authorKey=soft.getId()+"-"+StringUtil.getCharAndNumr(9)+"-"+soft.getServerId();
				soft.setAuthKey(Boss.createAuthorKey(authorKey));
			}
			if(!ArrayUtils.isEmpty(serviceDirIds))
			{
				List<EsbServiceDir> list=serviceDir.getServiceDirById(serviceDirIds);
				Iterator<EsbServiceDir> it=list.iterator();
				EsbServiceDir dir=null;
				while(it.hasNext())
				{
					dir = it.next();
					if(dir.getSoftId()!=0)			//判断该服务是否已经绑定到软件上
					{
						continue;
					}
					for (Integer serviceDirId : serviceDirIds) {
						if(serviceDirId == dir.getId())
						{
							dir.setSoftId(soft.getId());
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * 软件基本信息检查
	 * @author 胡礼波
	 * 2012-11-7 上午10:01:34
	 * @param soft
	 */
	private void checkSoftware(EsbSoftWare soft) {
		Assert.notNull(soft);
		Assert.notNull(soft.getSoftName());
	}

	@Transactional
	@ActionModel(description="删除软件")
	public int delSoftware(Integer...softId) {
		if(ArrayUtils.isEmpty(softId))
		{
			return 0;
		}
		serviceDir.delServiceDirBySoftId(softId);		//删除该软件下的接口
		return softDao.delSoftware(softId);
	}

	@Transactional
	@ActionModel(description="编辑软件")
	public int editSoftware(EsbSoftWare soft,Integer...dirIds) {
		checkSoftware(soft);
		if(StringUtils.isBlank(soft.getSoftUrl()) && soft.getServerId()!=0)	//如果访问地址为空，并且绑定了服务器 则默认为 服务器对应的协议地址
		{
			EsbServer server=serverService.getServerById(soft.getServerId());
			Assert.notNull(server,"软件关联的服务器为空!");
			soft.setSoftUrl(NetUtil.getProtolUrl(ProtocolType.getProcolType(soft.getSoftProtocol()),server.getServerIp(),soft.getSoftPort()));	
		}
		int result=softDao.updateObject(soft);
		if(result > 0)
		{
			if(!ArrayUtils.isEmpty(dirIds))
			{
				List<EsbServiceDir> list=serviceDir.getServiceDirById(dirIds);
				Iterator<EsbServiceDir> it=list.iterator();
				EsbServiceDir dir=null;
				while(it.hasNext())
				{
					dir = it.next();
					if(dir.getSoftId()!=0)			//判断该服务是否已经绑定到软件上
					{
						continue;
					}
					for (Integer serviceDirId : dirIds) {
						if(serviceDirId == dir.getId())
						{
							dir.setSoftId(soft.getId());
						}
					}
				}
			}
		}
		return result;
	}

	public int getCount() {
		return softDao.getCount();
	}

	public List<EsbSoftWare> getSoftwareList(Integer... softId) {
		if(ArrayUtils.isEmpty(softId))			//如果数组为空则查询全部数据
		{
			return softDao.getAll();			
		}
		return softDao.getByProperty("id",softId);
	}

	public EsbSoftWare getSoftwareById(int sid) {
		return softDao.getObject(sid);
	}

	@Transactional
	@ActionModel(description="更新软件信息")
	public int editSoftwareStatus(int sid, String opName, boolean value) {
		Assert.notNull(opName);
		EsbSoftWare soft=softDao.getObject(sid);
		Assert.notNull(soft);
		int count=softDao.updateObject(soft);
		return count;
	}

	public List<EsbSoftWare> getSoftwareByServerId(int serverId) {
		return softDao.getByProperty("serverId",serverId);
	}

	public int getServerSoftCount(int serverId) {
		return softDao.getCountByProperty("serverId",serverId);
	}

	@Override
	public boolean softCodeExist(String softCode) {
		Assert.hasText(softCode,"软件代号为空！");
		int count=softDao.getCountByProperty("softCode",softCode);
		return count>0?true:false;
	}
}
