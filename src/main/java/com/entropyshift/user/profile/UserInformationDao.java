package com.entropyshift.user.profile;

import com.datastax.driver.core.SimpleStatement;
import com.entropyshift.cassandra.IDataManagerFactoryProvider;

import java.util.List;

/**
 * Created by chaitanya.m on 2/20/17.
 */
public class UserInformationDao implements IUserInformationDao
{
    private IDataManagerFactoryProvider dataManagerFactoryProvider;

    public UserInformationDao(IDataManagerFactoryProvider dataManagerFactoryProvider)
    {
        this.dataManagerFactoryProvider = dataManagerFactoryProvider;
    }

    @Override
    public void insertOrUpdate(UserInformation userInformation)
    {
       this.dataManagerFactoryProvider.getManagerFactory().forUserInformation().crud()
               .insert(userInformation).execute();
    }

    @Override
    public UserInformation getByUserId(String userId)
    {
        return this.dataManagerFactoryProvider.getManagerFactory().forUserInformation().crud()
                .findById(userId).get();
    }

    @Override
    public UserInformation getByEmailAddress(String emailAddress)
    {
        final SimpleStatement simpleStatement = new SimpleStatement("SELECT * FROM entropyshift.user_profile WHERE email_address = :email_address");
        List<UserInformation> result = this.dataManagerFactoryProvider.getManagerFactory().forUserInformation().raw()
                .typedQueryForSelect(simpleStatement, emailAddress).getList();
        return result.size() > 0 ? result.get(0) : null;
    }

    @Override
    public void deleteByUserId(String userId)
    {
       this.dataManagerFactoryProvider.getManagerFactory().forUserInformation().crud()
               .deleteById(userId);
    }
}
