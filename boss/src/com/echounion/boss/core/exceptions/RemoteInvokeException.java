package com.echounion.boss.core.exceptions;

/**
 * 远程方法调用异常
 * @author 胡礼波
 * 2013-7-10 下午2:01:23
 */
public class RemoteInvokeException  extends Exception{

	/**
	 * @author 胡礼波
	 * 2013-7-10 下午2:01:49
	 */
	private static final long serialVersionUID = -220839098183338624L;

	public RemoteInvokeException(String msg)
	{
		super(msg);
	}
	
	public RemoteInvokeException(Throwable e)
	{
		super(e);
	}
}
