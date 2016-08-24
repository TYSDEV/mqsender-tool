package com.tysdev.tools.mqsender.jmsproviders;

import javax.jms.JMSException;
import java.util.Map;


/**
 * JMS provider interface
 *
 * @author Tyryshkin Alexander
 */
public interface JmsProvider {

    String PARAM_HOST = "host";
    String PARAM_PORT = "port";

    /**
     * Establish the connection
     *
     * @param params - Connection params
     */
    void connect(Map<String, Object> params) throws JMSException;


    /**
     * Send message
     */
    void sendTextMessage(String queueName, String textMessage) throws Exception;


    /**
     * Закрытие соединения
     */
    void close();
}