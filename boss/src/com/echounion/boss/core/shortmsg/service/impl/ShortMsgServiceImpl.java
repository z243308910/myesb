package com.echounion.boss.core.shortmsg.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.echounion.boss.common.enums.ShortMsgStatus;
import com.echounion.boss.common.util.ThreadLocalUtil;
import com.echounion.boss.core.jms.JmsTemplateUtil;
import com.echounion.boss.core.shortmsg.ShortMsg;
import com.echounion.boss.core.shortmsg.ShortMsgConfig;
import com.echounion.boss.core.shortmsg.service.IShortMsgService;
import com.echounion.boss.entity.MobileHistory;
import com.echounion.boss.entity.SysLog;
import com.echounion.boss.entity.dto.Caller;
import com.echounion.boss.logs.service.ILogService;
import com.echounion.boss.persistence.shortmsg.MobileMapper;

@Service
@Transactional(propagation=Propagation.SUPPORTS)
public class ShortMsgServiceImpl implements IShortMsgService {

	@Autowired
	private MobileMapper mobileMapper;
	private Logger logger=Logger.getLogger(ShortMsgServiceImpl.class);
	@Autowired
	@Qualifier("SysLogServiceImpl")
	private ILogService<SysLog> logService;	
	@Autowired
	private JmsTemplateUtil jsmTemplate;
	
	@Transactional
	public int addMobileHistory(MobileHistory history) {
		int result=0;
		try
		{
			result=mobileMapper.addMobileHistory(history);
		}catch (Exception e) {
			logger.warn("保存短信历史记录<"+history.getPhoneNo()+">出错!"+e);
			logService.addLog(new SysLog("短信服务","记录短信日志","保存短信历史记录<"+history.getPhoneNo()+">出错"));
		}
		return result;
	}

	public ShortMsgStatus send(ShortMsg shortMsg) {
		if(null==shortMsg){
			logger.info("要发送的短信为空，请确认提交的参数正确性!");
			return ShortMsgStatus.DATA_INVALIDATA;
		}
		if(CollectionUtils.isEmpty(shortMsg.getMobiles()))
		{
			logger.info("发送失败,手机号码为空!");
			return ShortMsgStatus.DATA_INVALIDATA;
		}
		String content=shortMsg.getContent();
		if(StringUtils.isBlank(content))
		{
			logger.info("发送失败,短信为空!");
			return ShortMsgStatus.PARAM_INVALIDATA;
		}
		try {
			int length=content.length();
			int pageCount=(length+ShortMsgConfig.MAXLENGTH-1)/ShortMsgConfig.MAXLENGTH;
			String sendContent=null;
			do{
				length=length<ShortMsgConfig.MAXLENGTH? length:ShortMsgConfig.MAXLENGTH;
				sendContent=StringUtils.substring(content,0,length);		//获得发送的内容
				content=StringUtils.substring(content,length);		//获得发送的内容
				shortMsg.setContent(sendContent);
				sendShortMsg(shortMsg);
				pageCount--;
			}while(pageCount>=1);		//500以内字
		} catch (JMSException e) {
			logger.error("发送短信到JMS异常"+e);
			logService.addLog(new SysLog("短信服务","检查短信并发送","短信发送到JMS异常"));
			return ShortMsgStatus.ERROR_MQ;
		} catch (Exception e) {
			logger.error("发送短信系统异常"+e);
			return ShortMsgStatus.ERROR_SYS;
		}
		return ShortMsgStatus.SUCCESS;
	}
	
