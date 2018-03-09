package com.echounion.boss.core.rtx.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.echounion.boss.common.enums.RtxStatus;
import com.echounion.boss.common.util.ThreadLocalUtil;
import com.echounion.boss.core.jms.JmsTemplateUtil;
import com.echounion.boss.core.rtx.Rtx;
import com.echounion.boss.core.rtx.service.IRtxService;
import com.echounion.boss.core.security.annotation.ActionModel;
import com.echounion.boss.core.shortmsg.ShortMsg;
import com.echounion.boss.core.shortmsg.service.IShortMsgService;
import com.echounion.boss.entity.RtxHistory;
import com.echounion.boss.entity.SysLog;
import com.echounion.boss.entity.dto.Caller;
import com.echounion.boss.logs.service.ILogService;
import com.echounion.boss.persistence.rtx.RtxMapper;

@Service
@ActionModel(description="RXT消息服务")
@Transactional(propagation=Propagation.SUPPORTS)
public class RtxServiceImpl implements IRtxService {

	private Logger logger=Logger.getLogger(RtxServiceImpl.class);
	@Autowired
	@Qualifier("SysLogServiceImpl")
	private ILogService<SysLog> logService;
	
	@Autowired
	private JmsTemplateUtil jsmTemplate;
	@Autowired 
	private IShortMsgService mobileService;
	@Autowired
	private RtxMapper rtxMapper;
	
	@Override
	@Transactional
	@ActionModel(description="保存RTX记录")
	public RtxStatus send(Rtx rtx) {
		if(rtx!=null)
		{
			try
			{
				if(!StringUtils.isEmpty(rtx.getPhone()))			//手机号不为空
				{
					ShortMsg shortMsg=new ShortMsg();
					shortMsg.setContent(rtx.getContent());
					shortMsg.setBcode(rtx.getBcode());
					shortMsg.setBtype(rtx.getBtype());
					mobileService.send(shortMsg);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			if(StringUtils.isBlank(rtx.getContent())){logger.error("RTX内容为空！");return RtxStatus.CONTENT_NULL;}
			if(StringUtils.isBlank(rtx.getReceiver())){logger.error("RTX接收人为空！");return RtxStatus.RECEIVER_NULL;}
			
			try
			{
				sendRtx(rtx);
			}catch (JMSException e)
			{
				logger.error("发送RTX到JMS异常"+e);
				logService.addLog(new SysLog("RTX服务","检查RTX并发送","RTX发送到JMS异常"));
				return RtxStatus.ERROR_MQ;
			} catch (Exception e) {
				logger.error("发送RTX系统异常"+e);
				return RtxStatus.SYS_UNKNOW_ERROR;
			}
			return RtxStatus.SUCCESS;
		}
		logger.error("RTX对象为空，请确认参数是否提交服务器！");
		return RtxStatus.RTX_NULL;
	}

	private void sendRtx(Rtx rtx) throws JMSException,Exception {
		RtxHistory history=null;
		Caller caller=(Caller)ThreadLocalUtil.getData();			//该部分要重构 N个RTX号分组拆分批次发送
		
		int length=0;
		List<String> addressList=rtx.getReceiverList();
		do
		{
			length=addressList.size();
			length=length>10?10:length;
			List<String> tmpList=addressList.subList(0,length);
			String receiverStr=StringUtils.join(tmpList, ",");
			history=new RtxHistory();
			history.setTitle(rtx.getTitle());
			history.setReceiver(receiverStr);
			history.setContent(rtx.getContent());
			history.setStatus(RtxHistory.SEND_WAITING);	//默认为等待发送
			history.setPhone(rtx.getPhone());
			history.setSendTime(new Date());
			history.setBcode(rtx.getBcode());
			history.setBtype(rtx.getBtype());
			history.setSoftId(caller.getSoftId());
			int result=addRtx(history);
			if(result>0)										//发送成功
			{
				Rtx tempRtx=(Rtx)BeanUtilsBean.getInstance().cloneBean(rtx);//根据RTX地址拆分一条条RTX
				tempRtx.setReceiver(receiverStr);				//设置当前RTX的收件人
				tempRtx.setHistoryId(history.getId());
				sendRtxToMq(tempRtx);
			}
			tmpList.clear();
		}while(addressList.size()>0);
	}
	
	/**
	 * 把邮件发送到MQ处理
	 * @author 胡礼波
	 * 2012-11-17 上午10:55:21
	 */
	private void sendRtxToMq(final Rtx... rtx)throws JMSException
	{
		if(!ArrayUtils.isEmpty(rtx))
		{
			jsmTemplate.sendDataToMQ(rtx);
		}
	}
	
	public int addRtx(RtxHistory history)
	{
		int count=rtxMapper.addRtxHistory(history);
		return count;
	}
	
	@Override
	@Transactional
	@ActionModel(description="更新RTX错误次数")
	public int editRtxHistoryErrorNum(int rtxId) {
		return rtxMapper.editRtxHistoryErrorNum(rtxId);
	}

	@Override
	@Transactional
	@ActionModel(description="更新RTX发送状态")
	public int editRtxHistoryStatus(int rtxId,int status) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("rtxId",rtxId);
		map.put("status",status);
		return rtxMapper.editRtxlHistoryStatus(map);
	}

	@Override
	public RtxHistory getRtxHistory(int rtxId) {
		return rtxMapper.getRtxHistory(rtxId);
	}
}
