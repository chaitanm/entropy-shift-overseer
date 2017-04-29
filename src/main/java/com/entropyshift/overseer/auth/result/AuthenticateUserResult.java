package com.entropyshift.overseer.auth.result;

import com.entropyshift.overseer.session.CreateSessionResult;
import com.entropyshift.user.profile.UserInformation;

/**
 * Created by chaitanya.m on 4/28/17.
 */
public class AuthenticateUserResult
{
    private final String requestId;

    private final String status;

    private final UserInformation userInformation;

    private final CreateSessionResult createSessionResult;

    public AuthenticateUserResult(final String requestId, final String status, final UserInformation userInformation
    , final CreateSessionResult createSessionResult)
    {
        this.requestId = requestId;
        this.status = status;
        this.userInformation = userInformation;
        this.createSessionResult = createSessionResult;
    }

    public AuthenticateUserResult(final String requestId, final String status, final UserInformation userInformation)
    {
        this(requestId, status, userInformation, null);
    }

    public AuthenticateUserResult(final String requestId, final String status)
    {
        this(requestId, status, null, null);
    }

    public String getRequestId()
    {
        return requestId;
    }

    public String getStatus()
    {
        return status;
    }

    public UserInformation getUserInformation()
    {
        return userInformation;
    }

    public CreateSessionResult getCreateSessionResult()
    {
        return createSessionResult;
    }
}
