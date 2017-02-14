package com.entropyshift.overseer.auth;

/**
 * Created by chaitanya.m on 2/13/17.
 */
public class AuthenticateUserResponse extends AuthResponse
{
    private final String statusCode;

    public AuthenticateUserResponse(final String requestId, final String statusCode)
    {
        super(requestId);
        this.statusCode = statusCode;
    }

    public String getStatusCode()
    {
        return statusCode;
    }

}
