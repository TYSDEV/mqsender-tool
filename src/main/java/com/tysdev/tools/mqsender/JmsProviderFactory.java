package com.tysdev.tools.mqsender;

import com.tysdev.tools.mqsender.jmsproviders.ActiveMqProvider;
import com.tysdev.tools.mqsender.jmsproviders.JmsProvider;
import com.tysdev.tools.mqsender.jmsproviders.WebSphereMqProvider;

import javax.jms.JMSException;
import java.util.Map;


/**
 * JMS Providers
 *
 * @author Tyryshkin Alexander
 */
public class JmsProviderFactory {
    public static final int JMS_PROVIDER_ACTIVEMQ    = 1;
    public static final int JMS_PROVIDER_WEBSPHEREMQ = 2;


    /**
     * Creates concrete JMS provider
     */
    public static JmsProvider createJmsProvider(int providerId, Map<String, Object> params) throws IllegalArgumentException, JMSException {
        JmsProvider provider;
        switch (providerId) {
            case JMS_PROVIDER_ACTIVEMQ:
                provider = new ActiveMqProvider();
                break;
            case JMS_PROVIDER_WEBSPHEREMQ:
                provider = new WebSphereMqProvider();
                break;
            default:
                throw new IllegalArgumentException();
        }
        provider.connect(params);
        return provider;
    }
}
