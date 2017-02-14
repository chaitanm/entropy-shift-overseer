package com.entropyshift.overseer.credentials.exceptions;

/**
 * Created by chaitanya.m on 2/9/17.
 */
public class NewPasswordSameAsCurrentPasswordException extends Exception
{
    private static final long serialVersionUID = -3056028546236045371L;

    public NewPasswordSameAsCurrentPasswordException()
    {
        super();
    }

    public NewPasswordSameAsCurrentPasswordException(String message)
    {
        super(message);
    }

    public NewPasswordSameAsCurrentPasswordException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public NewPasswordSameAsCurrentPasswordException(Throwable cause)
    {
        super(cause);
    }
}
