package com.entropyshift.overseer.oauth2.authorize;

import com.entropyshift.overseer.oauth2.OAuthSuccessResponse;

/**
 * Created by chaitanya.m on 1/10/17.
 */
public final class OAuthAuthorizeSuccessResponse extends OAuthSuccessResponse
{
    private final String code;

    private final String state;

    public OAuthAuthorizeSuccessResponse(final String code, final String state)
    {
        this.code = code;
        this.state = state;
    }

    public String getCode()
    {
        return code;
    }

    public String getState()
    {
        return state;
    }
}
