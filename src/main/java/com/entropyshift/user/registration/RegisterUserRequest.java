package com.entropyshift.user.registration;

import com.entropyshift.RegexConstants;
import com.entropyshift.user.UserBaseRequest;
import com.entropyshift.user.constants.RequestValidationErrorMessages;
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
    @NotNull(message = RequestValidationErrorMessages.USER_ID_NULL)
    @Size(min = 6, max = 30, message = RequestValidationErrorMessages.USER_ID_INVALID_SIZE)
    @Pattern(regexp = RegexConstants.USER_ID_REGEX, message = RequestValidationErrorMessages.USER_ID_INVALID_REGEX)
    private String userId;

    @NotNull(message = RequestValidationErrorMessages.PASSWORD_NULL)
    @Size(min = 8, max = 50, message = RequestValidationErrorMessages.PASSWORD_INVALID_SIZE)
    @Pattern(regexp = RegexConstants.PASSWORD_REGEX, message = RequestValidationErrorMessages.PASSWORD_INVALID_REGEX)
    private String password;

    @NotNull
    private String confirmPassword;

    @NotNull(message = RequestValidationErrorMessages.FIRST_NAME_NULL)
    @Size(min = 1, max = 25, message = RequestValidationErrorMessages.FIRST_NAME_INVALID_SIZE)
    @Pattern(regexp = RegexConstants.CHARACTER_REGEX, message = RequestValidationErrorMessages.FIRST_NAME_INVALID_REGEX)
    private String firstName;

    @Size(max = 25, message = RequestValidationErrorMessages.MIDDLE_NAME_INVALID_SIZE)
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_REGEX, message = RequestValidationErrorMessages.MIDDLE_NAME_INVALID_REGEX)
    private String middleName;

    @NotNull(message = RequestValidationErrorMessages.LAST_NAME_NULL)
    @Size(min = 1, max = 25, message = RequestValidationErrorMessages.LAST_NAME_INVALID_SIZE)
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_REGEX, message = RequestValidationErrorMessages.LAST_NAME_INVALID_REGEX)
    private String lastName;

    @NotNull(message = RequestValidationErrorMessages.EMAIL_NULL)
    @Email(message = RequestValidationErrorMessages.EMAIL_INVALID_REGEX)
    private String emailAddress;

    @Past(message = RequestValidationErrorMessages.DOB_INVALID)
    private Date dateOfBirth;

    @NotNull
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
