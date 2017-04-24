package com.entropyshift.user;

/**
 * Created by chaitanya.m on 2/21/17.
 */

public abstract class UserBaseRequest
{
    private String requestId;

    public String getRequestId()
    {
        return requestId;
    }

    public void setRequestId(String requestId)
    {
        this.requestId = requestId;
    }
}
