package com.entropyshift.overseer.cassandra.beans;

import info.archinnov.achilles.annotations.Column;
import info.archinnov.achilles.annotations.PartitionKey;
import info.archinnov.achilles.annotations.Table;

import java.util.UUID;

/**
 * Created by chaitanya.m on 1/10/17.
 */
@Table(keyspace = "entropyshift", table = "oauth_access")
public class OAuthRefresh
{
    @PartitionKey
    @Column("refresh_token_hash")
    private String refreshTokenHash;

    @Column("access_token_hash")
    private String accessTokenHash;

    @Column("client_id")
    private UUID clientId;

    @Column("user_id")
    private UUID userId;

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

    public String getRefreshTokenHash()
    {
        return refreshTokenHash;
    }

    public void setRefreshTokenHash(String refreshTokenHash)
    {
        this.refreshTokenHash = refreshTokenHash;
    }

    public String getAccessTokenHash()
    {
        return accessTokenHash;
    }

    public void setAccessTokenHash(String accessTokenHash)
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

    public UUID getUserId()
    {
        return userId;
    }

    public void setUserId(UUID userId)
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
