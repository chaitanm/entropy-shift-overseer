package com.entropyshift.overseer.oauth2.authorize;

import info.archinnov.achilles.annotations.Column;
import info.archinnov.achilles.annotations.PartitionKey;
import info.archinnov.achilles.annotations.Table;

import java.util.UUID;

/**
 * Created by chaitanya.m on 1/10/17.
 */
@Table(keyspace = "entropyshift", table = "oauth_authorization")
public class OAuthAuthorization
{
    @PartitionKey
    @Column("authorization_code_hash")
    private byte[] authorizationCodeHash;

    @Column("client_id")
    private UUID clientId;

    @Column("user_id")
    private String userId;

    @Column("redirect_uri")
    private String redirectUri;

    @Column("scope")
    private String scope;

    @Column("client_state")
    private String clientState;

    @Column("expires")
    private long expires;

    @Column("access_token_hash")
    private byte[] accessTokenHash;

    @Column("refresh_token_hash")
    private byte[] refreshTokenHash;

    @Column("created_timestamp")
    private long createdTimestamp;

    @Column("client_validated")
    private boolean clientValidated;

    public byte[] getAuthorizationCodeHash()
    {
        return authorizationCodeHash;
    }

    public void setAuthorizationCodeHash(byte[] authorizationCodeHash)
    {
        this.authorizationCodeHash = authorizationCodeHash;
    }

    public UUID getClientId()
    {
        return clientId;
    }

    public void setClientId(UUID clientId)
    {
        this.clientId = clientId;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getRedirectUri()
    {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri)
    {
        this.redirectUri = redirectUri;
    }

    public String getScope()
    {
        return scope;
    }

    public void setScope(String scope)
    {
        this.scope = scope;
    }

    public String getClientState()
    {
        return clientState;
    }

    public void setClientState(String clientState)
    {
        this.clientState = clientState;
    }

    public long getExpires()
    {
        return expires;
    }

    public void setExpires(long expires)
    {
        this.expires = expires;
    }

    public byte[] getAccessTokenHash()
    {
        return accessTokenHash;
    }

    public void setAccessTokenHash(byte[] accessTokenHash)
    {
        this.accessTokenHash = accessTokenHash;
    }

    public byte[] getRefreshTokenHash()
    {
        return refreshTokenHash;
    }

    public void setRefreshTokenHash(byte[] refreshTokenHash)
    {
        this.refreshTokenHash = refreshTokenHash;
    }

    public long getCreatedTimestamp()
    {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(long createdTimestamp)
    {
        this.createdTimestamp = createdTimestamp;
    }

    public boolean isClientValidated()
    {
        return clientValidated;
    }

    public void setClientValidated(boolean clientValidated)
    {
        this.clientValidated = clientValidated;
    }
}
