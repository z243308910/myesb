package com.echounion.boss.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.echounion.boss.core.rtx.Rtx;
import com.echounion.boss.core.rtx.service.IRtxService;


public class RtxTest {

	private ApplicationContext ctx;

	private IRtxService rtxService;

	@Before
	public void init()
	{
		
		try {
			ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
			rtxService=(IRtxService)ctx.getBean(IRtxService.class);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	@Test
	public void testSend()
	{
		Rtx rtx=new Rtx();
		rtx.setContent("测试内容");
		rtx.setReceiver("胡礼波");
		rtx.setTitle("测试标题");
		
		rtxService.send(rtx);
		try
		{
			Thread.sleep(10000);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
