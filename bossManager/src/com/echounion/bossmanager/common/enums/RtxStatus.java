package com.echounion.bossmanager.common.enums;
/**
 * 系统标识枚举
 * @author 胡礼波
 * 2012-11-21 下午03:59:29
 */
public enum RtxStatus {

	ERROR_MQ("RTX1004","JMS服务器异常"),
	
	SUCCESS("RTX001","发送成功"),
	RTX_NULL("RTX002","RTX参数为空!"),
	RECEIVER_NULL("RTX003","接收方为空!"),
	CONTENT_NULL("RTX004","内容为空"),
	SYS_UNKNOW_ERROR("RTX005","系统未知错误"),
	ACTION_NULL("RTX006","邮件操作指令为空!"),
	ACTION_ERROR("RTX007","无效的操作指令");	
	
	private String code;
	private String name;
	
	private RtxStatus(String code,String name)
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
	public static RtxStatus getSysStatus(String code)
	{
		for (RtxStatus status:RtxStatus.values()) {
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
