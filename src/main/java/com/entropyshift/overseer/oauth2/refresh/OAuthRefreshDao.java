package com.entropyshift.overseer.oauth2.refresh;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.SimpleStatement;
import com.entropyshift.cassandra.IDataManagerFactoryProvider;

import java.util.List;
import java.util.UUID;

/**
 * Created by chaitanya.m on 1/30/17.
 */
public class OAuthRefreshDao implements IOAuthRefreshDao
{
    private IDataManagerFactoryProvider dataManagerFactoryProvider;

    public OAuthRefreshDao(IDataManagerFactoryProvider dataManagerFactoryProvider)
    {
        this.dataManagerFactoryProvider = dataManagerFactoryProvider;
    }

    @Override
    public void insert(OAuthRefresh oAuthRefresh)
    {
        this.dataManagerFactoryProvider.getManagerFactory().forOAuthRefresh().crud()
                .insert(oAuthRefresh).execute();
    }

    @Override
    public OAuthRefresh getByRefreshCodeHash(byte[] refreshCodeHash)
    {
        return this.dataManagerFactoryProvider.getManagerFactory().forOAuthRefresh().crud()
                .findById(refreshCodeHash).get();
    }

    @Override
    public OAuthRefresh getByAccessTokenHash(byte[] accessTokenHash)
    {
        final SimpleStatement simpleStatement = new SimpleStatement("SELECT * FROM entropyshift.oauth_refresh WHERE access_token_hash = :access_token_hash");
        List<OAuthRefresh> result = this.dataManagerFactoryProvider.getManagerFactory().forOAuthRefresh().raw()
                .typedQueryForSelect(simpleStatement, accessTokenHash).getList();
        return result.size() > 0 ? result.get(0) : null;
    }

    @Override
    public List<OAuthRefresh> getByUserId(String userId)
    {
        final SimpleStatement simpleStatement = new SimpleStatement("SELECT * FROM entropyshift.oauth_refresh WHERE user_id = :user_id");
        return this.dataManagerFactoryProvider.getManagerFactory().forOAuthRefresh().raw()
                .typedQueryForSelect(simpleStatement, userId).getList();
    }

    @Override
    public List<OAuthRefresh> getByClientId(UUID clientId)
    {
        final SimpleStatement simpleStatement = new SimpleStatement("SELECT * FROM entropyshift.oauth_refresh WHERE client_id = :client_id", clientId);
        return this.dataManagerFactoryProvider.getManagerFactory().forOAuthRefresh().raw()
                .typedQueryForSelect(simpleStatement, clientId).getList();
    }

    @Override
    public void deleteByRefreshCodeHash(byte[] refreshCodeHash)
    {
        this.dataManagerFactoryProvider.getManagerFactory().forOAuthRefresh().crud()
                .deleteById(refreshCodeHash).execute();
    }

    @Override
    public void bulkDelete(List<byte[]> refreshCodeHashList)
    {
        final BatchStatement batch = new BatchStatement();
        refreshCodeHashList.forEach(refreshCodeHash ->
        {
            batch.add(this.dataManagerFactoryProvider.getManagerFactory().forOAuthRefresh().crud().deleteById(refreshCodeHash).generateAndGetBoundStatement());
        });
        this.dataManagerFactoryProvider.getManagerFactory().forOAuthAuthorization().getNativeSession().execute(batch);
    }
}
