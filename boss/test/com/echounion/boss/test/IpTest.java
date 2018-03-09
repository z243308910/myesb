package com.echounion.boss.test;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.echounion.boss.entity.dto.Provider;
import com.echounion.boss.persistence.esb.EsbServerMapper;
import com.echounion.boss.persistence.esb.EsbServiceDirMapper;


public class IpTest {

	private ApplicationContext ctx;

	private EsbServerMapper mapper;

	@Before
	public void init()
	{
		
		try {
			ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
			mapper=(EsbServerMapper)ctx.getBean(EsbServerMapper.class);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	@Test
	public void testSend()
	{
		List<String> list=null;
		try {
			list = mapper.getIp();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (String string : list) {
			System.out.println(string);
		}
	}
}
