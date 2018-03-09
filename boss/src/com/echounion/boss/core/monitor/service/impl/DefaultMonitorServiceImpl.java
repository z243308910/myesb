package com.echounion.boss.core.monitor.service.impl;

import java.nio.file.Path;

import org.springframework.stereotype.Service;

/**
 * 默认的监控实现
 * @author 胡礼波
 * 2013-10-14 下午3:38:34
 */
@Service("DefaultMonitorServiceImpl")
public abstract class DefaultMonitorServiceImpl implements IMonitorService {

	@Override
	public void onCreate(Path path) {
	}

	@Override
	public void onDelete(Path path) {
	}

	@Override
	public void onModify(Path path) {
	}

}
