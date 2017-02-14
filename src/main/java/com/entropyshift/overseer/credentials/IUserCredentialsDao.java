package com.entropyshift.overseer.credentials;

import com.entropyshift.overseer.credentials.exceptions.UserCredentialsNotFoundException;

/**
 * Created by chaitanya.m on 2/9/17.
 */
public interface IUserCredentialsDao
{
    public UserCredentials getUserCredentialsByUsername(final String username) throws UserCredentialsNotFoundException;

    public void insertUserCredentials(UserCredentials userCredentials);

    public void updateUserCredentials(UserCredentials userCredentials);

    public void deleteUserCredentialsByUsername(final String username) throws UserCredentialsNotFoundException;
}
