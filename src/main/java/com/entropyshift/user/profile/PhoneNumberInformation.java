package com.entropyshift.user.profile;

import com.entropyshift.RegexConstants;
import info.archinnov.achilles.annotations.Column;
import info.archinnov.achilles.annotations.Enumerated;
import info.archinnov.achilles.annotations.UDT;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by chaitanya.m on 2/20/17.
 */

@UDT(keyspace = "entropyshift",name = "phone_number")
public class PhoneNumberInformation
{
    @Column("country_code")
    @NotNull
    @Size(min = 1, max = 3)
    @Pattern(regexp = RegexConstants.NUMERIC_REGEX)
    private String countryCode;

    @Column("national_identification_number")
    @NotNull
    @Size(min = 1, max = 16)
    @Pattern(regexp = RegexConstants.NUMERIC_REGEX)
    private String nationalIdentificationNumber;

    @Column("category")
    @Enumerated(Enumerated.Encoding.NAME)
    private PhoneNumberCategory phoneNumberCategory;

    public String getCountryCode()
    {
        return countryCode;
    }

    public void setCountryCode(String countryCode)
    {
        this.countryCode = countryCode;
    }

    public String getNationalIdentificationNumber()
    {
        return nationalIdentificationNumber;
    }

    public void setNationalIdentificationNumber(String nationalIdentificationNumber)
    {
        this.nationalIdentificationNumber = nationalIdentificationNumber;
    }

    public PhoneNumberCategory getPhoneNumberCategory()
    {
        return phoneNumberCategory;
    }

    public void setPhoneNumberCategory(PhoneNumberCategory phoneNumberCategory)
    {
        this.phoneNumberCategory = phoneNumberCategory;
    }
}
