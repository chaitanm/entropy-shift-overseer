package com.entropyshift.overseer.oauth2.access;

import com.entropyshift.overseer.oauth2.OAuthResult;

import java.util.UUID;

/**
 * Created by chaitanya.m on 1/29/17.
 */
public class OAuthAccessResult extends OAuthResult
{
    private final String userId;

    private final UUID clientId;

    private final String accessToken;

    private final String tokenType;

    private final long createdTimestamp;

    private final String refreshToken;

    public OAuthAccessResult(final String userId, final UUID clientId, final String accessToken
    , final String tokenType, final long createdTimestamp, final String refreshToken)
    {
        this.userId = userId;
        this.clientId = clientId;
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.createdTimestamp = createdTimestamp;
        this.refreshToken = refreshToken;
    }

    public String getUserId()
    {
        return userId;
    }

    public UUID getClientId()
    {
        return clientId;
    }

    public String getAccessToken()
    {
        return accessToken;
    }

    public String getTokenType()
    {
        return tokenType;
    }

    public long getCreatedTimestamp()
    {
        return createdTimestamp;
    }

    public String getRefreshToken()
    {
        return refreshToken;
    }
}
