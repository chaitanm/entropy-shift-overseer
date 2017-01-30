package com.entropyshift.overseer.oauth2;

/**
 * Created by chaitanya.m on 1/20/17.
 */
public abstract class OAuthErrorResponse extends OAuthResponse
{
    private final String error;

    private final String errorDescription;

    private final String errorUri;

    private final String state;

    public OAuthErrorResponse(final String error, final String errorDescription
            , final String errorUri, final String state)
    {
        this.error = error;
        this.errorDescription = errorDescription;
        this.errorUri = errorUri;
        this.state = state;
    }

    public String getError()
    {
        return error;
    }

    public String getErrorDescription()
    {
        return errorDescription;
    }

    public String getErrorUri()
    {
        return errorUri;
    }

    public String getState()
    {
        return state;
    }
}
