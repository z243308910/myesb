package com.echounion.bossmanager.service.impl;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.echounion.bossmanager.common.constant.Constant;
import com.echounion.bossmanager.common.security.annotation.ActionModel;
import com.echounion.bossmanager.common.util.ContextUtil;
import com.echounion.bossmanager.core.cache.memcached.MemCacheUtil;
import com.echounion.bossmanager.core.shortmsg.api.ShortMsgSession;
import com.echounion.bossmanager.dao.ISysConfigDao;
import com.echounion.bossmanager.entity.SysUser;
import com.echounion.bossmanager.entity.SysConfig;
import com.echounion.bossmanager.entity.dto.DBTable;
import com.echounion.bossmanager.entity.dto.EmailConfig;
import com.echounion.bossmanager.entity.dto.ShortMsgConfig;
import com.echounion.bossmanager.service.ISysConfigService;

@Service
@ActionModel(description="系统配置")
public class SysConfigServiceImpl implements ISysConfigService {

	private Logger logger=Logger.getLogger(SysConfigServiceImpl.class);
	@Autowired
	private ISysConfigDao sysConfigDao;
	
	@Transactional
	@ActionModel(description="添加系统配置")
	public int addSysConfig(SysConfig config) {
		logger.info("添加系统配置!");
		Assert.notNull(config.getConfigCode());
		if(StringUtils.isEmpty(config.getConfigName()))
		{
			config.setConfigName(config.getConfigCode());	//配置名称为空时默认为配置代码
		}
		SysUser admin=ContextUtil.getContextLoginUser();
		Assert.notNull(admin,"登录用户为空!");
		config.setCreator(admin.getUserName());
		config.setCreateDate(new Date());
		config.setConfigCode(assibleConfigCode(config.getConfigCode(),config.getType()));
		int result=sysConfigDao.saveObject(config);
		if(result>0)			//更新缓存中的邮件或者短信配置
		{
//			flushMCaChe(config);	//JMS MQ接口	协议   0001|113456|12456415|1212
									//			   命令发送方|命令|命令的消息|密钥
		}
		return result;
	}
	
	/**
	 * 更新缓存
	 * @author 胡礼波
	 * 2012-11-21 上午09:59:26
	 * @param config
	 */
	private void flushMCaChe(SysConfig config)
	{
		if(config.getType()==Constant.SYS_EMAIL)	//邮件配置
		{
			EmailConfig emailConfig=new EmailConfig(config.getUrl(),config.getUserName(),config.getPassword(),config.getIp(),config.getPort());
			boolean flag=MemCacheUtil.add(config.getConfigCode(),emailConfig,30*60,true);
			if(flag)
			{
				logger.info("邮件配置缓存更新成功!");
			}else
			{
				logger.info("邮件配置缓存同步失败!");
			}
		}else if(config.getType() == Constant.SYS_SHORTMSG)	//短信配置
		{
			ShortMsgConfig shortMsgConfig=new ShortMsgConfig(config.getUserName(),config.getPassword(),config.getUrl());
			boolean flag=MemCacheUtil.add(config.getConfigCode(),shortMsgConfig,30*60,true);
			if(flag)
			{
				logger.info("短信配置缓存更新成功!");
			}else
			{
				logger.info("短信配置缓存同步失败!");
			}
		}else if(config.getType() == Constant.SYS_FTP)	//ftp配置
		{
		
		}
	}
	
	/**
	 * 根据类型重新组装系统配置的代号
	 * @author 胡礼波
	 * 2012-11-6 上午11:50:16
	 * @param code
	 * @param type
	 * @return
	 */
	private String assibleConfigCode(String code,int type)
	{
		switch (type) {
		case Constant.SYS_TRACKING:
			code="Track_"+code;
		default:
			break;
		}
		return code;
	}

	@Transactional
	@ActionModel(description="删除系统配置")
	public int delSysConfig(Integer[] sysConfigId) {
		logger.info("删除配置信息!");
		if(ArrayUtils.isEmpty(sysConfigId))
		{
			return 0;
		}
		return sysConfigDao.removeObject(sysConfigId,"id");
	}

	@Transactional
	@ActionModel(description="修改系统配置")
	public int editSysConfig(SysConfig config) {
		logger.info("修改配置信息!");
		Assert.notNull(config.getConfigCode());
		if(StringUtils.isEmpty(config.getConfigName()))
		{
			config.setConfigName(config.getConfigCode());//配置名称为空时默认为配置代码
		}
		int result=sysConfigDao.updateObject(config);
		if(result>0)
		{
//			flushMCaChe(config);
		}
		return result;
	}

	public List<SysConfig> getSysConfig() {
		return sysConfigDao.getAll();
	}

	@SuppressWarnings("unchecked")
	public List<SysConfig> getSysConfigByType(int type) {
		List<SysConfig> list=null;//sysConfigDao.getByProperty("type",type);
		list=sysConfigDao.createCriteria(Restrictions.eq("type",type)).addOrder(Order.asc("channel")).list();
		if(type==Constant.SYS_SHORTMSG && list!=null)		//短信
		{
			for (SysConfig sysConfig : list) {
				try {
					String balance=new ShortMsgSession(sysConfig.getUserName(),sysConfig.getPassword(),sysConfig.getUrl()).getBalance();
					sysConfig.setBalance(balance);
				} catch (Exception e) {
					logger.error("获取短信余额失败"+e);
				}
			}
		}
		return list;
	}

	public SysConfig getSysConfigById(int sysConfigId) {
		return sysConfigDao.getObject(sysConfigId);
	}

	public int getCount(int type) {
		return sysConfigDao.getCountByProperty("type",type);
	}

	public List<DBTable> getDSTables() {
		return sysConfigDao.getDSTables();
	}

	@Transactional
	public boolean initTableData(String tableName) {
		boolean flag=false;
		try
		{
			sysConfigDao.delTableData(tableName);
			flag=true;
		}catch (Exception e) {
			flag=false;
		}
		return flag;
	}

	public SysConfig getSysConfig(String configCode, int type) {
		return sysConfigDao.getSysConfig(configCode, type);
	}

	public SysConfig getSysConfig(String configCode) {
		return sysConfigDao.getSysConfig(configCode);
	}

}
