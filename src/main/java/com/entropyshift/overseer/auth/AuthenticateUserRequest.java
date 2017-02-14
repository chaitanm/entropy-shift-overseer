package com.entropyshift.overseer.auth;

/**
 * Created by chaitanya.m on 2/13/17.
 */
public class AuthenticateUserRequest extends AuthRequest
{
    private String username;

    private String password;

    public String getUsername()

    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
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
