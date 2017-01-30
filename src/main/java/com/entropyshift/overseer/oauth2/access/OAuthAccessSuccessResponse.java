package com.entropyshift.overseer.oauth2.access;

import com.entropyshift.overseer.oauth2.OAuthSuccessResponse;

/**
 * Created by chaitanya.m on 1/20/17.
 */
public class OAuthAccessSuccessResponse extends OAuthSuccessResponse
{
    private final String accessToken;

    private final String tokenType;

    private final long expiresIn;

    private final String refreshToken;

    public OAuthAccessSuccessResponse(final String accessToken, final String tokenType, final long expiresIn, final String refreshToken)
    {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken()
    {
        return accessToken;
    }

    public String getTokenType()
    {
        return tokenType;
    }

    public long getExpiresIn()
    {
        return expiresIn;
    }

    public String getRefreshToken()
    {
        return refreshToken;
    }
}
