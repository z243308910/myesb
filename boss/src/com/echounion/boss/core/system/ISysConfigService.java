package com.echounion.boss.core.system;

import java.util.List;
import com.echounion.boss.core.email.EmailConfig;
import com.echounion.boss.entity.SysConfig;

/**
 * 系统配置业务接口
 * @author 胡礼波
 * 2012-11-3 下午05:04:40
 */
public interface ISysConfigService {

	/**
	 * 根据配置代码查找对应的配置信息
	 * @author 胡礼波
	 * 2012-11-3 下午05:06:06
	 * @param configCode
	 * @return
	 */
	public SysConfig getSysConfig(String configCode);
	
	/**
	 * 获取邮件的所有配置
	 * @author 胡礼波
	 * 2013-8-5 下午3:33:08
	 * @return
	 */
	public List<EmailConfig> getEmailConfig();
	
	/**
	 * 根据配置类型查找对应的配置信息集合
	 * @author 胡礼波
	 * 2012-11-3 下午05:06:54
	 * @param type
	 * @return
	 */
	public List<SysConfig> getSysConfigByType(int type);
}
