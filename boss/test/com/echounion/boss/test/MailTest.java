package com.echounion.boss.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.echounion.boss.common.enums.EmailStatus;
import com.echounion.boss.core.email.EmailMessage;
import com.echounion.boss.core.email.service.IEmailService;


public class MailTest {

	private ApplicationContext ctx;

	private IEmailService emailService;

	@Before
	public void init()
	{
		
		try {
			ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
			emailService=(IEmailService)ctx.getBean(IEmailService.class);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	@Test
	public void testSend()
	{
		EmailMessage message=new EmailMessage();
		message.setSubject("测试主题");
		message.setContent("测试内容");
		message.setReceiver("243308910@qq.com,z243308910@163.com");
		EmailStatus status= emailService.send(message);
		System.out.println(status.getCode());
		try
		{
			Thread.sleep(1000);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
