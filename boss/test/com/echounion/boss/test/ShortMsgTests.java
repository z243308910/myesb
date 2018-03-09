package com.echounion.boss.test;

import java.io.UnsupportedEncodingException;
import org.junit.Test;

import com.echounion.boss.core.shortmsg.api.ShortMsgSession;

public class ShortMsgTests {

	@Test
	public void testMsgContent()
	{
		try {
			ShortMsgSession s=new ShortMsgSession("SDK-SKY-010-01810","584695","http://117.79.237.16/webservice.asmx");
			System.out.println(s.getBalance());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
