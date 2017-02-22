package com.entropyshift.user;

/**
 * Created by chaitanya.m on 2/21/17.
 */
public abstract class UserBaseResponse
{
    private final String requestId;

    public UserBaseResponse(String requestId)
    {
        this.requestId = requestId;
    }

    public String getRequestId()
    {
        return requestId;
    }

}
