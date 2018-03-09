package com.echounion.bossmanager.core.cache.memcached;

import java.io.Serializable;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.danga.MemCached.SockIOPool;
import com.echounion.bossmanager.core.SystemLoaderListener;
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
		Properties p =SystemLoaderListener.loadFile("system.properties");
		SockIOPool pool=SockIOPool.getInstance();
		String servers=p.getProperty("cached.server");
		pool.setServers(servers.split(","));
		pool.setInitConn(Integer.parseInt(p.getProperty("cached.initConn")));
		pool.setMinConn(Integer.parseInt(p.getProperty("cached.minConn")));
		pool.setMaxConn(Integer.parseInt(p.getProperty("cached.maxConn")));
		pool.setSocketTO(Integer.parseInt(p.getProperty("cached.socketTO")));
		pool.setSocketConnectTO(Integer.parseInt(p.getProperty("cached.socketConnectTO")));
		pool.initialize();
		log.info("....................缓存服务器<"+servers+">配置成功....................");
	}
}
