package com.tysdev.tools.mqsender.jmsproviders;

import com.ibm.jms.JMSTextMessage;
import com.ibm.mq.jms.*;

import javax.jms.JMSException;
import javax.jms.Session;


/**
 * JMS Provider:
 * WebSphere MQ
 *
 * @author Tyryshkin Alexander
 */
public class WebSphereMqProvider implements IJmsProvider
{
	private MQQueueConnectionFactory cf         = new MQQueueConnectionFactory();
	private MQQueueConnection        connection = null;
	private MQQueueSession           session    = null;


	@Override
	public void connect(String host, String port, String queueManager, String channel)
			throws
			JMSException
	{
		try
		{
			MQQueueConnectionFactory cf = new MQQueueConnectionFactory();
			cf.setHostName(host);
			cf.setPort(Integer.parseInt(port));
			cf.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);
			cf.setQueueManager(queueManager);
			cf.setChannel(channel);

			connection = (MQQueueConnection) cf.createQueueConnection();
			connection.start();

			session = (MQQueueSession) connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		}
		catch( Exception error )
		{
			throw new JMSException("Can't establish the conenction!\n" + error.getMessage());
		}
	}


	@Override
	public void sendTextMessage(String queueName, String textMessage) throws Exception
	{
		MQQueue queue = (MQQueue) session.createQueue("queue:///" + queueName);
		MQQueueSender sender = (MQQueueSender) session.createSender(queue);

		JMSTextMessage message = (JMSTextMessage) session.createTextMessage(textMessage);

		sender.send(message);

		sender.close();
	}


	@Override
	public void close()
	{
		try
		{
			if( session != null )
			{
				session.close();
			}
			if( connection != null )
			{
				connection.close();
			}
		}
		catch( JMSException ignored )
		{
		}
	}
}