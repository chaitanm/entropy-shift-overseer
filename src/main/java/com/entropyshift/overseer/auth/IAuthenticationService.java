package com.entropyshift.overseer.auth;

import com.entropyshift.overseer.auth.request.AuthenticateUserRequest;
import com.entropyshift.overseer.auth.result.AuthenticateUserResult;
import com.entropyshift.overseer.credentials.exceptions.IncorrectPasswordException;
import com.entropyshift.overseer.credentials.exceptions.PasswordHashGeneratorNotFoundException;
import com.entropyshift.overseer.credentials.exceptions.UserCredentialsNotFoundException;
import com.entropyshift.overseer.session.exceptions.SessionExpiredException;
import com.entropyshift.overseer.session.exceptions.SessionNotFoundException;

import java.security.NoSuchAlgorithmException;

/**
 * Created by chaitanya.m on 4/28/17.
 */
public interface IAuthenticationService
{
    AuthenticateUserResult authenticate(AuthenticateUserRequest request) throws PasswordHashGeneratorNotFoundException, IncorrectPasswordException, UserCredentialsNotFoundException;

    void authenticateAuthorizationCode(String requestId, String authorizationCode
            , String username, String password) throws PasswordHashGeneratorNotFoundException,
            IncorrectPasswordException, UserCredentialsNotFoundException, NoSuchAlgorithmException;

    void keepSessionAlive(String sessionKey) throws SessionExpiredException, SessionNotFoundException;

    void logout(String sessionKey);

    void deleteUserSessions(String userId);


}
