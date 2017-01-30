package com.entropyshift.overseer.oauth2.access;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.SimpleStatement;
import com.entropyshift.cassandra.IDataManagerFactoryProvider;

import java.util.List;

/**
 * Created by chaitanya.m on 1/20/17.
 */
public class OAuthAccessDao implements IOAuthAccessDao
{
    private IDataManagerFactoryProvider dataManagerFactoryProvider;

    public OAuthAccessDao(IDataManagerFactoryProvider dataManagerFactoryProvider)
    {
        this.dataManagerFactoryProvider = dataManagerFactoryProvider;
    }

    @Override
    public void insert(OAuthAccess oAuthAccess)
    {
        this.dataManagerFactoryProvider.getManagerFactory().forOAuthAccess().crud()
                .insert(oAuthAccess).execute();
    }

    @Override
    public OAuthAccess getByAccessCodeHash(byte[] accessCodeHash)
    {
        return this.dataManagerFactoryProvider.getManagerFactory().forOAuthAccess().crud()
                .findById(accessCodeHash).get();
    }


    @Override
    public List<OAuthAccess> getByUserId(String userId)
    {
        final SimpleStatement simpleStatement = new SimpleStatement("SELECT * FROM entropyshift.oauth_access WHERE user_id = :user_id");
        return this.dataManagerFactoryProvider.getManagerFactory().forOAuthAccess().raw()
                .typedQueryForSelect(simpleStatement, userId).getList();
    }

    @Override
    public List<OAuthAccess> getByClientId(String clientId)
    {
        final SimpleStatement simpleStatement = new SimpleStatement("SELECT * FROM entropyshift.oauth_access WHERE client_id = :client_id", clientId);
        return this.dataManagerFactoryProvider.getManagerFactory().forOAuthAccess().raw()
                .typedQueryForSelect(simpleStatement, clientId).getList();
    }

    @Override
    public void deleteByAccessCodeHash(byte[] accessCodeHash)
    {
        this.dataManagerFactoryProvider.getManagerFactory().forOAuthAccess().crud()
                .deleteById(accessCodeHash).execute();
    }

    @Override
    public void bulkDelete(List<byte[]> accessCodeHashList)
    {
        final BatchStatement batch = new BatchStatement();
        accessCodeHashList.forEach(accessCodeHash -> {
            batch.add(this.dataManagerFactoryProvider.getManagerFactory().forOAuthAccess().crud().deleteById(accessCodeHash).generateAndGetBoundStatement());
        });
        this.dataManagerFactoryProvider.getManagerFactory().forOAuthAuthorization().getNativeSession().execute(batch);
    }
}
