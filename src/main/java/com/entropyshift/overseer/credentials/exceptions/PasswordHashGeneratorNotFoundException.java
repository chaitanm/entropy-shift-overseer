package com.entropyshift.overseer.credentials.exceptions;

/**
 * Created by chaitanya.m on 2/9/17.
 */
public class PasswordHashGeneratorNotFoundException extends Exception
{
    private static final long serialVersionUID = 1879862057151941447L;

    public PasswordHashGeneratorNotFoundException()
    {
        super();
    }

    public PasswordHashGeneratorNotFoundException(String message)
    {
        super(message);
    }

    public PasswordHashGeneratorNotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public PasswordHashGeneratorNotFoundException(Throwable cause)
    {
        super(cause);
    }
}
