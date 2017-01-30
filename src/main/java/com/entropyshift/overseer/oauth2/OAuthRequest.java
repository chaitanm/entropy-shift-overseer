
package com.entropyshift.overseer.oauth2;

import com.entropyshift.annotations.ParamName;
import com.entropyshift.annotations.Required;
import com.entropyshift.overseer.oauth2.constants.OAuthParameters;

import java.util.UUID;


/**
 * Created by chaitanya.m on 1/10/17.
 */
public abstract class OAuthRequest
{
    @Required
    @ParamName(OAuthParameters.CLIENT_ID)
    private final UUID clientId;

    @Required
    @ParamName(OAuthParameters.USER_ID)
    private final String userId;

    public OAuthRequest(final UUID clientId, final String userId)
    {
        this.clientId = clientId;
        this.userId = userId;
    }

    public UUID getClientId()
    {
        return clientId;
    }

    public String getUserId()
    {
        return userId;
    }
}
