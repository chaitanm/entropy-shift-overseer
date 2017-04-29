package com.entropyshift.overseer.auth.response;

import com.entropyshift.user.profile.UserInformation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by chaitanya.m on 2/13/17.
 */

@ApiModel(value = "AuthenticateUserResponse", description = "Response Model for Authenticate User API")
public class AuthenticateUserResponse extends AuthResponse
{
    @ApiModelProperty(value = "Status for validation of user authentication request")
    private final String status;

    @ApiModelProperty(value = "User Information")
    private final UserInformation userInformation;

    public AuthenticateUserResponse(final String requestId, final String status, final UserInformation userInformation)
    {
        super(requestId);
        this.status = status;
        this.userInformation = userInformation;
    }

    public String getStatus()
    {
        return status;
    }

    public UserInformation getUserInformation()
    {
        return userInformation;
    }

}
