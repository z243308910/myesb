package com.echounion.bossmanager.core.exception;
/**
 * 数据验证异常
 * @author 胡礼波
 * 2012-11-14 上午10:04:28
 */
public class CheckDataException extends RuntimeException {

	/**
	 * @author 胡礼波
	 * 2012-11-14 上午10:04:37
	 */
	private static final long serialVersionUID = -7807568605446732524L;

	public CheckDataException(String msg)
	{
		super(msg);
	}
	
}
