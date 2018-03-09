package com.echounion.boss.core.system;

/**
 * 数据序列业务接口
 * @author 胡礼波
 * 2012-11-2 下午03:10:58
 */
public interface ISysSequenceService {

	/**
	 * 根据序号代码查询返回当前的ID值
	 * @author 胡礼波
	 * 2012-11-2 下午03:07:05
	 * @param code
	 * @return
	 */
	public int getCurrentValue(String code);
	
	/**
	 * 根据表名和字段名称返回当前的ID值
	 * @author 胡礼波
	 * 2012-11-2 下午03:07:58
	 * @param tableName
	 * @param fieldName
	 * @return
	 */
	public int getCUrrentValue(String tableName,String fieldName);
}
