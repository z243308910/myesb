package com.echounion.bossmanager.common.enums;
/**
 * 系统标识枚举
 * @author 胡礼波
 * 2012-11-21 下午03:59:29
 */
public enum SystemStatus {

	NO_LOGIN("S001","未登录"),
	DATA_ERROR("SE002","参数错误"),
	
	SUCCESS("SYS001","请求成功"),
	AUTHORKEY_NULL("SYS002","授权码为空或不正确！"),
	Authorkey_ERROR("SYS003","授权码错误!"),
	UNAUTHOR_IP_INVOKE("SYS004","非法服务器IP调用"),
	SYS_UNKNOW_ERROR("SYS005","系统未知错误"),
	URL_NOT_EXIST("ESB010","请求地址不存在!");
	
	private String code;
	private String name;
	
	private SystemStatus(String code,String name)
	{
		this.code=code;
		this.name=name;
	}

	/**
	 * 根据系统错误代号返回系统状态枚举
	 * @author 胡礼波
	 * 2012-11-21 下午04:01:53
	 * @param code
	 * @return
	 */
	public static SystemStatus getSysStatus(String code)
	{
		for (SystemStatus status:SystemStatus.values()) {
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
