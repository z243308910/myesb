package com.echounion.boss.common.enums;
/**
 * 软件服务状态枚举
 * @author 胡礼波
 * 2012-11-21 下午04:08:30
 */
public enum SoftServerStatus {

	SOFT_DISABLED("SD500","软件已经停止提供服务"),
	SOFT_DELETED("SD501","该软件已经删除"),
	SOFT_DEPLOY_DISABLED("SDD500","软件部署环境已经停止"),
	SOFT_DEPLOY_DELETED("SDD501","该软件未部署"),
	SOFT_DEPLOY_UNKONE_IP("SDDI502","非法服务器IP调用"),
	SOFT_DEPLOY_ERROR_SECURITYKEY("SDES503","安全码错误"),
	SOFT_AGREEMENT_PAUSE("SAD501","暂停服务"),
	SOFT_AGREEMENT_EXPIRE("SAD502","该协议已经失效"),
	SOFT_AGREEMENT_DELETED("SAD503","该协议已经撤销");
	
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
