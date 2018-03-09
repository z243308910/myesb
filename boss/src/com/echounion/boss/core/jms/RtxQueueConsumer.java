package com.echounion.boss.core.jms;

import java.io.Serializable;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.log4j.Logger;
import com.echounion.boss.core.rtx.Rtx;
import com.echounion.boss.core.rtx.RtxUtil;

/**
 * MQ消息处理消费者--RTX通道
 * @author 胡礼波
 * 2012-11-16 下午04:22:55
 */
public class RtxQueueConsumer implements MessageListener{

	public RtxQueueConsumer(){}
	
	private Logger logger=Logger.getLogger(RtxQueueConsumer.class);
	
	@Override
	public void onMessage(Message message) {
		try {
			if(message instanceof ActiveMQObjectMessage)
			{
				Serializable amo=((ActiveMQObjectMessage)message).getObject();
				if(amo instanceof Rtx)
				{
					Rtx rxt=(Rtx)((ActiveMQObjectMessage)message).getObject();
					try {
						RtxUtil.send(rxt);
						Thread.sleep(200);
					} catch (Exception e) {
						logger.error("MQ消费RXT异常",e);
					}
				}else if(amo instanceof Rtx[])
				{
					Rtx[] rxt=(Rtx[])((ActiveMQObjectMessage)message).getObject();
					try {
						RtxUtil.send(rxt);
						Thread.sleep(200);
					} catch (Exception e) {
						logger.error("MQ消费RXT异常",e);
					}
				}
			}
		} catch (JMSException e) {
			logger.error("MQ消费文本数据异常"+e);
		}
	}
}
