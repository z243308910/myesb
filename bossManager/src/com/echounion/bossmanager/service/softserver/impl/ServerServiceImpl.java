package com.echounion.bossmanager.service.softserver.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.echounion.bossmanager.common.security.Boss;
import com.echounion.bossmanager.common.security.annotation.ActionModel;
import com.echounion.bossmanager.common.util.ContextUtil;
import com.echounion.bossmanager.common.util.StringUtil;
import com.echounion.bossmanager.dao.softserver.IServerDao;
import com.echounion.bossmanager.entity.EsbServer;
import com.echounion.bossmanager.entity.EsbSoftWare;
import com.echounion.bossmanager.service.softserver.IServerService;
import com.echounion.bossmanager.service.softserver.ISoftwareService;

@Service
@Transactional(propagation=Propagation.SUPPORTS)
@ActionModel(description="服务器管理")
public class ServerServiceImpl implements IServerService {

	private Logger logger=Logger.getLogger(ServerServiceImpl.class);
	
	@Autowired
	private IServerDao serverDao;
	
	@Autowired
	private ISoftwareService softWareService;
	
	public EsbServer getServerById(int serverId) {
		return serverDao.getObject(serverId);
	}

	@Transactional
	@ActionModel(description="添加服务器信息")
	public int addServer(EsbServer server,Integer...softWareId) {
		checkServer(server);
		server.setCreator(ContextUtil.getContextLoginUser().getUserName());
		server.setCreateDate(new Date());
		if(isExistIp(server.getServerIp()))
		{
			logger.warn("已存在相同的服务器IP，添加失败!");
			return 0;
		}
		int result=serverDao.saveObject(server);
		if(result > 0)
		{
			if(!ArrayUtils.isEmpty(softWareId))
			{
				List<EsbSoftWare> list=softWareService.getSoftwareList(softWareId);
				Iterator<EsbSoftWare> it=list.iterator();
				EsbSoftWare soft=null;
				while(it.hasNext())
				{
					soft = it.next();
					if(soft.getServerId()!=0)			//判断该软件是否已经绑定到服务器
					{
						continue;
					}
					for (Integer softId : softWareId) {
						if(softId == soft.getId())
						{
							soft.setServerId(server.getId());
							String authorKey=softId+"-"+StringUtil.getCharAndNumr(9)+"-"+server.getId();
							soft.setAuthKey(Boss.createAuthorKey(authorKey));
							softWareService.editSoftware(soft);
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * 服务器基本信息校验
	 * @author 胡礼波
	 * 2012-11-8 下午05:27:34
	 * @param server
	 */
	private void checkServer(EsbServer server) {
		Assert.notNull(server);
		Assert.notNull(server.getServerIp(),"服务器IP为空!");
		Assert.notNull(server.getServerName(),"服务器名称为空!");
	}

	@Transactional
	@ActionModel(description="编辑服务器信息")
	public int editServer(EsbServer server,Integer...softIds) {
		checkServer(server);
		int result=serverDao.updateObject(server);
		if(result>0)
		{
			if(!ArrayUtils.isEmpty(softIds))
			{
				List<EsbSoftWare> softWareList=softWareService.getSoftwareList(softIds);
				for (EsbSoftWare esbSoftWare : softWareList) {			//绑定软件
					if(esbSoftWare.getServerId()!=0)				   //已经绑定的不需要再绑定
					{
						continue;
					}
					esbSoftWare.setServerId(server.getId());
					String authorKey=esbSoftWare.getId()+"-"+StringUtil.getCharAndNumr(9)+"-"+server.getId();
					esbSoftWare.setAuthKey(Boss.createAuthorKey(authorKey));					
					softWareService.editSoftware(esbSoftWare);
				}
			}
		}
		return result;
	}

	public List<EsbServer> getServerList() {
		return serverDao.getAll();
	}

	public int getCount() {
		return serverDao.getCount();
	}

	public List<EsbServer> getServerBySoftId(int softId, boolean flag) {
		return serverDao.getServerBySoftId(softId, flag);
	}

	public int getServerBySoftCount(int softId, boolean flag) {
		return serverDao.getServerBySoftCount(softId, flag);
	}

	public boolean isExistIp(String ip) {
		Assert.hasText(ip,"服务器IP为空!");
		return serverDao.getCountByProperty("serverIp",ip)>0?true:false;
	}

	@Override
	@Transactional
	@ActionModel(description="删除服务器")
	public int delServer(Integer... serverId) {
		for (Integer ssId : serverId) {
			List<EsbSoftWare> list=softWareService.getSoftwareByServerId(ssId);
			for (EsbSoftWare esbSoftWare : list) {		//删除软件和服务器之间的关联关系
				esbSoftWare.setServerId(0);
				esbSoftWare.setAuthKey(null);
			}
		}
		return serverDao.removeObject(serverId, "id");
	}

}
