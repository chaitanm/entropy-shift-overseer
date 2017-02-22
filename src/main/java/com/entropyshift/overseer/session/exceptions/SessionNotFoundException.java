package com.entropyshift.overseer.session.exceptions;

/**
 * Created by chaitanya.m on 2/14/17.
 */
public class SessionNotFoundException extends Exception
{
    public SessionNotFoundException()
    {
        super();
    }

    public SessionNotFoundException(String message)
    {
        super(message);
    }

    public SessionNotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public SessionNotFoundException(Throwable cause)
    {
        super(cause);
    }
}
