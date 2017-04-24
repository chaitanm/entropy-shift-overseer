package com.entropyshift.overseer.oauth2.refresh;

import info.archinnov.achilles.annotations.Column;
import info.archinnov.achilles.annotations.PartitionKey;
import info.archinnov.achilles.annotations.Table;

import java.util.UUID;

/**
 * Created by chaitanya.m on 1/10/17.
 */
@Table(keyspace = "overseer", table = "oauth_refresh")
public class OAuthRefresh
{
    @PartitionKey
    @Column("refresh_token_hash")
    private byte[] refreshTokenHash;

    @Column("access_token_hash")
    private byte[] accessTokenHash;

    @Column("client_id")
    private UUID clientId;

    @Column("user_id")
    private String userId;

    @Column("scope")
    private String scope;

    @Column("client_state")
    private String clientState;

    @Column("expires")
    private long expires;

    @Column("issued_next_access_token_hash")
    private byte[] issuedNextAccessTokenHash;

    @Column("issued_next_refresh_token_hash")
    private byte[] issuedNextRefreshTokenHash;

    @Column("created_timestamp")
    private long createdTimestamp;

    public byte[] getRefreshTokenHash()
    {
        return refreshTokenHash;
    }

    public void setRefreshTokenHash(byte[] refreshTokenHash)
    {
        this.refreshTokenHash = refreshTokenHash;
    }

    public byte[] getAccessTokenHash()
    {
        return accessTokenHash;
    }

    public void setAccessTokenHash(byte[] accessTokenHash)
    {
        this.accessTokenHash = accessTokenHash;
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

    public byte[] getIssuedNextAccessTokenHash()
    {
        return issuedNextAccessTokenHash;
    }

    public void setIssuedNextAccessTokenHash(byte[] issuedNextAccessTokenHash)
    {
        this.issuedNextAccessTokenHash = issuedNextAccessTokenHash;
    }

    public byte[] getIssuedNextRefreshTokenHash()
    {
        return issuedNextRefreshTokenHash;
    }

    public void setIssuedNextRefreshTokenHash(byte[] issuedNextRefreshTokenHash)
    {
        this.issuedNextRefreshTokenHash = issuedNextRefreshTokenHash;
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
