package com.entropyshift.overseer.oauth2.refresh;

import com.entropyshift.annotations.AllowedRegex;
import com.entropyshift.annotations.ParamName;
import com.entropyshift.annotations.Required;
import com.entropyshift.overseer.oauth2.OAuthRequest;
import com.entropyshift.overseer.oauth2.constants.OAuthParameters;

import java.util.UUID;

/**
 * Created by chaitanya.m on 1/31/17.
 */
public class OAuthRefreshRequest extends OAuthRequest
{
    @ParamName(OAuthParameters.GRANT_TYPE)
    @Required
    @AllowedRegex("^(?i)(refresh_token)$")
    private final String grantType;

    @ParamName(OAuthParameters.REFRESH_TOKEN)
    @Required
    private final String refreshToken;

    public OAuthRefreshRequest(final UUID clientId, final String userId, final String grantType, final String refreshToken )
    {
        super(clientId, userId);
        this.grantType = grantType;
        this.refreshToken = refreshToken;
    }

    public String getGrantType()
    {
        return grantType;
    }

    public String getRefreshToken()
    {
        return refreshToken;
    }
}
