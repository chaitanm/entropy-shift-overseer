package com.entropyshift.overseer.auth;

/**
 * Created by chaitanya.m on 2/13/17.
 */
public class AuthenticateUserResponse extends AuthResponse
{
    private final String status;

    public AuthenticateUserResponse(final String requestId, final String status)
    {
        super(requestId);
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

}
