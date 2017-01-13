package com.entropyshift.configuration;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Properties;

/**
 * Created by chaitanya.m on 1/12/17.
 */
public class ConfigurationPropertyDao implements IConfigurationPropertyDao
{
    private HibernateTemplate template;

    public ConfigurationPropertyDao(HibernateTemplate template) {
        this.template = template;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.DEFAULT)
    public Properties getConfigurationProperties() {
        Properties configProperties = new Properties();
        DetachedCriteria criteria = DetachedCriteria.forClass(ConfigurationProperty.class);
        List<ConfigurationProperty> properties  = (List<ConfigurationProperty>) template.findByCriteria(criteria);
        properties.forEach(property -> configProperties.put(property.getPropertyName(), property.getPropertyValue()));
        return configProperties;
    }
}
