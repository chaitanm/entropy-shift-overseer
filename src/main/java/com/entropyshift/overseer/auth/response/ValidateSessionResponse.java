package com.entropyshift.overseer.auth.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by chaitanya.m on 4/27/17.
 */
@ApiModel
public class ValidateSessionResponse extends AuthResponse
{
    public ValidateSessionResponse(final String requestId, final String userId, final String scope)
    {
        super(requestId);
        this.userId = userId;
        this.scope = scope;
    }

    @ApiModelProperty(value = "User Id for the Session")
    private String userId;

    @ApiModelProperty(value = "Scope of the Session.")
    private String scope;

    public String getUserId()
    {
        return userId;
    }

    public String getScope()
    {
        return scope;
    }
}
