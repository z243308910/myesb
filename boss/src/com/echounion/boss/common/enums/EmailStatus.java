package com.echounion.boss.common.enums;

/**
 * 邮件枚举
 * @author 胡礼波
 * 2012-11-17 上午11:05:30
 */
public enum EmailStatus {

	SYS_UNKNOW_ERROR(SystemStatus.SYS_UNKNOW_ERROR.getCode(),SystemStatus.SYS_UNKNOW_ERROR.getName()),
	SUCCESS("E1000","邮件历史记录成功"),
	NULL_EMAIL("E1001","邮件为空"),
	NULL_RECIVER("E1002","没有收件人地址"),
	NOT_TPL("E1003","邮件模版不存在!"),
	ERROR_MQ("E1004","JMS服务器异常"),
	ERROR_SYS("E1005","系统异常"),
	ACTION_NULL("E1006","邮件操作指令为空!"),
	ACTION_ERROR("E1007","无效的操作指令"),
	DATA_TOO_LONG("E1008","请求数据过长"),
	CONTENT_NULL("E1009","邮件内容为空!"),
	DATA_ERROR_TPL("ETP1006","邮件模版参数错误!");
	
	
	
	private String code;
	private String name;
	
	private EmailStatus(String code,String name)
	{
		this.code=code;
		this.name=name;
	}
	
	/**
	 * 根据错误码获得对应的枚举状态
	 * @author 胡礼波
	 * 2012-11-20 上午11:43:36
	 * @param code
	 * @return
	 */
	public static EmailStatus getStatus(String code)
	{
		for (EmailStatus status:EmailStatus.values()) {
			if(status.getCode().equals(code))
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
