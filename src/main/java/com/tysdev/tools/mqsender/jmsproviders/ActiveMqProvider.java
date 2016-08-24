package com.tysdev.tools.mqsender.jmsproviders;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;


/**
 * JMS Provider: Apache ActiveMQ
 *
 * @author Tyryshkin Alexander
 */
public class ActiveMqProvider implements JmsProvider {
    private Connection connection = null;
    private Session    session    = null;


    @Override
    public void connect(String host, String port, String queueManager, String channel)
            throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://" + host + ":" + port + "/");
        connectionFactory.setCopyMessageOnSend(false);

        connection = connectionFactory.createConnection();
        connection.start();

        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }


    @Override
    public void sendTextMessage(String queueName, String textMessage) throws Exception {
        Destination destination = session.createQueue(queueName);

        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        TextMessage message = session.createTextMessage(textMessage);
        producer.send(message);

        producer.close();
    }


    @Override
    public void close() {
        try {
            if (session != null) {
                session.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        catch (JMSException ignored) {
        }
    }
}