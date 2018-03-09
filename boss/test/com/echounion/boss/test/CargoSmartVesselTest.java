package com.echounion.boss.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.echounion.boss.cargosmart.schedule.CargosmartSchedule;

public class CargoSmartVesselTest {
	private ApplicationContext ctx;

	private CargosmartSchedule schedule;

	@Before
	public void init()
	{
		
		try {
			ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
			schedule=(CargosmartSchedule)ctx.getBean(CargosmartSchedule.class);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	@Test
	public void testSend()
	{
		String data=schedule.doTrack(null);
		System.out.println(data);
	}
}
