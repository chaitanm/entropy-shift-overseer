package com.entropyshift.overseer.oauth2.refresh;

import com.entropyshift.overseer.oauth2.OAuthResult;

import java.util.UUID;

/**
 * Created by chaitanya.m on 1/31/17.
 */
public class OAuthRefreshResult extends OAuthResult
{
    private final String userId;

    private final UUID clientId;

    private final long createdTimestamp;

    private final String nextAccessToken;

    private final String nextRefreshToken;

    public OAuthRefreshResult(final String userId, final UUID clientId, final long createdTimestamp
            , final String nextAccessToken, final String nextRefreshToken)
    {
        this.userId = userId;
        this.clientId = clientId;
        this.createdTimestamp = createdTimestamp;
        this.nextAccessToken = nextAccessToken;
        this.nextRefreshToken = nextRefreshToken;
    }

    public String getUserId()
    {
        return userId;
    }

    public UUID getClientId()
    {
        return clientId;
    }

    public long getCreatedTimestamp()
    {
        return createdTimestamp;
    }

    public String getNextAccessToken()
    {
        return nextAccessToken;
    }

    public String getNextRefreshToken()
    {
        return nextRefreshToken;
    }
}
