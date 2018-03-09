package com.echounion.boss.core.monitor;

import java.nio.file.FileSystems;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.WatchEvent.Kind;
import org.apache.log4j.Logger;
import com.echounion.boss.core.monitor.service.impl.IMonitorService;

/**
 * 监控目录结构
 * @author 胡礼波 2013-10-14 下午3:29:51
 */
public class MonitorDir {

	private IMonitorService monitorService; 
	private static Logger log = Logger.getLogger(MonitorDir.class);
	
	/**
	 * 开始监控
	 * @author 胡礼波
	 * 2013-10-14 下午3:47:41
	 * @param path 监控的目录
	 * @throws Exception
	 */
	public void watch(final String...path) throws Exception {
		Thread thread=new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					doWatch(path);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
		thread.start();
	}

	private void doWatch(String... path)throws Exception
	{
		try (WatchService watchService = FileSystems.getDefault().newWatchService()) {

			for (String th : path) {
				log.info("系统正在监听目录<"+th+">");
				Path monitorPath=Paths.get(th);
				monitorPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
						StandardWatchEventKinds.ENTRY_MODIFY,
						StandardWatchEventKinds.ENTRY_DELETE); // 注册监听事件
			}

			WatchKey key = null;
			do {
				key = watchService.take();
				for (WatchEvent<?> watchEvent : key.pollEvents()) {
					final Kind<?> kind = watchEvent.kind();
					if (kind == StandardWatchEventKinds.OVERFLOW) {
						continue;
					}
					
					Path file=(Path)watchEvent.context();
					if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
						monitorService.onCreate(file);
					} else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
						monitorService.onDelete(file);
					} else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
						monitorService.onModify(file);
					}

				}
			} while (key.reset());
		}
	}
	
	public IMonitorService getMonitorService() {
		return monitorService;
	}

	public void setMonitorService(IMonitorService monitorService) {
		this.monitorService = monitorService;
	}
}
