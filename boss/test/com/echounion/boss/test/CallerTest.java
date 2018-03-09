package com.echounion.boss.test;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.echounion.boss.core.esb.service.IEsbService;
import com.echounion.boss.entity.dto.Caller;


public class CallerTest {

	private ApplicationContext ctx;

	private IEsbService esbService;

	@Before
	public void init()
	{
		
		try {
			ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
			esbService=(IEsbService)ctx.getBean(IEsbService.class);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	@Test
	public void testSend()
	{
		List<Caller> list=esbService.getCallers();
		for (Caller caller : list) {
			System.out.println(caller.getServerId());
		}
	}
}
