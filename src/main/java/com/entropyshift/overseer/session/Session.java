package com.entropyshift.overseer.session;

import info.archinnov.achilles.annotations.Column;
import info.archinnov.achilles.annotations.PartitionKey;
import info.archinnov.achilles.annotations.Table;

@Table(keyspace = "entropyshift", table = "session")
public class Session
{
    @PartitionKey
    @Column("session_key_hash")
    private byte[] sessionKeyHash;

    @Column("user_id")
    private String userId;

    @Column("ip_address")
    private String ipAddress;

    @Column("device_id")
    private String deviceId;

    @Column("browser_id")
    private String browserId;

    @Column("scope")
    private String scope;

    @Column("expires")
    private long expires;

    @Column("absolute_expiry")
    private long absoluteExpiry;

    @Column("created_timestamp")
    private long createdTimestamp;

    public byte[] getSessionKeyHash()
    {
        return sessionKeyHash;
    }

    public void setSessionKeyHash(byte[] sessionKeyHash)
    {
        this.sessionKeyHash = sessionKeyHash;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }


    public String getIpAddress()
    {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }

    public String getDeviceId()
    {
        return deviceId;
    }

    public void setDeviceId(String deviceId)
    {
        this.deviceId = deviceId;
    }

    public String getBrowserId()
    {
        return browserId;
    }

    public void setBrowserId(String browserId)
    {
        this.browserId = browserId;
    }

    public String getScope()
    {
        return scope;
    }

    public void setScope(String scope)
    {
        this.scope = scope;
    }

    public long getExpires()
    {
        return expires;
    }

    public void setExpires(long expires)
    {
        this.expires = expires;
    }

    public long getAbsoluteExpiry()
    {
        return absoluteExpiry;
    }

    public void setAbsoluteExpiry(long absoluteExpiry)
    {
        this.absoluteExpiry = absoluteExpiry;
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
