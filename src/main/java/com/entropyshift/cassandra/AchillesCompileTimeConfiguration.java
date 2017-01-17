package com.entropyshift.cassandra;

import info.archinnov.achilles.annotations.CompileTimeConfig;
import info.archinnov.achilles.type.CassandraVersion;
import info.archinnov.achilles.type.strategy.ColumnMappingStrategy;

/**
 * Created by chaitanya.m on 1/10/17.
 */
@CompileTimeConfig(cassandraVersion = CassandraVersion.CASSANDRA_3_2,
        columnMappingStrategy = ColumnMappingStrategy.EXPLICIT)
public interface AchillesCompileTimeConfiguration
{

}
