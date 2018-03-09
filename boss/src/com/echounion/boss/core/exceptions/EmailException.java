package com.echounion.boss.core.exceptions;

/**
 * 邮件自定义异常
 * @author 胡礼波
 * 2012-11-19 下午03:29:36
 */
public class EmailException extends RuntimeException{

	/**
	 * @author 胡礼波
	 * 2012-11-19 下午03:29:51
	 */
	private static final long serialVersionUID = -5318686895304273570L;

	public EmailException(String msg)
	{
		super(msg);
	}
	
	public EmailException(Throwable e)
	{
		super(e);
	}
}
