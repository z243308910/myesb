package com.echounion.bossmanager.common.enums;
/**
 * 软件服务状态枚举
 * @author 胡礼波
 * 2012-11-21 下午04:08:30
 */
public enum SoftServerStatus {

	SOFT_DISABLED("SD500","软件已经停止提供服务"),
	SOFT_DELETED("SD501","该软件已经删除");
	
	private String code;
	private String name;
	
	private SoftServerStatus(String code,String name)
	{
		this.code=code;
		this.name=name;
	}
	
	public static SoftServerStatus getSoftStatus(String code)
	{
		for (SoftServerStatus status:SoftServerStatus.values()) {
			if(code.equals(status.getCode()))
			{
				return status;
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
