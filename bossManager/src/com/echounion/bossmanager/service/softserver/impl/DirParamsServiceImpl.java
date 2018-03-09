package com.echounion.bossmanager.service.softserver.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.echounion.bossmanager.common.security.annotation.ActionModel;
import com.echounion.bossmanager.dao.softserver.IDirParamsDao;
import com.echounion.bossmanager.entity.EsbServiceDirParam;
import com.echounion.bossmanager.service.softserver.IDirParamsService;

@Service
@Transactional(propagation=Propagation.SUPPORTS)
@ActionModel(description="服务接口参数")
public class DirParamsServiceImpl implements IDirParamsService {

	@Autowired
	private IDirParamsDao dirParamsDao;
	
	@Override
	@Transactional
	@ActionModel(description="添加服务接口参数")
	public int addParams(EsbServiceDirParam... params) {
		Assert.notEmpty(params,"服务接口参数为空!");
		int count=0;
		for (EsbServiceDirParam param : params) {
			if(param==null)
			{
				continue;
			}
			count+=dirParamsDao.saveObject(param);
		}
		return count;
	}

	@Override
	@ActionModel(description="根据服务接口ID获取参数")
	public List<EsbServiceDirParam> getDirParamsByServiceId(Integer serviceDirId) {
		return dirParamsDao.getByProperty("serviceDirId", serviceDirId);
	}

	@Override
	@Transactional
	@ActionModel(description="编辑服务参数")
	public int editParams(EsbServiceDirParam[] paramsList) {
		Assert.notEmpty(paramsList,"服务接口参数为空!");
		int count=0;
		for (EsbServiceDirParam param : paramsList) {
			if(param==null)
			{
				continue;
			}
			count+=dirParamsDao.updateObject(param);
		}
		return count;
	}

	@Override
	public EsbServiceDirParam getDirParamById(int paramId) {
		return dirParamsDao.getObject(paramId);
	}

	@Override
	@Transactional
	@ActionModel(description="删除参数")
	public int delParams(Integer[] ids) {
		return dirParamsDao.removeObject(ids,"id");
	}
}
