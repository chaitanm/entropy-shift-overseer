package com.entropyshift.overseer.session;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.SimpleStatement;
import com.entropyshift.cassandra.IDataManagerFactoryProvider;

import java.util.List;

/**
 * Created by chaitanya.m on 2/14/17.
 */
public class SessionDao implements ISessionDao
{
    private IDataManagerFactoryProvider dataManagerFactoryProvider;

    public SessionDao(IDataManagerFactoryProvider dataManagerFactoryProvider)
    {
        this.dataManagerFactoryProvider = dataManagerFactoryProvider;
    }

    @Override
    public void insertOrUpdate(Session session)
    {
        this.dataManagerFactoryProvider.getManagerFactory().forSession().crud()
                .insert(session).execute();
    }

    @Override
    public Session getBySessionKeyHash(byte[] sessionKeyHash)
    {
        return this.dataManagerFactoryProvider.getManagerFactory().forSession().crud()
                .findById(sessionKeyHash).get();
    }

    @Override
    public List<Session> getByUserId(String userId)
    {
        final SimpleStatement simpleStatement = new SimpleStatement("SELECT * FROM entropyshift.session WHERE user_id = :user_id");
        return this.dataManagerFactoryProvider.getManagerFactory().forSession().raw()
                .typedQueryForSelect(simpleStatement, userId).getList();
    }

    @Override
    public void deleteBySessionKeyhash(byte[] sessionKeyHash)
    {
       this.dataManagerFactoryProvider.getManagerFactory().forSession().crud()
               .deleteById(sessionKeyHash).execute();
    }

    @Override
    public void bulkDelete(List<byte[]> sessionKeyHashList)
    {
        final BatchStatement batch = new BatchStatement();
        sessionKeyHashList.forEach(sessionKeyHash -> {
            batch.add(this.dataManagerFactoryProvider.getManagerFactory().forOAuthAccess().crud().deleteById(sessionKeyHash).generateAndGetBoundStatement());
        });
        this.dataManagerFactoryProvider.getManagerFactory().forSession().getNativeSession().execute(batch);
    }
}
