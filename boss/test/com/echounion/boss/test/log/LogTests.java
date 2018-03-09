package com.echounion.boss.test.log;


import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.echounion.boss.entity.EsbApiLog;
import com.echounion.boss.logs.service.ILogService;

public class LogTests {

	private ApplicationContext ctx;
	
	private ILogService<EsbApiLog> esbLogService;
	@SuppressWarnings("unchecked")
	@Before
	public void init()
	{
		
		try {
			ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
			esbLogService=(ILogService<EsbApiLog>)ctx.getBean("EsbApiLogServiceImpl");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	@Test
	public void testAddLog()
	{
		EsbApiLog log=new EsbApiLog();
		log.setInvokeIp("127.0.0.1");
		log.setResCode("200");
		log.setResMsg("成功");
		try
		{
			esbLogService.addLog(log);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
