
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


    public OAuthRequest(final UUID clientId)
    {
        this.clientId = clientId;
    }

    public UUID getClientId()
    {
        return clientId;
    }

}
