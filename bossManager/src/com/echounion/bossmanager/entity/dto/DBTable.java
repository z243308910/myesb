package com.echounion.bossmanager.entity.dto;

import java.io.Serializable;
/**
 * 数据库表实体类DTO
 * @author 胡礼波
 * 2012-11-15 上午11:52:50
 */
public class DBTable implements Serializable {

	/**
	 * @author 胡礼波
	 * 2012-11-15 上午11:53:41
	 */
	private static final long serialVersionUID = -2778775862514441826L;
	
	private String name;		//表名
	private int count;			//当前表中的记录数
	private String remark;		//表的备注
	
	public DBTable(){}
	
	public DBTable(String name,int count,String remark)
	{
		this.name=name;
		this.count=count;
		this.remark=remark;
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
