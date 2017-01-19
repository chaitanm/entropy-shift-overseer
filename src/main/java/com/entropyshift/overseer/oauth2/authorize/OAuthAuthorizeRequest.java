package com.entropyshift.overseer.oauth2.authorize;

import com.entropyshift.annotations.AllowedRegex;
import com.entropyshift.annotations.ParamName;
import com.entropyshift.annotations.Required;
import com.entropyshift.overseer.oauth2.OAuthRequest;
import com.entropyshift.overseer.oauth2.constants.OAuthParameters;

import javax.servlet.http.HttpServletRequest;
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

    @ParamName(OAuthParameters.USER_ID)
    @Required
    private final String userId;

    public OAuthAuthorizeRequest(final HttpServletRequest request)
    {
        super(UUID.fromString(request.getParameter(OAuthParameters.CLIENT_ID)));
        this.userId = request.getParameter(OAuthParameters.USER_ID);
        this.responseType = request.getParameter(OAuthParameters.RESPONSE_TYPE);
        this.redirectUri = request.getParameter(OAuthParameters.REDIRECT_URI);
        this.scope = request.getParameter(OAuthParameters.SCOPE);
        this.state = request.getParameter(OAuthParameters.STATE);
    }

    public OAuthAuthorizeRequest(final String responseType, final UUID clientId, final String userId, final String redirectUri,
                                 final String scope, final String state)
    {
        super(clientId);
        this.userId = userId;
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

    public String getUserId()
    {
        return userId;
    }
}
