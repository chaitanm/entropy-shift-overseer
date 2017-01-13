package com.entropyshift.overseer.oauth2.authorize;

/**
 * Created by chaitanya.m on 1/10/17.
 */
public final class OAuthAuthorizeErrorResponse extends OAuthAuthorizeResponse
{
    private final String error;

    private final String errorDescription;

    private final String errorUri;

    private final String state;

    public OAuthAuthorizeErrorResponse(final String error, final String errorDescription
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
