package com.entropyshift.overseer.oauth2.access;

import com.entropyshift.overseer.oauth2.OAuthErrorResponse;

/**
 * Created by chaitanya.m on 1/20/17.
 */
public class OAuthAccessErrorResponse extends OAuthErrorResponse
{
    public OAuthAccessErrorResponse(String error, String errorDescription, String errorUri, String state)
    {
        super(error, errorDescription, errorUri, state);
    }
}
