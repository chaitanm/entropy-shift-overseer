package com.entropyshift.configuration;

import java.util.Properties;

/**
 * Created by chaitanya.m on 1/12/17.
 */

public class PropertiesProvider implements IPropertiesProvider
{
    private Properties configProp = null;

    public PropertiesProvider(IConfigurationPropertyDao configurationPropertyDao) {
        configProp = configurationPropertyDao.getConfigurationProperties();
    }

    public String getProperty(String key) {
        return configProp.getProperty(key);
    }

}
