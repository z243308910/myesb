package com.echounion.boss.core.cache.memcache;

import java.io.Serializable;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.danga.MemCached.SockIOPool;
import com.echounion.boss.common.util.FileUtil;
import com.echounion.boss.core.SystemLoaderListener;

/**
 * Memcached配置类
 * @author 胡礼波
 * 2012-11-28 上午09:13:21
 */
public class MemCacheServer implements Serializable {

	/**
	 * @author 胡礼波
	 * 2012-11-28 上午09:13:35
	 */
	private static final long serialVersionUID = -1305199045479910812L;
	private static Logger log = Logger.getLogger(SystemLoaderListener.class);
	
	/**
	 * 初始化Memcached服务器配置
	 * @author 胡礼波
	 * 2012-11-28 上午09:34:20
	 */
	public static void initServer()
	{
		Properties p =FileUtil.getPropertiesFile("system.properties");
		SockIOPool pool=SockIOPool.getInstance();
		pool.setServers(p.getProperty("cached.server").split(","));
		pool.setInitConn(Integer.parseInt(p.getProperty("cached.initConn")));
		pool.setMinConn(Integer.parseInt(p.getProperty("cached.minConn")));
		pool.setMaxConn(Integer.parseInt(p.getProperty("cached.maxConn")));
		pool.setMaxIdle(Integer.parseInt(p.getProperty("cached.maxIdle")));
		pool.setMaintSleep(Integer.parseInt(p.getProperty("cached.maintSleep")));
		pool.setSocketTO(Integer.parseInt(p.getProperty("cached.socketTO")));
		pool.setSocketConnectTO(Integer.parseInt(p.getProperty("cached.socketConnectTO")));
		pool.setNagle(Boolean.parseBoolean(p.getProperty("cached.nagle")));
		pool.setHashingAlg(Integer.parseInt(p.getProperty("cached.HashingAlg")));
		pool.initialize();
		log.info("....................缓存服务器配置成功....................");
	}
}
