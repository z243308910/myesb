package com.echounion.boss.entity.dto;

import java.io.Serializable;

import com.echounion.boss.common.enums.RecordType;
import com.echounion.boss.core.security.SecurityEncrypt;


/**
 * 系统间消息返回类DTO
 * @author 胡礼波
 * 2012-11-1 下午05:26:17
 */
public class Record implements Serializable{

	/**
	 * @author 胡礼波
	 * 2012-11-1 下午05:31:51
	 */
	private static final long serialVersionUID = 4338686348424782945L;
	
	public static final int SUCCESS=1;		//成功状态码
	public static final int FAIL=-1;			//失败状态码
	
	private int status;		//消息状态是否成功 成功1，失败-1
	private String errorCode;	//错误代号
	private String message;	//返回数据
	private boolean isEncrypt;//是否加密
	private RecordType type=RecordType.JSON;	//数据类型，默认为json
	
	public Record(){}
	
	public Record(int status,String errorCode,String data,boolean isEncrypt,RecordType type)
	{
		this.setStatus(status);
		this.setEncrypt(isEncrypt);
		this.setErrorCode(errorCode);
		this.setMessage(data);
		this.setType(type);
	}
	
	/**
	 * 设置返回成功状态的消息
	 * @author 胡礼波
	 * 2012-11-1 下午05:38:39
	 * @param data
	 * @param isEncrypt
	 * @return
	 */
	public static Record setSuccessRecord(String data,boolean isEncrypt,RecordType type)
	{
		Record record=new Record(SUCCESS,null,data,isEncrypt,type);
		return record;
	}
	
	/**
	 * 设置返回失败状态的消息 默认为不加密
	 * @author 胡礼波
	 * 2012-11-1 下午05:39:52
	 * @param errorCode
	 * @param data
	 * @param isEncrypt
	 * @return
	 */
	public static Record setFailRecord(String errorCode,String data,RecordType type)
	{
		errorCode=errorCode==null?"":errorCode;
		data=data==null?"":data;
		Record record=new Record(FAIL,errorCode,data,false,type);
		return record;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public boolean isEncrypt() {
		return isEncrypt;
	}
	public void setEncrypt(boolean isEncrypt) {
		this.isEncrypt = isEncrypt;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		if(null==message)
		{
			message="";
		}
		if(isEncrypt)		//加密
		{
			message=SecurityEncrypt.getInstance().getEncString(message);
		}
		this.message = message;
	}

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public RecordType getType() {
		return type;
	}

	public void setType(RecordType type) {
		this.type = type;
	}
}
