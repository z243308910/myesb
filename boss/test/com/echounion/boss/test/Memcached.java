package com.echounion.boss.test;

import java.util.Date;
import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

public class Memcached {

	private static MemCachedClient mcc=new MemCachedClient();
	private static Memcached mem=new Memcached();
	
	static 
	{
		//服务器列表和其权重，个人memcached地址和端口
		String[] servers={"127.0.0.1:11211"};
		Integer[] weights={3};
		
		//获取socket连接池的实例对象
		SockIOPool pool=SockIOPool.getInstance();
		
		//设置服务器信息
		pool.setServers(servers);
		pool.setWeights(weights);
		
		//设置初始连接，最小和最大连接数以及最大处理时间
		pool.setInitConn(5);
		pool.setMinConn(5);
		pool.setMaxConn(250);
		pool.setMaxIdle(1000*60*60*6);
		
		//设置主线程的睡眠时间
		pool.setMaintSleep(30);
		
		//设置TCP的参数，连接超时等
		pool.setNagle(false);
		pool.setSocketTO(3000);
		pool.setSocketConnectTO(0);
		
		//初始化连接池
		pool.initialize();
	}
	
	/**
	 * 获取唯一实例
	 * @return
	 */
	public static Memcached getInstance()
	{
		return mem;
	}
	
	/**
	 * 添加一个指定的值到缓存中
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean add(String key,Object value)
	{
		return mcc.add(key, value);
	}
	
	public boolean add(String key,Object value,Date expiry)
	{
		return mcc.add(key, value, expiry);
	}
	
	/**
	 * 替换一个指定的值到缓存
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean replace(String key,Object value)
	{
		return mcc.replace(key, value);
	}
	
	public boolean replace(String key,Object value,Date expiry)
	{
		return mcc.replace(key, value, expiry);
	}
	
	/**
	 * 删除一个指定的值到缓存
	 * @param key
	 * @return
	 */
	public boolean delete(String key)
	{
			return mcc.delete(key);
	}
	
	/**
	 * 根据指定的关键字获取对象
	 * @param key
	 * @return
	 */
	public Object get(String key)
	{
			return mcc.get(key);
	}
	
	public static void main(String[] args) {
		Memcached cache=Memcached.getInstance();
		mcc.flushAll();
		for(int i=1;i<100000;i++)
		{
			cache.add("key"+i,new String[]{"LDSFKJDFLKJ;SLKDFJDLKGJDLdf.kgdf;lk下的两分开始懂了福克斯的；类似的福克斯的浪费是的发生的浪费速度","岁的李开发建设的离开螺丝钉棵解放螺丝钉棵防水堵漏顺利打开解放螺丝钉棵发生的","岁的李开发建设拉开大联手开发建设罗迪克烦死了看风景三闾大夫算啦发送李开发塞德里克发送旅客发生的激烈交锋岁的李开发上的浪费苏打绿发生的发生的发送代理费速度","DDD","EEE","FFF","GGG","HHHH","IIII","LLLLL"});
		}
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>缓存成功!");
		for (int i = 1; i <100000; i++) {
			String vlaue=cache.get("key"+i).toString();
			System.out.println("<<<<<<<<<<<<<<<  "+vlaue+" >>>>>>>>>>>>>>>>");
		}
	}
}
