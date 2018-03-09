package com.echounion.boss.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.echounion.boss.common.enums.ShortMsgStatus;
import com.echounion.boss.core.shortmsg.ShortMsg;
import com.echounion.boss.core.shortmsg.service.IShortMsgService;


public class ShortMsgTest {

	private ApplicationContext ctx;

	private IShortMsgService shortMsgService;

	@Before
	public void init()
	{
		
		try {
			ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
			shortMsgService=(IShortMsgService)ctx.getBean(IShortMsgService.class);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	@Test
	public void testSend()
	{
		ShortMsg shortMsg=new ShortMsg();
		shortMsg.setContent("短信内容");
		shortMsg.setMobile("15889545721,13632721144");
		
		ShortMsgStatus s= shortMsgService.send(shortMsg);
		System.out.println(s.getCode());
		try
		{
			Thread.sleep(10000);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
