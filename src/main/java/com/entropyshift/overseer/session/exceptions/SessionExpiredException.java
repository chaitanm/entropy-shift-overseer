package com.entropyshift.overseer.session.exceptions;

/**
 * Created by chaitanya.m on 2/14/17.
 */
public class SessionExpiredException extends Exception
{
    public SessionExpiredException()
    {
        super();
    }

    public SessionExpiredException(String message)
    {
        super(message);
    }

    public SessionExpiredException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public SessionExpiredException(Throwable cause)
    {
        super(cause);
    }
}
