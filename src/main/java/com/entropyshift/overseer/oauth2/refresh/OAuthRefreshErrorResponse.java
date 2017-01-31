package com.entropyshift.overseer.oauth2.refresh;

import com.entropyshift.overseer.oauth2.OAuthErrorResponse;

/**
 * Created by chaitanya.m on 1/31/17.
 */
public final class OAuthRefreshErrorResponse extends OAuthErrorResponse
{
    public OAuthRefreshErrorResponse(String error, String errorDescription, String errorUri, String state)
    {
        super(error, errorDescription, errorUri, state);
    }
}
