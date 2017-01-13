package com.entropyshift.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.Properties;

/**
 * Created by chaitanya.m on 1/12/17.
 */
public final class PropertiesProvider
{
    @Autowired
    private ApplicationContext appContext;

    private Properties configProp = null;

    private PropertiesProvider() {
        IConfigurationPropertyDao configurationPropertyDao =  (IConfigurationPropertyDao) appContext.getBean("configurationPropertyDao");
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
