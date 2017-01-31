package com.entropyshift.overseer.oauth2.refresh;

import com.entropyshift.overseer.oauth2.OAuthSuccessResponse;

/**
 * Created by chaitanya.m on 1/31/17.
 */
public final class OAuthRefreshSuccessResponse extends OAuthSuccessResponse
{
    private final String accessToken;

    private final String refreshToken;

    public OAuthRefreshSuccessResponse(final String accessToken, final String refreshToken)
    {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken()
    {
        return accessToken;
    }

    public String getRefreshToken()
    {
        return refreshToken;
    }
}
