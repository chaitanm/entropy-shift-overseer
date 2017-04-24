package com.entropyshift.user.profile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import info.archinnov.achilles.annotations.*;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * Created by chaitanya.m on 2/20/17.
 */
@Table(keyspace = "overseer",table = "user_profile")
public class UserInformation
{
    @Column("user_id")
    @PartitionKey
    private String userId;

    @Column("uuid")
    @JsonIgnore
    private UUID uuid;

    @Column("email_address")
    private String emailAddress;

    @Column("name_information")
    @Frozen
    private NameInformation nameInformation;

    @Column("address_information_list")
    private Set<@Frozen AddressInformation> addressInformationList;

    @Column("phone_number_information_list")
    private Set<@Frozen PhoneNumberInformation> phoneNumberInformationList;

    @Column("date_of_birth")
    private Date dateOfBirth;

    @Column("status")
    @Enumerated(Enumerated.Encoding.NAME)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserStatus status;

    @Column("registration_timestamp")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long registrationTimestamp;

    @Column("registration_device_id")
    @JsonIgnore
    private UUID registrationDeviceId;

    @Column("registration_browser_id")
    @JsonIgnore
    private UUID registrationBrowserId;

    @Column("registration_ip_address")
    @JsonIgnore
    private String registrationIpAddress;

    @Column("registration_geo_location")
    @Frozen
    @JsonIgnore
    private GeoLocationInformation registrationGeoLocationInformation;

    @Column("registered_devices")
    @JsonIgnore
    private Set<UUID> registeredDevices;

    @Column("password_rejection_count")
    @JsonIgnore
    private Integer passwordRejectionCount;

    @Column("last_password_update_timestamp")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long lastPasswordUpdateTimestamp;

    @Column("last_login_timestamp")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long lastLoginTimestamp;

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public NameInformation getNameInformation()
    {
        return nameInformation;
    }

    public void setNameInformation(NameInformation nameInformation)
    {
        this.nameInformation = nameInformation;
    }

    public Set<AddressInformation> getAddressInformationList()
    {
        return addressInformationList;
    }

    public void setAddressInformationList(Set<AddressInformation> addressInformationList)
    {
        this.addressInformationList = addressInformationList;
    }

    public Set<PhoneNumberInformation> getPhoneNumberInformationList()
    {
        return phoneNumberInformationList;
    }

    public void setPhoneNumberInformationList(Set<PhoneNumberInformation> phoneNumberInformationList)
    {
        this.phoneNumberInformationList = phoneNumberInformationList;
    }

    public Date getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    public UserStatus getStatus()
    {
        return status;
    }

    public void setStatus(UserStatus status)
    {
        this.status = status;
    }

    public long getRegistrationTimestamp()
    {
        return registrationTimestamp;
    }

    public void setRegistrationTimestamp(long registrationTimestamp)
    {
        this.registrationTimestamp = registrationTimestamp;
    }

    public UUID getRegistrationDeviceId()
    {
        return registrationDeviceId;
    }

    public void setRegistrationDeviceId(UUID registrationDeviceId)
    {
        this.registrationDeviceId = registrationDeviceId;
    }

    public UUID getRegistrationBrowserId()
    {
        return registrationBrowserId;
    }

    public void setRegistrationBrowserId(UUID registrationBrowserId)
    {
        this.registrationBrowserId = registrationBrowserId;
    }

    public String getRegistrationIpAddress()
    {
        return registrationIpAddress;
    }

    public void setRegistrationIpAddress(String registrationIpAddress)
    {
        this.registrationIpAddress = registrationIpAddress;
    }

    public GeoLocationInformation getRegistrationGeoLocationInformation()
    {
        return registrationGeoLocationInformation;
    }

    public void setRegistrationGeoLocationInformation(GeoLocationInformation registrationGeoLocationInformation)
    {
        this.registrationGeoLocationInformation = registrationGeoLocationInformation;
    }

    public Set<UUID> getRegisteredDevices()
    {
        return registeredDevices;
    }

    public void setRegisteredDevices(Set<UUID> registeredDevices)
    {
        this.registeredDevices = registeredDevices;
    }

    public Integer getPasswordRejectionCount()
    {
        return passwordRejectionCount;
    }

    public void setPasswordRejectionCount(Integer passwordRejectionCount)
    {
        this.passwordRejectionCount = passwordRejectionCount;
    }

    public Long getLastPasswordUpdateTimestamp()
    {
        return lastPasswordUpdateTimestamp;
    }

    public void setLastPasswordUpdateTimestamp(Long lastPasswordUpdateTimestamp)
    {
        this.lastPasswordUpdateTimestamp = lastPasswordUpdateTimestamp;
    }

    public Long getLastLoginTimestamp()
    {
        return lastLoginTimestamp;
    }

    public void setLastLoginTimestamp(Long lastLoginTimestamp)
    {
        this.lastLoginTimestamp = lastLoginTimestamp;
    }
}
