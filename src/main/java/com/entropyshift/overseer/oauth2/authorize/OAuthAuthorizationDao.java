package com.entropyshift.overseer.oauth2.authorize;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.SimpleStatement;
import com.entropyshift.cassandra.IDataManagerFactoryProvider;

import java.util.List;
import java.util.UUID;

/**
 * Created by chaitanya.m on 1/18/17.
 */
public class OAuthAuthorizationDao implements IOAuthAuthorizationDao
{
    private IDataManagerFactoryProvider dataManagerFactoryProvider;

    public OAuthAuthorizationDao(IDataManagerFactoryProvider dataManagerFactoryProvider)
    {
       this.dataManagerFactoryProvider = dataManagerFactoryProvider;
    }

    @Override
    public void insertOrUpdate(OAuthAuthorization oAuthAuthorization)
    {
       this.dataManagerFactoryProvider.getManagerFactory().forOAuthAuthorization().crud()
               .insert(oAuthAuthorization).execute();
    }

    @Override
    public OAuthAuthorization getByAuthorizationCodeHash(byte[] authorizationCodeHash)
    {
       return this.dataManagerFactoryProvider.getManagerFactory().forOAuthAuthorization().crud()
               .findById(authorizationCodeHash).get();
    }

    @Override
    public void updateUserValidatedFlag(byte[] authorizationCodeHash, boolean flag)
    {
        this.dataManagerFactoryProvider.getManagerFactory().forOAuthAuthorization().dsl()
                .update().fromBaseTable().userValidated().Set(flag).where().authorizationCodeHash().equals(authorizationCodeHash);
    }

    @Override
    public List<OAuthAuthorization> getByUserId(String userId)
    {
        final SimpleStatement simpleStatement = new SimpleStatement("SELECT * FROM entropyshift.oauth_authorization WHERE user_id = :user_id");
        return this.dataManagerFactoryProvider.getManagerFactory().forOAuthAuthorization().raw()
        .typedQueryForSelect(simpleStatement, userId).getList();
    }

    @Override
    public List<OAuthAuthorization> getByClientId(UUID clientId)
    {
        final SimpleStatement simpleStatement = new SimpleStatement("SELECT * FROM entropyshift.oauth_authorization WHERE client_id = :client_id", clientId);
        return this.dataManagerFactoryProvider.getManagerFactory().forOAuthAuthorization().raw()
                .typedQueryForSelect(simpleStatement, clientId).getList();
    }

    @Override
    public void deleteByAuthorizationCodeHash(byte[] authorizationCodeHash)
    {
      this.dataManagerFactoryProvider.getManagerFactory().forOAuthAuthorization().crud()
              .deleteById(authorizationCodeHash).execute();
    }

    @Override
    public void bulkDelete(List<byte[]> authorizationCodeHashList)
    {
        final BatchStatement batch = new BatchStatement();
        authorizationCodeHashList.forEach(authorizationCodeHash -> {
            batch.add(this.dataManagerFactoryProvider.getManagerFactory().forOAuthAuthorization().crud().deleteById(authorizationCodeHash).generateAndGetBoundStatement());
        });
        this.dataManagerFactoryProvider.getManagerFactory().forOAuthAuthorization().getNativeSession().execute(batch);
    }

}
