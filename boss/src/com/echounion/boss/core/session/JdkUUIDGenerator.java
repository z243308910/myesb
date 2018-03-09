package com.echounion.boss.core.session;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;

/**
 * JDK UUID生成
 * @author 胡礼波
 * 2012-5-4 下午11:04:54
 */
public class JdkUUIDGenerator implements SessionIdGenerator {
	
	public String get() {
		return StringUtils.remove(UUID.randomUUID().toString(), '-');
	}

	public static String getSessionId()
	{
		return new JdkUUIDGenerator().get();
	}
}
