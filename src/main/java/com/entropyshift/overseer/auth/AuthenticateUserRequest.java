package com.entropyshift.overseer.auth;

/**
 * Created by chaitanya.m on 2/13/17.
 */
public class AuthenticateUserRequest extends AuthRequest
{
    private String userId;

    private String password;

    public String getUserId()

    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

}
