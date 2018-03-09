package com.echounion.boss.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统数据序列类
 * 
 * @author 胡礼波 2012-11-2 下午02:59:04
 */
public class SysSequence implements Serializable {

	/**
	 * @author 胡礼波 2012-11-2 下午02:59:20
	 */
	private static final long serialVersionUID = 7979067120706086088L;

	String seqCode; // 序号代码

	String seqDesc; // 序号描述

	String tableName; // 表名

	String fieldName; // 作用的字段名称

	int minValue; // 最小值

	int maxValue; // 最大值

	int nextValue; // 下一个值

	Date createTime; // 创建时间

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	public int getMinValue() {
		return minValue;
	}

	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}

	public int getNextValue() {
		return nextValue;
	}

	public void setNextValue(int nextValue) {
		this.nextValue = nextValue;
	}

	public String getSeqCode() {
		return seqCode;
	}

	public void setSeqCode(String seqCode) {
		this.seqCode = seqCode;
	}

	public String getSeqDesc() {
		return seqDesc;
	}

	public void setSeqDesc(String seqDesc) {
		this.seqDesc = seqDesc;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
