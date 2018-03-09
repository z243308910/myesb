package com.echounion.bossmanager.entity.dto;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.echounion.bossmanager.common.json.JsonUtil;
import com.echounion.bossmanager.common.security.SecurityEncrypt;

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
	private String type="json";	//数据类型，默认为json
	
	public Record(){}
	
	public Record(int status,String errorCode,String data,boolean isEncrypt)
	{
		this.setEncrypt(isEncrypt);
		this.setStatus(status);
		this.setErrorCode(errorCode);
		this.setMessage(data);
	}
	
	/**
	 * 设置返回成功状态的消息
	 * @author 胡礼波
	 * 2012-11-1 下午05:38:39
	 * @param data
	 * @param isEncrypt
	 * @return
	 */
	public static Record setSuccessRecord(String data,boolean isEncrypt)
	{
		Record record=new Record(SUCCESS,null,data,isEncrypt);
		return record;
	}
	
	/**
	 * 设置返回失败状态的消息
	 * @author 胡礼波
	 * 2012-11-1 下午05:39:52
	 * @param errorCode
	 * @param data
	 * @param isEncrypt
	 * @return
	 */
	public static Record setFailRecord(String errorCode,String data,boolean isEncrypt)
	{
		errorCode=errorCode==null?"":errorCode;
		data=data==null?"":data;
		Record record=new Record(FAIL,errorCode,data,isEncrypt);
		return record;
	}
	
	/**
	 * 获得Record加密后的字符串
	 * @author 胡礼波
	 * 2012-11-1 下午05:49:00
	 * @param record
	 * @return
	 */
	public static String getEncryptRecord(Record record)
	{
		String str=JsonUtil.toJsonStringFilterPropter(record).toJSONString();
		str=SecurityEncrypt.getInstance().getEncString(str);
		return str;
	}
	
	/**
	 * 将加密后的字符串解密并转化为Record对象返回
	 * @author 胡礼波
	 * 2012-11-1 下午06:07:12
	 * @param str
	 * @return
	 */
	public static Record getDecipRecord(String str)
	{
		str=SecurityEncrypt.getInstance().getDesString(str);	//解密
		Record record=(Record) JSON.parse(str);
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
		if(isEncrypt)			//解密
		{
			message=SecurityEncrypt.getInstance().getDesString(message);
		}
		return message;
	}

	public void setMessage(String message) {
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
