package com.entropyshift.overseer.credentials.exceptions;

/**
 * Created by chaitanya.m on 2/9/17.
 */
public class IncorrectPasswordException extends Exception
{
    private static final long serialVersionUID = -2746532430201951203L;

    public IncorrectPasswordException()
    {
        super();
    }

    public IncorrectPasswordException(String message)
    {
        super(message);
    }

    public IncorrectPasswordException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public IncorrectPasswordException(Throwable cause)
    {
        super(cause);
    }
}
