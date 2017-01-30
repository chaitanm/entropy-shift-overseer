package com.entropyshift.overseer.oauth2.authorize;

import com.entropyshift.overseer.oauth2.OAuthErrorResponse;

/**
 * Created by chaitanya.m on 1/10/17.
 */
public final class OAuthAuthorizeErrorResponse extends OAuthErrorResponse
{

    public OAuthAuthorizeErrorResponse(final String error, final String errorDescription
            , final String errorUri, final String state)
    {
        super(error, errorDescription, errorUri, state);
    }

}
