package com.echounion.bossmanager.common.security;


import org.junit.Test;
import junit.framework.TestCase;

public class InstansOfTest extends TestCase {

	@Test
	public void testInstansOf()
	{
		Object data=new Integer[10];
		if(data.getClass().isArray())
		{
			System.out.println(222);
		}
	}
}
