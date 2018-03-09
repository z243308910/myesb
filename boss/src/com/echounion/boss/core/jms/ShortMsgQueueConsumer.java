package com.echounion.boss.core.jms;

import java.io.Serializable;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.log4j.Logger;
import com.echounion.boss.core.shortmsg.ShortMsg;
import com.echounion.boss.core.shortmsg.ShortMsgUtil;

/**
 * MQ消息处理消费者--短信通道
 * @author 胡礼波
 * 2012-11-16 下午04:22:55
 */
public class ShortMsgQueueConsumer implements MessageListener{

	public ShortMsgQueueConsumer(){}
	
	private Logger logger=Logger.getLogger(ShortMsgQueueConsumer.class);
	
	@Override
	public void onMessage(Message message) {
		try {
		if(message instanceof ActiveMQObjectMessage)
		{
			Serializable amo=((ActiveMQObjectMessage)message).getObject();
			if(amo instanceof ShortMsg[])
			{
				ShortMsg[] shortMsgs=(ShortMsg[])((ActiveMQObjectMessage)message).getObject();
				try {
					ShortMsgUtil.send(shortMsgs);
					Thread.sleep(200);
				} catch (Exception e) {
					logger.error("MQ消费短信异常"+e);
				}
			}else if(amo instanceof ShortMsg)
			{
				ShortMsg shortMsgs=(ShortMsg)((ActiveMQObjectMessage)message).getObject();
				try {
					ShortMsgUtil.send(shortMsgs);
					Thread.sleep(200);
				} catch (Exception e) {
					logger.error("MQ消费短信异常"+e);
				}
			}
		}
		} catch (JMSException e) {
			logger.error("MQ消费文本数据异常"+e);
		}
	}
}
