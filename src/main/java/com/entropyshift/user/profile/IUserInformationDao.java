package com.entropyshift.user.profile;

/**
 * Created by chaitanya.m on 2/20/17.
 */
public interface IUserInformationDao
{
    void insertOrUpdate(UserInformation userInformation);

    UserInformation getByUserId(String userId);

    UserInformation getByEmailAddress(String emailAddress);

    void deleteByUserId(String userId);

}
