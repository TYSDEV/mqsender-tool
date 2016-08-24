package com.tysdev.tools.mqsender;

import com.tysdev.tools.mqsender.jmsproviders.ActiveMqProvider;
import com.tysdev.tools.mqsender.jmsproviders.JmsProvider;
import com.tysdev.tools.mqsender.jmsproviders.WebSphereMqProvider;


/**
 * JMS Providers
 * TODO : Refactor
 * @author Tyryshkin Alexander
 */
public class JmsProviderFactory {
    public static final int JMS_PROVIDER_ACTIVEMQ    = 1;
    public static final int JMS_PROVIDER_WEBSPHEREMQ = 2;


    /**
     * Creates concrete JMS provider
     *
     * @param provider
     * @return
     * @throws IllegalArgumentException
     */
    public static JmsProvider getJmsProvider(int provider) throws IllegalArgumentException {
        switch (provider) {
            case JMS_PROVIDER_ACTIVEMQ:
                return new ActiveMqProvider();
            case JMS_PROVIDER_WEBSPHEREMQ:
                return new WebSphereMqProvider();
        }
        throw new IllegalArgumentException();
    }
}
