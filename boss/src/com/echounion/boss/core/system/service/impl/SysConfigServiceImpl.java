package com.echounion.boss.core.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import com.echounion.boss.common.constant.Constant;
import com.echounion.boss.core.email.EmailConfig;
import com.echounion.boss.core.system.ISysConfigService;
import com.echounion.boss.entity.SysConfig;
import com.echounion.boss.persistence.system.SysConfigMapper;

@Service
@Transactional(propagation=Propagation.SUPPORTS)
public class SysConfigServiceImpl implements ISysConfigService {

	@Autowired
	private SysConfigMapper configMapper;
	
	private Logger logger=Logger.getLogger(SysConfigServiceImpl.class);
	
	public SysConfig getSysConfig(String configCode) {
		Assert.notNull(configCode);
		return configMapper.getSysConfig(configCode);
	}

	public List<SysConfig> getSysConfigByType(int type) {
		return configMapper.getSysConfigByType(type);
	}

	public List<EmailConfig> getEmailConfig()
	{
		EmailConfig emailConfig=null;
		List<EmailConfig> data=null;
		try
		{
				List<SysConfig> list=getSysConfigByType(Constant.SYS_EMAIL);			//获取邮件配置 系统参数
				data=new ArrayList<>();
				for (SysConfig config : list) {
					emailConfig=new EmailConfig();
					emailConfig.setHost(config.getUrl());			//设置邮件SMTP主机
					emailConfig.setPassword(config.getPassword());	//邮件密码
					emailConfig.setUsername(config.getUserName());	//邮件用户名
					emailConfig.setPort(config.getPort());			//端口
					emailConfig.setFrom(config.getIp());			//发送的邮件地址
					emailConfig.setChannel(config.getChannel());
					data.add(emailConfig);
				}
		}
		catch (Exception e) {
			logger.error("系统异常，请检查邮件配置参数或缓存服务器是否连接"+e);
		}
		return data;
	}
}
