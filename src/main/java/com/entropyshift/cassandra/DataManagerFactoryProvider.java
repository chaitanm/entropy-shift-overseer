package com.entropyshift.cassandra;

import com.datastax.driver.core.Cluster;
import com.entropyshift.PropertyNameConstants;
import com.entropyshift.configuration.IPropertiesProvider;
import info.archinnov.achilles.generated.ManagerFactory;
import info.archinnov.achilles.generated.ManagerFactoryBuilder;

/**
 * Created by chaitanya.m on 1/18/17.
 */
public class DataManagerFactoryProvider implements IDataManagerFactoryProvider
{
    private final ManagerFactory managerFactory;

    public DataManagerFactoryProvider(IPropertiesProvider propertiesProvider)
    {
        Cluster cluster = Cluster.builder()
                .addContactPoints(propertiesProvider.getProperty(PropertyNameConstants.CASSANDRA_HOST))
                .withPort(Integer.parseInt(propertiesProvider.getProperty(PropertyNameConstants.CASSANDRA_CQL_PORT)))
                .withClusterName(propertiesProvider.getProperty(PropertyNameConstants.CASSANDRA_CLUSTER_NAME))
                .build();
        this.managerFactory = ManagerFactoryBuilder
                .builder(cluster)
                .build();
    }

    @Override
    public ManagerFactory getManagerFactory()
    {
        return this.managerFactory;
    }
}
