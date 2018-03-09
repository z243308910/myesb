package com.echounion.bossmanager.common.security;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import com.echounion.bossmanager.common.constant.Constant;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class SecurityEncrypt {

	private static SecurityEncrypt securityEncrypt;
	
	private SecurityEncrypt(){this.setKey(Constant.INTERFACE_SS_KEY);}		//该密钥是数据接口对接时所需要的密钥和BOSS系统连接数据库密钥不同
	
	public synchronized static SecurityEncrypt getInstance()
	{
		if(securityEncrypt==null)
		{
			securityEncrypt=new SecurityEncrypt();
		}
		return securityEncrypt;
	}
	
	/**
	  * 使用DES加密与解密,可对byte[],String类型进行加密与解密 密文可使用String,byte[]存储.
	  * 方法: void getKey(String strKey)从strKey的字条生成一个Key
	  * String getEncString(String strMing)对strMing进行加密,返回String密文 String
	  * getDesString(String strMi)对strMin进行解密,返回String明文
	  *byte[] getEncCode(byte[] byteS)byte[]型的加密 byte[] getDesCode(byte[]
	  * byteD)byte[]型的解密
	  */
	private Key key;

	 /**
	  * 根据参数生成KEY
	  * @param strKey
	  */
	 public void setKey(String strKey) {
	  try {
	   SecretKeySpec key = new SecretKeySpec(getDataByte(strKey), "DES");
	   this.key =key;
	  } catch (Exception e) {
	   e.printStackTrace();
	  }
	 }
	 
	 /**
	  * 把数据转化为8的倍数的字节数返回
	  * @author 胡礼波
	  * 2012-10-24 下午04:32:34
	  * @param data
	  * @return
	  */
	 public byte[] getDataByte(String data)
	 {
		 Key key = null;
		 byte[] keyByte = data.getBytes();
		 byte[] byteTemp = new byte[8];// 创建一个空的八位数组,默认情况下为0
		  for (int i = 0; i < byteTemp.length && i < keyByte.length; i++) {// 将用户指定的规则转换成八位数组
		   byteTemp[i] = keyByte[i];
		  }
		  key = new SecretKeySpec(byteTemp, "DES");
		  return key.getEncoded();
	 }

	 /**
	  * 加密String明文输入,String密文输出
	  * @param strMing
	  * @return
	  */
	 public String getEncString(String strMing) {

	  byte[] byteMi = null;
	  byte[] byteMing = null;
	  String strMi = "";
	  BASE64Encoder base64en = new BASE64Encoder();
	  try {
	   byteMing = strMing.getBytes("UTF-8");
	   byteMi = this.getEncCode(byteMing);
	   strMi = base64en.encode(byteMi);
	  } catch (Exception e) {
	   e.printStackTrace();
	  } finally {
	   base64en = null;
	   byteMing = null;
	   byteMi = null;
	  }
	  return strMi;
	 }

	 
	 /**
	  * 解密 以String密文输入,String明文输出
	  * @param strMi
	  * @return
	  */
	 public String getDesString(String strMi) {
	  BASE64Decoder base64De = new BASE64Decoder();
	  byte[] byteMing = null;
	  byte[] byteMi = null;
	  String strMing = "";
	  try {
	   byteMi = base64De.decodeBuffer(strMi);
	   byteMing = this.getDesCode(byteMi);
	   strMing = new String(byteMing, "UTF-8");
	  } catch (Exception e) {
	   e.printStackTrace();
	  } finally {
	   base64De = null;
	   byteMing = null;
	   byteMi = null;
	  }
	  return strMing;
	 }

	 /**
	  * 加密以byte[]明文输入,byte[]密文输出
	  * @param byteS
	  * @return
	  */
	 private byte[] getEncCode(byte[] byteS) {
	  byte[] byteFina = null;
	  Cipher cipher;
	  try {
	   cipher = Cipher.getInstance("DES");
	   cipher.init(Cipher.ENCRYPT_MODE, key);
	   byteFina = cipher.doFinal(byteS);
	  } catch (Exception e) {
	   e.printStackTrace();
	  } finally {
	   cipher = null;
	  }
	  return byteFina;
	 }

	 /**
	  * 解密以byte[]密文输入,以byte[]明文输出
	  * @param byteD
	  * @return
	  */
	 private byte[] getDesCode(byte[] byteD) {
	  Cipher cipher;
	  byte[] byteFina = null;
	  try {
	   cipher = Cipher.getInstance("DES");
	   cipher.init(Cipher.DECRYPT_MODE, key);
	   byteFina = cipher.doFinal(byteD);
	  } catch (Exception e) {
	   e.printStackTrace();
	  } finally {
	   cipher = null;
	  }
	  return byteFina;
	 }
}
