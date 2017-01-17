package com.entropyshift.configuration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Properties;

/**
 * Created by chaitanya.m on 1/12/17.
 */

@Configuration
public final class PropertiesProvider
{
    private Properties configProp = null;

    private PropertiesProvider() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        IConfigurationPropertyDao configurationPropertyDao =  (IConfigurationPropertyDao) applicationContext.getBean("configurationPropertyDao");
        configProp = configurationPropertyDao.getConfigurationProperties();
    }

    private static class LazyHolder {
        private static final PropertiesProvider INSTANCE = new PropertiesProvider();
    }

    public static PropertiesProvider getInstance() {
        return LazyHolder.INSTANCE;
    }

    public String getProperty(String key) {
        return configProp.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return configProp.getProperty(key, defaultValue);
    }
}
