package com.echounion.boss.common.enums;

/**
 * 协议类型
 * @author 胡礼波
 * 2013-7-9 下午6:36:14
 */
public enum ProtocolType {

	HTTP(1),
	SOAP(2),
	JMS(3),
	FTP(4),
	SOCKET(5);
	
	private int id;
	
	public static ProtocolType getProcolType(int typeId)
	{
		for (ProtocolType type:ProtocolType.values()) {
			if(type.getId()==typeId)
			{
				return type;
			}
		}
		return null;
	}
	
	private ProtocolType(int id)
	{
		this.id=id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
