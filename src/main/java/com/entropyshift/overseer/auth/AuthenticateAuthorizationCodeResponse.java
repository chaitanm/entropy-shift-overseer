package com.entropyshift.overseer.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by chaitanya.m on 4/24/17.
 */
@ApiModel(value = "AuthenticateAuthorizationCodeResponse", description = "Response Model for Authenticate OAuth Authorization Code API")
public class AuthenticateAuthorizationCodeResponse extends AuthResponse
{
    @ApiModelProperty(value = "Status for authentication of oauth authorization code")
    private final String status;

    public AuthenticateAuthorizationCodeResponse(final String requestId, final String status)
    {
        super(requestId);
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }


}
