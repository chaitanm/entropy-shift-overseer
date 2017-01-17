package com.entropyshift.cassandra;

import info.archinnov.achilles.generated.ManagerFactory;
import info.archinnov.achilles.generated.manager.OAuthAccess_Manager;
import info.archinnov.achilles.generated.manager.OAuthAuthorization_Manager;
import info.archinnov.achilles.generated.manager.OAuthRefresh_Manager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by chaitanya.m on 1/17/17.
 */
@Configuration
public class AchillesConfiguration
{
    private ManagerFactory managerFactory = new CassandraConfiguration().cassandraNativeCluster();

    @Bean
    public OAuthAccess_Manager oAuthAccessManager()
    {
        return managerFactory.forOAuthAccess();
    }

    @Bean
    public OAuthAuthorization_Manager oAuthAuthorizationManager()
    {
        return this.managerFactory.forOAuthAuthorization();
    }

    @Bean
    public OAuthRefresh_Manager oAuthRefreshManager()
    {
        return managerFactory.forOAuthRefresh();
    }
}
