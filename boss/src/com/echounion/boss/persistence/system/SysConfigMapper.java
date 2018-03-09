package com.echounion.boss.persistence.system;

import java.util.List;

import com.echounion.boss.entity.SysConfig;

/**
 * 系统配置信息数据接口
 * @author 胡礼波
 * 2012-11-3 下午05:08:21
 */
public interface SysConfigMapper {

	public SysConfig getSysConfig(String configCode);

	public List<SysConfig> getSysConfigByType(int type);
}
