package com.entropyshift.overseer.oauth2.authorize;

import com.entropyshift.overseer.oauth2.OAuthResult;

import java.util.UUID;

/**
 * Created by chaitanya.m on 1/15/17.
 */
public class OAuthAuthorizeResult extends OAuthResult
{
    private final String userId;

    private final UUID clientId;

    private final String code;

    private final String clientState;

    private final long createdTimeStamp;

    public OAuthAuthorizeResult(final String userId, final UUID clientId
            , final String code, final String clientState, final long createdTimeStamp)
    {
        this.userId = userId;
        this.clientId = clientId;
        this.code = code;
        this.clientState = clientState;
        this.createdTimeStamp = createdTimeStamp;
    }

    public String getUserId()
    {
        return userId;
    }

    public UUID getClientId()
    {
        return clientId;
    }

    public String getCode()
    {
        return code;
    }

    public String getClientState()
    {
        return clientState;
    }

    public long getCreatedTimeStamp()
    {
        return createdTimeStamp;
    }
}
