package com.echounion.bossmanager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.echounion.bossmanager.common.security.annotation.ActionModel;
import com.echounion.bossmanager.common.security.encoder.PwdEncoder;
import com.echounion.bossmanager.common.util.ContextUtil;
import com.echounion.bossmanager.dao.IAdminDao;
import com.echounion.bossmanager.entity.SysUser;
import com.echounion.bossmanager.service.IAdminService;
@Service
@ActionModel(description="管理员管理")
@Transactional(propagation=Propagation.SUPPORTS)
public class AdminServiceImpl implements IAdminService {

	@Autowired
	private IAdminDao adminDao;
	@Autowired
	private PwdEncoder pwdEncoder;
	
	
	@ActionModel(description="添加管理员")
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean addAdmin(SysUser adminUser) {
		Assert.notNull(adminUser,"添加管理员失败，管理员对象为null");
		Assert.notNull(adminUser.getUserName(),"添加管理员失败,用户名为空");
		Assert.notNull(adminUser.getPassword(),"添加管理员失败,密码为空");
		Assert.notNull(adminUser.getName(),"添加管理员失败,姓名为空");
		adminUser.setPassword(pwdEncoder.encodePassword(adminUser.getPassword()));//密码加密
		return adminDao.addAdmin(adminUser)>0?true:false;
	}

	public boolean emailExist(String email) {
		int count=adminDao.getCountByProperty("email",email);
		return count>0?true:false;
	}

	public SysUser login(String name, String password) {
		SysUser admin=adminDao.getByUsername(name);
		if(admin!=null)
		{
			if(pwdEncoder.isPasswordValid(admin.getPassword(),password))	//匹配加密后的密码
			{
				return admin;
			}
		}
		return null;
	}

	public boolean usernameExist(String username) {
		Assert.hasText(username,"用户名为空！");
		int count=adminDao.getCountByProperty("userName",username);
		return count>0?true:false;
	}

	public List<SysUser> getAllAdmin() {
		return adminDao.getAll();
	}

	@Transactional
	@ActionModel(description="删除管理员")
	public int delAdmins(Integer[] ids) {
		SysUser admin= ContextUtil.getContextLoginUser();
		for (int i=0 ;i<ids.length;i++) {
			if(admin.getId()==ids[i])
			{
				ids[i]= -1;
			}
		}
	
		return adminDao.removeObject(ids,"id");
	}

	public SysUser getAdminByUserName(String username) {
		Assert.notNull(username);
		return adminDao.getByUsername(username);
	}

	@Transactional
	@ActionModel(description="修改管理员信息")
	public int editAdmin(SysUser adminUser) {
		Assert.notNull(adminUser);
		return adminDao.updateObject(adminUser);
	}
}
