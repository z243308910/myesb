package com.echounion.boss.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.echounion.boss.cargosmart.service.ISiService;
import com.echounion.boss.entity.dto.Record;


public class SiTest {

	private ApplicationContext ctx;

	private ISiService siService;

	@Before
	public void init()
	{
		
		try {
			ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
			siService=(ISiService)ctx.getBean(ISiService.class);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	@Test
	public void testSend()
	{
		Record record=siService.postSiData("aa.xml","<?xml version='1.0' encoding='utf-8'?><ShippingInstructions></ShippingInstructions>");
		System.out.println(">>>>>>>>>>>>>>>>> "+record.getMessage());
	}
	
	
}
