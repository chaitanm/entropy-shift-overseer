package com.entropyshift.overseer.oauth2.authorize;

import com.entropyshift.annotations.AllowedRegex;
import com.entropyshift.annotations.ParamName;
import com.entropyshift.annotations.Required;
import com.entropyshift.overseer.oauth2.OAuthRequest;
import com.entropyshift.overseer.oauth2.constants.OAuthParameters;

import java.util.UUID;

/**
 * Created by chaitanya.m on 1/10/17.
 */
public class OAuthAuthorizeRequest extends OAuthRequest
{
    @ParamName(OAuthParameters.RESPONSE_TYPE)
    @Required
    @AllowedRegex("^(?i)(code)$")
    private final String responseType;

    @ParamName(OAuthParameters.REDIRECT_URI)
    private final String redirectUri;

    @ParamName(OAuthParameters.SCOPE)
    @AllowedRegex("[\\[a-zA-Z\\]\\s]+")
    private final String scope;

    @ParamName(OAuthParameters.STATE)
    @Required
    private final String state;

    public OAuthAuthorizeRequest(final String responseType, final UUID clientId, final String userId, final String redirectUri,
                                 final String scope, final String state)
    {
        super(clientId, userId);
        this.responseType = responseType;
        this.redirectUri = redirectUri;
        this.scope = scope;
        this.state = state;
    }


    public String getResponseType()
    {
        return responseType;
    }

    public String getRedirectUri()
    {
        return redirectUri;
    }

    public String getScope()
    {
        return scope;
    }

    public String getState()
    {
        return state;
    }

}
