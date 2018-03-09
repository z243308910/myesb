package com.echounion.bossmanager.common.enums;


/**
 * 短信枚举
 * @author 胡礼波
 * 2012-11-17 下午05:36:32
 */
public enum ShortMsgStatus {

	NO_LOGIN(SystemStatus.NO_LOGIN.getCode(),0,SystemStatus.NO_LOGIN.getName()),
	SYS_UNKNOW_ERROR(SystemStatus.SYS_UNKNOW_ERROR.getCode(),-1,SystemStatus.SYS_UNKNOW_ERROR.getName()),
	PASSWORD_INVALIDATA("SM002",-2,"帐号/密码不正确"),
	NO_MONEY("SM004",-4,"余额不足"),
	DATA_INVALIDATA("SM005",-5,"数据格式错误"),
	PARAM_INVALIDATA("SM006",-6,"参数错误(内容为空)"),
	RIGHT_INVALIDATA("SM007",-7,"权限受限"),
	FLOW_ERROR("SM008",-8,"流量控制错误"),
	EXTRACODE_ERROR("SM009",-9,"扩展码权限错误"),
	DATA_TOOLONG("SM010",-10,"内容长度过长"),
	DATABASE_ERROR("SM011",-11,"内部数据库异常"),
	SN_INVALIDATE("SM012",-12,"序列号状态错误"),
	NUL_INCRE_CONTENT("SM013",-13,"没有提交增值内容"),
	WRITE_ERROR("SM014",-14,"服务器写文件失败"),
	CONTENTBASE64_ERROR("SM015",-15,"文件内容base64编码错误"),
	NO_RIGHT("SM017",-17,"没有权限"),
	THREAD_ERROR("SM018",-18,"需等待上次的提交返回"),
	MOREURL_ERROR("SM019",-19,"禁止同时使用多个接口地址"),
	MORE_SUBMIT("SM020",-20,"提交过多"),
	IP_INVALIDATE("SM021",-21,"Ip鉴权失败 "),
	SUCCESS("SM100",100,"短信历史记录成功"),
	ERROR_MQ("SM101",101,"JMS服务器异常"),
	ERROR_SYS("SM102",102,"系统异常");
	
	private String code;
	private String name;
	private int sysValue;		//网关系统参数
	
	/**
	 * 根据值参数获得对象的状态枚举
	 * @author 胡礼波
	 * 2012-11-19 下午02:21:47
	 * @param sysValue
	 * @return
	 */
	public static ShortMsgStatus getStatusBySysValue(int sysValue)
	{
		for (ShortMsgStatus status:ShortMsgStatus.values()) {
			if(sysValue==status.getSysValue())
			{
				return status;
			}
		}
		return null;
	}
	
	/**
	 * 根据错误代码获得对应的短信状态枚举
	 * @author 胡礼波
	 * 2012-11-20 下午02:04:13
	 * @param code
	 * @return
	 */
	public static ShortMsgStatus getStatus(String code)
	{
		for (ShortMsgStatus status:ShortMsgStatus.values()) {
			if(code==status.getCode())
			{
				return status;
			}
		}
		return null;
	}
	
	private ShortMsgStatus(String code,int sysValue,String name)
	{
		this.code=code;
		this.name=name;
		this.sysValue=sysValue;
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

	public int getSysValue() {
		return sysValue;
	}

	public void setSysValue(int sysValue) {
		this.sysValue = sysValue;
	}
}
