package com.echounion.boss.persistence.system;

import java.util.Map;

import com.echounion.boss.entity.SysSequence;

/**
 * 系统序列值业务接口
 * @author 胡礼波
 * 2012-11-2 下午03:04:30
 */
public interface SysSequenceMapper {

	/**
	 * 根据表名和字段名称等等返回当前序列对象
	 * @author 胡礼波
	 * 2012-11-2 下午03:07:58
	 * @param map 存放查询的条件
	 * @return
	 */
	public SysSequence getSysSequence(Map<String,Object> map);
}
