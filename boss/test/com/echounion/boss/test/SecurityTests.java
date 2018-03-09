package com.echounion.boss.test;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import com.echounion.boss.core.security.SecurityEncrypt;


public class SecurityTests {

	@Test
	public void testEnrity()throws Exception
	{
		String s=SecurityEncrypt.getInstance().getEncString("root");
		System.out.println(s);
		
		String data="54-12dbh5s96-25";
		data=SecurityEncrypt.getInstance().getEncString(data);
		System.out.println("对称加密："+data);
		data=Hex.encodeHexString(data.getBytes());
		System.out.println("加密后:"+data);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		data=new String(Hex.decodeHex(data.toCharArray()));
		System.out.println("十六进制解密："+data);
		data=SecurityEncrypt.getInstance().getDesString(data);
		System.out.println("解密后："+data);
	}
}
