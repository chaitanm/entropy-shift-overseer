package com.entropyshift.user.registration;

import com.entropyshift.RegexConstants;
import com.entropyshift.user.UserBaseRequest;
import com.entropyshift.user.profile.AddressInformation;
import com.entropyshift.user.profile.PhoneNumberInformation;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

/**
 * Created by chaitanya.m on 2/20/17.
 */
public class RegisterUserRequest extends UserBaseRequest
{
    @NotNull
    @Size(min = 6, max = 30)
    @Pattern(regexp = RegexConstants.USER_ID_REGEX)
    private String userId;

    @NotNull
    @Size(min = 8, max = 50)
    @Pattern(regexp = RegexConstants.PASSWORD_REGEX)
    private String password;

    @NotNull
    private String confirmPassword;

    @NotNull
    @Size(min = 1, max = 25)
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_REGEX)
    private String firstName;

    @Size(max = 25)
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_REGEX)
    private String middleName;

    @NotNull
    @Size(min = 1, max = 25)
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_REGEX)
    private String lastName;

    @NotNull
    @Email
    private String emailAddress;

    @Past
    private Date dateOfBirth;

    @NotNull
    @Size
    private Set<PhoneNumberInformation> phoneNumberInformationList;

    private Set<AddressInformation> addressInformationList;

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getMiddleName()
    {
        return middleName;
    }

    public void setMiddleName(String middleName)
    {
        this.middleName = middleName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public Date getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    public Set<PhoneNumberInformation> getPhoneNumberInformationList()
    {
        return phoneNumberInformationList;
    }

    public void setPhoneNumberInformationList(Set<PhoneNumberInformation> phoneNumberInformationList)
    {
        this.phoneNumberInformationList = phoneNumberInformationList;
    }

    public Set<AddressInformation> getAddressInformationList()
    {
        return addressInformationList;
    }

    public void setAddressInformationList(Set<AddressInformation> addressInformationList)
    {
        this.addressInformationList = addressInformationList;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getConfirmPassword()
    {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword)
    {
        this.confirmPassword = confirmPassword;
    }
}
