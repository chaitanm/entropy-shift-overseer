package com.entropyshift.user.registration;

import com.entropyshift.user.UserBaseResponse;

/**
 * Created by chaitanya.m on 2/21/17.
 */
public class RegisterUserResponse extends UserBaseResponse
{
    private final String status;

    public RegisterUserResponse(final String requestId, final String status)
    {
        super(requestId);
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
}
