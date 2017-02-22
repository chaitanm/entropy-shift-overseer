package com.entropyshift.overseer.session;

/**
 * Created by chaitanya.m on 2/14/17.
 */
public class CreateSessionResult
{
    private final String sessionKey;

    private final long createdTimestamp;

    private final String scope;

    public CreateSessionResult(final String sessionKey, final long createdTimestamp, final String scope)
    {
        this.sessionKey = sessionKey;
        this.createdTimestamp = createdTimestamp;
        this.scope = scope;
    }

    public String getSessionKey()
    {
        return sessionKey;
    }

    public long getCreatedTimestamp()
    {
        return createdTimestamp;
    }

    public String getScope()
    {
        return scope;
    }
}
