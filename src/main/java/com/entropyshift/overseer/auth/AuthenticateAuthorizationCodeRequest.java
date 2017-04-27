package com.entropyshift.overseer.auth;

/**
 * Created by chaitanya.m on 4/24/17.
 */
public class AuthenticateAuthorizationCodeRequest extends AuthenticateUserRequest
{
    private String authorizationCode;

    public String getAuthorizationCode()
    {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode)
    {
        this.authorizationCode = authorizationCode;
    }
}
