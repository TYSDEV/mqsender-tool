package com.tysdev.tools.mqsender.jmsproviders;

import javax.jms.JMSException;


/**
 * JMS provider interface
 *
 * @author Tyryshkin Alexander
 */
public interface JmsProvider {
    /**
     * Establish the connection
     *
     * @param host
     * @param port
     * @param queueManager
     * @param channel
     */
    void connect(String host, String port, String queueManager, String channel) throws JMSException; // TODO : Refactor


    /**
     * Send message
     */
    void sendTextMessage(String queueName, String textMessage) throws Exception;


    /**
     * Закрытие соединения
     */
    void close();
}