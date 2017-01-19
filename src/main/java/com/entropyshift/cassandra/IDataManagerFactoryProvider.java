package com.entropyshift.cassandra;

import info.archinnov.achilles.generated.ManagerFactory;

/**
 * Created by chaitanya.m on 1/18/17.
 */
public interface IDataManagerFactoryProvider
{
    ManagerFactory  getManagerFactory();
}