	private void sendShortMsg(ShortMsg shortMsg) throws JMSException,Exception {
		MobileHistory history=null;
		List<ShortMsg> list=new ArrayList<ShortMsg>();
		ShortMsg[] shortMsgs=null;
		
		Caller caller=(Caller)ThreadLocalUtil.getData();
		
		for (String address:shortMsg.getMobiles()) {	//拆分短信一条条发送
			history=new MobileHistory();
			history.setSoftId(caller.getSoftId());
			history.setContent(shortMsg.getContent());
			history.setErrorsNum(0);							//第一次默认为0
			history.setType(MobileHistory.MSG_SEND);		//类型为发送短信
			history.setStatus(MobileHistory.SEND_WAITING);	//默认为等待发送	
			history.setPhoneNo(address);
			history.setActiveTime(new Date());
			history.setBcode(shortMsg.getBcode());
			history.setBtype(shortMsg.getBtype());
			int result=addMobileHistory(history);
			if(result>0)										//发送成功
			{
				ShortMsg tempShortMsg=(ShortMsg)BeanUtilsBean.getInstance().cloneBean(shortMsg);	//拆分一条条短信
				tempShortMsg.setHistoryId(history.getId());		//标志短信的编号为数据库记录的编号
				tempShortMsg.setMobile(address);
				list.add(tempShortMsg);
			}
		}
		shortMsgs=list.toArray(new ShortMsg[list.size()]);
		sendShortMsgToMq(shortMsgs);			//这里要判断调用哪个消费者 两个网站的通道不同
	}
	
	/**
	 * 把短信发送到MQ处理
	 * @author 胡礼波
	 * 2012-11-17 上午10:55:21
	 */
	private void sendShortMsgToMq(final ShortMsg... shortMsg)throws JMSException
	{
		if(!ArrayUtils.isEmpty(shortMsg))
		{
		jsmTemplate.sendDataToMQ(shortMsg);
		}
	}

	@Transactional
	public int editMobileHistoryStatus(int historyId, int status) {
		int result=0;
		try
		{
			Map<String,Object> data=new  HashMap<String, Object>();
			data.put("historyId",historyId);
			data.put("status", status);
			result=mobileMapper.editMobileHistoryStatus(data);			//更新短信状态 保存成功
			if(status==MobileHistory.SEND_FAIL)							//失败
			{
				result=mobileMapper.editMobileHistoryErrorNum(historyId);	//更新失败的次数
			}
			logger.info("更新短信<"+historyId+">状态成功!");
		}catch (Exception e) {
			logger.info("更新短信<"+historyId+">状态失败!"+e);
			logService.addLog(new SysLog("短信服务","更改短信状态或失败次数","更改短信<"+historyId+">状态或失败次数失败!"));
		}
		return result;
	}

	public ShortMsgStatus reSendShortMsg(Integer[] shortMsgIds) {
		if(ArrayUtils.isEmpty(shortMsgIds))
		{
			logger.warn("没有选中要发送的短信!");
			return ShortMsgStatus.ERROR_SYS;
		}
		try
		{
			List<MobileHistory> list=mobileMapper.getMobileHistoryByIds(shortMsgIds);
			List<ShortMsg> listShortMsg=new ArrayList<ShortMsg>();
			ShortMsg[] shortMsgs=null;
			ShortMsg shortMsg=null;
			for (MobileHistory history : list) {
				shortMsg=new ShortMsg();
				shortMsg.setHistoryId(history.getId());				//历史记录
				shortMsg.setContent(history.getContent());			//内容
				shortMsg.setMobile(history.getPhoneNo());			//手机号码
				listShortMsg.add(shortMsg);
			}
			shortMsgs=listShortMsg.toArray(new ShortMsg[listShortMsg.size()]);
			sendShortMsgToMq(shortMsgs);
			return ShortMsgStatus.SUCCESS;
		}catch (JMSException e) {
			logger.error("再次发送短信到MQ异常!"+e);
			return ShortMsgStatus.ERROR_MQ;
		}catch (Exception e) {
			logger.error("再次发送短信到系统异常!"+e);
			return ShortMsgStatus.ERROR_SYS;
		}
	}

	@Override
	@Transactional
	public int addRegistMobile(String phone) {
		Assert.notNull(phone,"添加注册手机号失败，手机号为空!");
		int count=mobileMapper.getExistMobile(phone);
		if(count>0)		//如果存在相同的注册手机号码
		{
			return 1;
		}
		count=mobileMapper.addRegistMobile(phone);
		return count;
	}

	@Override
	public int getCountByTime(Date begin, Date end) {
		return mobileMapper.getCountByTime(begin, end);
	}
	
}
