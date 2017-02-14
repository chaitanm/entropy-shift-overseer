package com.entropyshift.rest;

import com.entropyshift.overseer.auth.AuthStatusCodes;
import com.entropyshift.overseer.auth.AuthenticateUserRequest;
import com.entropyshift.overseer.auth.AuthenticateUserResponse;
import com.entropyshift.overseer.credentials.ICredentialService;
import com.entropyshift.overseer.credentials.exceptions.IncorrectPasswordException;
import com.entropyshift.overseer.credentials.exceptions.UserCredentialsNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by chaitanya.m on 2/9/17.
 */
public class AuthenticationController
{
    @Autowired
    private ICredentialService credentialService;

    public AuthenticateUserResponse authenticate(AuthenticateUserRequest request)
    {
        String requestId = request.getRequestId();
        try
        {
            credentialService.authenticateCredentials(request.getUsername(), request.getPassword());

        }
        catch (UserCredentialsNotFoundException e)
        {
            return new AuthenticateUserResponse(requestId, AuthStatusCodes.USER_NOT_FOUND);
        }
        catch (IncorrectPasswordException e)
        {
            return new AuthenticateUserResponse(requestId, AuthStatusCodes.INCORRECT_PASSWORD);
        }
        catch (Exception e)
        {
            return new AuthenticateUserResponse(requestId, AuthStatusCodes.SERVER_ERROR);
        }
        return new AuthenticateUserResponse(requestId, AuthStatusCodes.SUCCESS);
    }

}
