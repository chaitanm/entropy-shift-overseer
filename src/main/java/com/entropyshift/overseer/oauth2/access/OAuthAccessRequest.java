package com.entropyshift.overseer.oauth2.access;

import com.entropyshift.annotations.AllowedRegex;
import com.entropyshift.annotations.Required;
import com.entropyshift.overseer.oauth2.OAuthRequest;

import java.util.UUID;

/**
 * Created by chaitanya.m on 1/20/17.
 */
public class OAuthAccessRequest extends OAuthRequest
{
    @Required
    @AllowedRegex("^(?i)(authorization_code)$")
    private final String grantType;

    @Required
    private final String redirectUri;

    @Required
    private final String authorizationCode;

    public OAuthAccessRequest(final String grantType, final UUID clientId, final String userId
            , final String redirectUri, final String authorizationCode)
    {
        super(clientId, userId);
        this.grantType = grantType;
        this.redirectUri = redirectUri;
        this.authorizationCode = authorizationCode;
    }

    public String getGrantType()
    {
        return grantType;
    }

    public String getRedirectUri()
    {
        return redirectUri;
    }

    public String getAuthorizationCode()
    {
        return authorizationCode;
    }
}
