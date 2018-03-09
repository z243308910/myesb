package com.echounion.bossmanager.dao.impl;

import org.springframework.stereotype.Repository;

import com.echounion.bossmanager.dao.IAdminDao;
import com.echounion.bossmanager.entity.SysUser;


@Repository
public class AdminDao extends BaseDao<SysUser> implements IAdminDao {

	public int addAdmin(SysUser admin) {
		return super.saveObject(admin);
	}

	public SysUser getByUsername(String userName) {
		return super.getUniqueByProperty("userName",userName);
	}
}
