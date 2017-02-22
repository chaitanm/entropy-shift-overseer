package com.entropyshift.overseer.credentials;

import com.entropyshift.overseer.credentials.exceptions.IncorrectPasswordException;
import com.entropyshift.overseer.credentials.exceptions.NewPasswordSameAsCurrentPasswordException;
import com.entropyshift.overseer.credentials.exceptions.PasswordHashGeneratorNotFoundException;
import com.entropyshift.overseer.credentials.exceptions.UserCredentialsNotFoundException;

import java.util.UUID;

/**
 * Created by chaitanya.m on 2/9/17.
 */
public interface ICredentialService
{
    UUID saveNewCredentials(final String username, final String password) throws PasswordHashGeneratorNotFoundException;

    void authenticateCredentials(final String username, final String password) throws UserCredentialsNotFoundException, PasswordHashGeneratorNotFoundException, IncorrectPasswordException;

    void changePassword(final String username, final String oldPassword, final String newPassword) throws NewPasswordSameAsCurrentPasswordException, UserCredentialsNotFoundException, PasswordHashGeneratorNotFoundException, IncorrectPasswordException;

    void resetPassword(final String username, final String newPassword) throws UserCredentialsNotFoundException, PasswordHashGeneratorNotFoundException;

    String getUuidFromUsername(final String username) throws UserCredentialsNotFoundException;
}
