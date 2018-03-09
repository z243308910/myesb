package com.echounion.bossmanager.common.security;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.echounion.bossmanager.common.security.encoder.PwdEncoder;

public class SecurityEncryptTests {

	private ApplicationContext cxt=null;
	private PwdEncoder pwdEncoder;
	
	@Before
	public void before()
	{
		cxt=new ClassPathXmlApplicationContext("applicationContext.xml");
		pwdEncoder= cxt.getBean(PwdEncoder.class);
	}
	
	/**
	 * 加密
	 * @author 胡礼波
	 * 2012-10-30 下午04:22:14
	 */
	@Test
	public void testEncrypt()
	{
		System.out.println(SecurityEncrypt.getInstance().getEncString("root"));
	}
	
	/**
	 * 密码加密
	 * @author 胡礼波
	 * 2012-10-30 下午04:42:28
	 */
	@Test
	public void testEncoderPwd()
	{
		String pwd=pwdEncoder.encodePassword("123456");
		System.out.println(pwd);
	}
}
