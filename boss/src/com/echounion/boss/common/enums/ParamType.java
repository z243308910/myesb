package com.echounion.boss.common.enums;

/**
 * 参数类型枚举
 * @author 胡礼波
 * 2013-7-9 下午4:10:32
 */
public enum ParamType {

	INTEGER(1,"整形"),
	FLOAT(2,"浮点型"),
	BOOLEAN(3,"布尔型"),
	STRING(4,"字符串"),
	DATETIME(5,"时间型");
	
	private int id;
	private String name;

	private ParamType(int id,String name)
	{
		this.id=id;
		this.name=name;
	}
	
	/**
	 * 根据系统错误代号返回系统状态枚举
	 * @author 胡礼波
	 * 2012-11-21 下午04:01:53
	 * @param code
	 * @return
	 */
	public static ParamType getParamType(int id)
	{
		for (ParamType status:ParamType.values()) {
			if(id==status.getId())
			{
				return status;
			}
		}
		return null;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
