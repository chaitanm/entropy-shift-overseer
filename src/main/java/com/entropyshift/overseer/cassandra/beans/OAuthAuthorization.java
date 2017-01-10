package com.entropyshift.overseer.cassandra.beans;

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
    private String authorizationCodeHash;

    @Column("client_id")
    private UUID clientId;

    @Column("user_id")
    private UUID userId;

    @Column("redirect_uri")
    private String redirectUri;

    @Column("scope")
    private String scope;

    @Column("client_state")
    private String clientState;

    @Column("expires")
    private long expires;

    @Column("access_token_hash")
    private String accessTokenHash;

    @Column("refresh_token_hash")
    private String refreshTokenHash;

    @Column("created_timestamp")
    private long createdTimestamp;

    public String getAuthorizationCodeHash()
    {
        return authorizationCodeHash;
    }

    public void setAuthorizationCodeHash(String authorizationCodeHash)
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

    public UUID getUserId()
    {
        return userId;
    }

    public void setUserId(UUID userId)
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

    public String getAccessTokenHash()
    {
        return accessTokenHash;
    }

    public void setAccessTokenHash(String accessTokenHash)
    {
        this.accessTokenHash = accessTokenHash;
    }

    public String getRefreshTokenHash()
    {
        return refreshTokenHash;
    }

    public void setRefreshTokenHash(String refreshTokenHash)
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
}
