package com.echounion.boss.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.cargosmart.service.ICsTrackingService;
import com.echounion.boss.entity.dto.TrackingInfo;


public class CsTrackingTest {

	private ApplicationContext ctx;

	private ICsTrackingService adapter;

	@Before
	public void init()
	{
		
		try {
			ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
			adapter=(ICsTrackingService)ctx.getBean(ICsTrackingService.class);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	@Test
	public void testSend()
	{
		TrackingInfo info=adapter.getTrackingInfo(TrackingAdapter.STYPE_BILL,"2540732580");
		System.out.println(info);
	}
}
