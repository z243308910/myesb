package com.echounion.boss.core.exceptions;

/**
 * 短信网关自定义异常
 * @author 胡礼波
 * 2012-11-19 下午03:29:36
 */
public class ShortMsgException extends RuntimeException{

	/**
	 * @author 胡礼波
	 * 2012-11-19 下午03:29:51
	 */
	private static final long serialVersionUID = -5318686895304273570L;

	public ShortMsgException(String msg)
	{
		super(msg);
	}
	
	public ShortMsgException(Throwable e)
	{
		super(e);
	}
}
