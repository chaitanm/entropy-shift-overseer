package com.entropyshift.cassandra;

import com.datastax.driver.core.Cluster;
import info.archinnov.achilles.generated.ManagerFactory;
import info.archinnov.achilles.generated.ManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by chaitanya.m on 1/17/17.
 */
@Configuration
public class CassandraConfiguration
{
    @Bean(destroyMethod = "shutDown")
    public ManagerFactory cassandraNativeCluster()
    {
        Cluster cluster = Cluster.builder()
                .addContactPoints("ec2-54-91-32-81.compute-1.amazonaws.com")
                .withPort(Integer.parseInt("9042"))
                .withClusterName("Test Cluster")
                .build();
        final ManagerFactory factory = ManagerFactoryBuilder
                .builder(cluster)
                .build();
        return factory;
    }
}
