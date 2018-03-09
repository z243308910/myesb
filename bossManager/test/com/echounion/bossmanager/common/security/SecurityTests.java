package com.echounion.bossmanager.common.security;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;


public class SecurityTests {

	@Test
	public void testEnrity()throws Exception
	{
		
		String data="103-6F2a8o6F1-33";
		data=SecurityEncrypt.getInstance().getEncString(data);
		System.out.println("对称加密："+data);
		data=Hex.encodeHexString(data.getBytes());
		System.out.println("加密后:"+data);
		
		//72537a304b554746317332472f63764139414c4c46494a634b48345a35677972
		
		
		data="7374785676614d665174794877716e78596c38754446677744456f31412f6a69";
		data=new String(Hex.decodeHex(data.toCharArray()));
		System.out.println("十六进制解密："+data);
		data=SecurityEncrypt.getInstance().getDesString(data);
		System.out.println("解密后："+data);
	}
}
