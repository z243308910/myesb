package com.echounion.boss.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.echounion.boss.core.system.ISysSequenceService;


public class SysSequenceTests {

	private ApplicationContext ctx;
	
	private ISysSequenceService sequenceService;
	@Before
	public void init()
	{
		ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
		sequenceService=ctx.getBean(ISysSequenceService.class);
	}
	
	@Test
	public void testSearch()
	{
		int s=sequenceService.getCurrentValue("23");
		System.out.println(s);
	}

}
