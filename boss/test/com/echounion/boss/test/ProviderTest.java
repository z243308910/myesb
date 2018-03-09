package com.echounion.boss.test;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.echounion.boss.entity.dto.Provider;
import com.echounion.boss.persistence.esb.EsbServiceDirMapper;


public class ProviderTest {

	private ApplicationContext ctx;

	private EsbServiceDirMapper mapper;

	@Before
	public void init()
	{
		
		try {
			ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
			mapper=(EsbServiceDirMapper)ctx.getBean(EsbServiceDirMapper.class);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	@Test
	public void testSend()
	{
		List<Provider> list=mapper.getServiceDirInfo();
		for (Provider provider : list) {
			System.out.println(provider.getServerId());
		}
	}
}
