package com.entropyshift.overseer.auth;

/**
 * Created by chaitanya.m on 2/13/17.
 */
public class AuthResponse
{
    private final String requestId;

    public AuthResponse(final String requestId)
    {
       this.requestId = requestId;
    }

    public String getRequestId()
    {
        return requestId;
    }

}
