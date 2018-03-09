package com.echounion.boss.core.cache.memcache;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import com.danga.MemCached.MemCachedClient;

/**
 * 缓存工具类
 * @author 胡礼波
 * 2012-11-1 下午06:13:42
 */
public class MemCacheUtil {

	private static MemCachedClient mclient;
	
	private MemCacheUtil(){}
	
	/**
	 * 获得MemcacheClient对象
	 * @author 胡礼波
	 * 2012-11-1 下午06:54:36
	 * @return
	 */
	private static MemCachedClient getClient()
	{
		Lock lock=new ReentrantLock();
		try
		{
			lock.lock();
			if(mclient==null)
			{
				mclient=new MemCachedClient();
				mclient.setPrimitiveAsString(true);
			}
			return mclient;
		}finally
		{
			lock.unlock();
		}
	}
	
	/**
	 * 添加一个指定的值到缓存中
	 * @author 胡礼波
	 * 2012-11-1 下午06:15:08
	 * @param key
	 * @param value
	 * @param isReplace true表示如果存在key则替换，false表示则添加
	 * @return
	 */
	public static boolean add(String key,Serializable value,boolean isReplace)throws IOException
	{
		Lock lock=new ReentrantLock();
		boolean flag=false;
		try
		{
			lock.lock();
			if(isExist(key))
			{
				if(isReplace)
				{
					flag=replace(key, value);
				}
			}else
			{
				flag=getClient().add(key, value);
			}
			return flag;
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/**
	 * 添加一个指定的值到缓存中
	 * @author 胡礼波
	 * 2012-11-1 下午06:15:14
	 * @param key
	 * @param value
	 * @param expiry 有效期
	 * @param isReplace isReplace true表示如果存在key则替换，false表示则添加
	 * @return
	 */
	public static boolean add(String key,Serializable value,Integer expiry,boolean isReplace)throws IOException
	{
		Lock lock=new ReentrantLock();
		boolean flag=false;
		try
		{
			lock.lock();
			if(isExist(key))
			{
				if(isReplace)
				{
					flag=replace(key, value,expiry);
				}
			}else
			{
				flag=getClient().add(key, value,expiry);
			}
			return flag;
		}finally
		{
			lock.unlock();
		}
	}
	
	/**
	 * 替换一个指定的值到缓存
	 * @author 胡礼波
	 * 2012-11-1 下午06:15:50
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean replace(String key,Serializable value)throws IOException
	{
		Lock lock=new ReentrantLock();
		try
		{
			lock.lock();
			return getClient().replace(key, value);
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/**
	 * 替换一个指定的值到缓存
	 * @author 胡礼波
	 * 2012-11-1 下午06:15:58
	 * @param key
	 * @param value
	 * @param expiry 有效期
	 * @return
	 */
	public static boolean replace(String key,Serializable value,Integer expiry)throws IOException
	{
		Lock lock=new ReentrantLock();
		try
		{
			lock.lock();
		return getClient().replace(key, value, expiry);
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/**
	 * 删除一个指定的值到缓存
	 * @author 胡礼波
	 * 2012-11-1 下午06:16:11
	 * @param key
	 * @return
	 */
	public static boolean delete(String key)throws IOException
	{
		Lock lock=new ReentrantLock();
		try
		{
			lock.lock();
			return getClient().delete(key);
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/**
	 * 根据指定的关键字获取对象
	 * @author 胡礼波
	 * 2012-11-1 下午06:16:19
	 * @param key
	 * @return
	 */
	public static Object get(String key)throws IOException
	{
		Object obj=getClient().get(key);
		return obj;
	}
	
	/**
	 * 判断某个元素是否存在
	 * @author 胡礼波
	 * 2012-11-1 下午07:06:03
	 * @param key
	 * @return 存在返回true 不存在返回false
	 */
	public static boolean isExist(String key)throws IOException
	{
		boolean flag=get(key)==null?false:true;
		return flag;
	}
}
