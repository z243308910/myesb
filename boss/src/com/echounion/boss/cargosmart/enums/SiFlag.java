package com.echounion.boss.cargosmart.enums;
import com.echounion.boss.common.enums.SystemStatus;

/**
 * SI信息枚举
 * @author 胡礼波
 * 2013-10-14 下午6:03:20
 */
public enum SiFlag {

	SYS_UNKNOW_ERROR(SystemStatus.SYS_UNKNOW_ERROR.getCode(),SystemStatus.SYS_UNKNOW_ERROR.getName()),
	SUCCESS("SI1000","请求成功"),
	NULL_FILENAME("SI1001","文件名为空"),
	NULL_CONTENT("SI1002","SI内容为空");
	
	private String code;
	private String name;
	
	private SiFlag(String code,String name)
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
	public static SiFlag getStatus(String code)
	{
		for (SiFlag status:SiFlag.values()) {
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
