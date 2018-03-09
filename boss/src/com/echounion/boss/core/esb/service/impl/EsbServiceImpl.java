package com.echounion.boss.core.esb.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echounion.boss.common.enums.RecordType;
import com.echounion.boss.common.enums.Status;
import com.echounion.boss.common.enums.SystemStatus;
import com.echounion.boss.core.esb.convertor.ConvertorFactory;
import com.echounion.boss.core.esb.convertor.impl.HttpConvertor;
import com.echounion.boss.core.esb.convertor.impl.SimpleConvertorFactory;
import com.echounion.boss.core.esb.service.IEsbService;
import com.echounion.boss.entity.EsbApiLog;
import com.echounion.boss.entity.dto.Caller;
import com.echounion.boss.entity.dto.Provider;
import com.echounion.boss.entity.dto.Record;
import com.echounion.boss.entity.dto.ResponseMessage;
import com.echounion.boss.logs.service.ILogService;
import com.echounion.boss.persistence.esb.EsbServerMapper;
import com.echounion.boss.persistence.esb.EsbServiceDirMapper;
import com.echounion.boss.persistence.esb.EsbSoftWareMapper;

@Service("EsbServiceImpl")
@Transactional(propagation=Propagation.SUPPORTS)
public class EsbServiceImpl implements IEsbService {

	private Logger logger=Logger.getLogger(IEsbService.class);
	
	@Autowired
	@Qualifier("EsbApiLogServiceImpl")
	private ILogService<EsbApiLog> logService;
	@Autowired
	private EsbServiceDirMapper serviceDirMapper;
	@Autowired
	private EsbSoftWareMapper softWareMapper;
	@Autowired
	private EsbServerMapper serverMapper;
	
	@Override
	public Record invokeService(Provider provider) {
		ConvertorFactory factory=SimpleConvertorFactory.create(provider);
		Record record=null;
		try {
			ResponseMessage message=null;
			if(factory instanceof HttpConvertor)
			{
				message=((HttpConvertor)factory).invoke(provider.getUrl(),provider.getParamMap(),provider.getMethod());
				
			}
			if(message.getStatus()==Status.SUCCESS)			//响应成功
			{
				record=Record.setSuccessRecord(message.getMessage(),false,RecordType.JSON);
			}else
			{
				record=Record.setFailRecord(message.getCode(),message.getMessage(),RecordType.TEXT);
			}
			addEsbLog(provider,message);
		}
		catch(ConnectTimeoutException e)
		{
			logger.error("远程服务连接超时"+e.getMessage());
			record=Record.setFailRecord(SystemStatus.SERVICE_TIME_OUT.getCode(),SystemStatus.SERVICE_TIME_OUT.getName(),RecordType.TEXT);
		}
		catch(HttpHostConnectException e)
		{
			logger.error("远程服务未开启或拒绝连接!"+e.getMessage());
			record=Record.setFailRecord(SystemStatus.SERVICE_REFUSED.getCode(),SystemStatus.SERVICE_REFUSED.getName(),RecordType.TEXT);
		}
		catch (Exception e)
		{
			logger.error("系统远程调用错误"+e.getMessage());
			record=Record.setFailRecord(SystemStatus.SYS_UNKNOW_ERROR.getCode(),SystemStatus.SYS_UNKNOW_ERROR.getName(),RecordType.TEXT);
		}
		return record;
	}

	/**
	 * 添加日志
	 * @author 胡礼波
	 * 2013-9-4 下午3:05:27
	 */
	private void addEsbLog(final Provider provider,final ResponseMessage message)
	{
		EsbApiLog log=new EsbApiLog(provider,message.getCode(),message.getMessage());
		log.setInvokeTime(new Date());
		logService.addLog(log);	
	}
	
	@Override
	public List<Provider> getProviders() {
		List<Provider> providerList=serviceDirMapper.getServiceDirInfo();
		return providerList;
	}

	@Override
	public List<Caller> getCallers() {
		return softWareMapper.getCallers();
	}

	@Override
	public List<String> getIp() {
		return serverMapper.getIp();
	}
	
}
