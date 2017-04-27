package com.entropyshift.overseer.session.exceptions;

/**
 * Created by chaitanya.m on 4/27/17.
 */
public class InvalidSessionException extends Exception
{
    public InvalidSessionException()
    {
        super();
    }

    public InvalidSessionException(String message)
    {
        super(message);
    }

    public InvalidSessionException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public InvalidSessionException(Throwable cause)
    {
        super(cause);
    }
}
