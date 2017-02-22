package com.entropyshift.user.registration;

import com.entropyshift.user.UserBaseResponse;

/**
 * Created by chaitanya.m on 2/21/17.
 */
public class RegisterUserResponse extends UserBaseResponse
{
    private final int statusCode;

    public RegisterUserResponse(String requestId, int statusCode)
    {
        super(requestId);
        this.statusCode = statusCode;
    }

    public int getStatusCode()
    {
        return statusCode;
    }
}
