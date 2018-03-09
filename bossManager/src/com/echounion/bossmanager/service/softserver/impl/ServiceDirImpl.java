package com.echounion.bossmanager.service.softserver.impl;

import java.util.List;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import com.echounion.bossmanager.common.security.annotation.ActionModel;
import com.echounion.bossmanager.dao.softserver.IServiceDirDao;
import com.echounion.bossmanager.entity.EsbServiceDir;
import com.echounion.bossmanager.entity.EsbServiceDirParam;
import com.echounion.bossmanager.service.softserver.IDirParamsService;
import com.echounion.bossmanager.service.softserver.IServiceDirService;

@Service
@Transactional(propagation=Propagation.SUPPORTS)
@ActionModel(description="软件服务目录")
public class ServiceDirImpl implements IServiceDirService {

	@Autowired
	private IServiceDirDao softServiceDao;
	@Autowired
	private IDirParamsService dirParamsService;
	
	public List<EsbServiceDir> getServiceDirBySoftId(int softId) {
		List<EsbServiceDir> list=softServiceDao.getByProperty("softId",softId);
		return list;
	}

	
	public List<EsbServiceDir> getServiceDirById(Integer... serviceId) {
		if(ArrayUtils.isEmpty(serviceId))
		{
			return null;
		}
		List<EsbServiceDir> list=softServiceDao.getByProperty("id",serviceId);
		return list;
	}

	@Transactional
	@ActionModel(description="添加软件服务项")
	public int addServiceDir(EsbServiceDir softService,List<EsbServiceDirParam> params) {
		
		Assert.notNull(softService,"服务接口为空!");
		Assert.notNull(softService.getServiceCode(),"服务接口代号为空!");
		Assert.notNull(softService.getServiceName(),"服务接口名称为空!");
		Assert.notNull(softService.getServiceUrl(),"服务接口URL为空!");
		
		int count=softServiceDao.saveObject(softService);
		if(count>0)
		{
			if(params!=null)
			{
				EsbServiceDirParam[] paramsList=new EsbServiceDirParam[params.size()];
				EsbServiceDirParam tempParam=null;
				for (int i=0;i<params.size();i++) {
					tempParam=params.get(i);
					if(params.get(i)==null)
					{
						continue;
					}
					tempParam.setServiceDirId(softService.getId());	//给参数绑定接口ID
					paramsList[i]=tempParam;
					
				}
				dirParamsService.addParams(paramsList);
			}
		}
		return count;
	}
	
	@Override
	@Transactional
	@ActionModel(description="删除服务接口")
	public int delServiceDirBySoftId(Integer... softId) {
		if(ArrayUtils.isEmpty(softId))
		{
			return 0;
		}
		return softServiceDao.delServiceDirBySoftId(softId);
	}


	@Override
	@Transactional
	@ActionModel(description="删除服务接口")
	public int delServiceDirById(Integer... dirId) {
		if(ArrayUtils.isEmpty(dirId))
		{
			return 0;
		}
		return softServiceDao.removeObject(dirId, "id");
	}


	@Override
	public int getServiceDirCount(int softId) {
		return softServiceDao.getCountByProperty("softId",softId);
	}


	@Override
	public boolean serviceCodeExist(String serviceCode) {
		Assert.hasText(serviceCode,"服务代号为空！");
		int count=softServiceDao.getCountByProperty("serviceCode",serviceCode);
		return count>0?true:false;
	}


	@Override
	public EsbServiceDir getDirById(int serviceId) {
		return softServiceDao.getObject(serviceId);
	}


	@Override
	@Transactional
	@ActionModel(description="编辑软件服务项")
	public int editServiceDir(EsbServiceDir serviceDir) {
		int count =softServiceDao.updateObject(serviceDir);
		return  count;
	}
}
