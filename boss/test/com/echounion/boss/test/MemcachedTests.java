package com.echounion.boss.test;


import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.echounion.boss.common.constant.Constant;
import com.echounion.boss.entity.SysConfig;

public class MemcachedTests {

	MemCachedClient mc=null;
	@Before
	public void init()
	{
		SockIOPool pool=SockIOPool.getInstance();
		pool.setServers(new String[]{"127.0.0.1:11211"});
		pool.setInitConn(10);
		pool.setMinConn(10);
		pool.setMaxConn(30);
		pool.setSocketTO(3000);
		pool.setSocketConnectTO(0);
		pool.setNagle(false);
		pool.initialize();
		mc=new MemCachedClient();
		mc.setPrimitiveAsString(true);
	}
	

	@Test
	public void testMAdd()
	{
		SysConfig config=new SysConfig();
		config.setConfigCode(Constant.SHORTMSG_CONFIG_SOFREIGHT);
		config.setConfigDesc("闪亮的开发建设的法律框架是的弗兰克三级地方绿卡时间的");
		config.setConfigName("受法律框架式独立开发建设的雷锋精神多了");
		config.setCreateDate(new Date());
		config.setCreator("hulibo");
		config.setIp("127.0.0.1");
		config.setPassword("sdflksdjflsdf");
		config.setPort(25);
		config.setType(1);
		config.setUrl("http://sdklfjsdlkfsdlfs.com");
		config.setUserName("hulibo");
		boolean flag=mc.add(Constant.SHORTMSG_CONFIG_SOFREIGHT,config);
		System.out.println(flag);
		
		SysConfig t=(SysConfig)mc.get(Constant.SHORTMSG_CONFIG_SOFREIGHT);
		for(int i=0;i<20;i++)
		{
		System.out.println(t.getConfigCode());
		}
		System.out.println(t);
	}
	
	public static void main(String[] args) {
		String email="_sdf_23ds353@s.d";
		String pattern1 = "[a-zA-Z0-9][a-zA-Z0-9._-]{2,16}[a-zA-Z0-9]@[a-zA-Z0-9]+.[a-zA-Z0-9]";  
        Pattern pattern = Pattern.compile(pattern1);  
        Matcher mat = pattern.matcher(email);  
        if (mat.find()) {  
            System.out.println(">>>>>>>>");  
        }
	}
}
