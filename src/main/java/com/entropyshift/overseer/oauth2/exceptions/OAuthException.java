package com.entropyshift.overseer.oauth2.exceptions;

import com.entropyshift.overseer.oauth2.constants.OAuthErrorCodeDescriptors;

/**
 * Created by chaitanya.m on 1/10/17.
 */
public class OAuthException extends Exception
{
    private final OAuthErrorCodeDescriptors errorCodeDescriptor;
    private final String message;

    public OAuthException(final OAuthErrorCodeDescriptors errorCodeDescriptor, final String message)
    {
        super();
        this.errorCodeDescriptor = errorCodeDescriptor;
        this.message = message;
    }

    public OAuthErrorCodeDescriptors getErrorCodeDescriptor()
    {
        return errorCodeDescriptor;
    }

    @Override
    public String getMessage()
    {
        return message;
    }
}
