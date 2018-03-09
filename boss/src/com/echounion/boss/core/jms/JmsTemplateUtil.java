package com.echounion.boss.core.jms;

import java.io.Serializable;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import com.echounion.boss.core.email.EmailMessage;
import com.echounion.boss.core.rtx.Rtx;
import com.echounion.boss.core.shortmsg.ShortMsg;

/**
 * JMS发送类
 * @author 胡礼波
 * 2013-3-13 下午5:30:36
 */
@Component
public class JmsTemplateUtil {

	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	@Qualifier("emailDestinationMessageQueue")
	private Destination emailDestination;
	
	@Autowired
	@Qualifier("shortMsgDestinationMessageQueue")
	private Destination shortMsgDestination;
	
	@Autowired
	@Qualifier("rtxDestinationMessageQueue")
	private Destination rtxDestination;	
	
	public void sendDataToMQ(final Serializable object)
	{
		Destination des=null;
		if(object instanceof Rtx || object instanceof Rtx[])				//RTX通道
		{
			des=rtxDestination;
		}else if(object instanceof EmailMessage || object instanceof EmailMessage[])
		{
			des=emailDestination;
		}
		else if(object instanceof ShortMsg || object instanceof ShortMsg[])
		{
			des=shortMsgDestination;
		}
		if(des==null)
		{
			return;
		}
		
		jmsTemplate.send(des,new MessageCreator()//调用mq
		{
			@Override
			public Message createMessage(Session session) throws JMSException{
				Message message=session.createObjectMessage(object);
				return message;
			}
		});
	}
}
