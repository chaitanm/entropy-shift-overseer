package com.entropyshift.overseer.credentials.exceptions;

/**
 * Created by chaitanya.m on 2/9/17.
 */
public class UserCredentialsNotFoundException extends Exception
{
    private static final long serialVersionUID = -5679890346265342060L;

    public UserCredentialsNotFoundException()
    {
        super();
    }

    public UserCredentialsNotFoundException(String message)
    {
        super(message);
    }

    public UserCredentialsNotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public UserCredentialsNotFoundException(Throwable cause)
    {
        super(cause);
    }

}
