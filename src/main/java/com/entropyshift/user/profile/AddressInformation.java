package com.entropyshift.user.profile;

import com.entropyshift.RegexConstants;
import com.entropyshift.user.constants.RequestValidationErrorMessages;
import info.archinnov.achilles.annotations.Column;
import info.archinnov.achilles.annotations.Enumerated;
import info.archinnov.achilles.annotations.UDT;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by chaitanya.m on 2/20/17.
 */
@UDT(keyspace = "entropyshift", name = "address")
public class AddressInformation
{
    @Column("line1")
    @NotNull(message = RequestValidationErrorMessages.ADDRESS_LINE1_NULL)
    @Size(max = 100, message = RequestValidationErrorMessages.ADDRESS_LINE_INVALID_SIZE)
    private String line1;

    @Column("line2")
    @Size(max = 100, message = RequestValidationErrorMessages.ADDRESS_LINE_INVALID_SIZE)
    private String line2;

    @Column("line3")
    @Size(max = 100, message = RequestValidationErrorMessages.ADDRESS_LINE_INVALID_SIZE)
    private String line3;

    @Column("postal")
    private String postal;

    @Column("province")
    private String province;

    @Column("city")
    @NotNull(message = RequestValidationErrorMessages.CITY_NULL)
    @Size(max = 100, message = RequestValidationErrorMessages.CITY_INVALID_SIZE)
    @Pattern(regexp = RegexConstants.CHARACTER_REGEX, message = RequestValidationErrorMessages.CITY_INVALID_REGEX)
    private String city;

    @Column("state")
    @NotNull(message = RequestValidationErrorMessages.STATE_NULL)
    @Size(max = 100, message = RequestValidationErrorMessages.STATE_INVALID_SIZE)
    @Pattern(regexp = RegexConstants.CHARACTER_REGEX, message = RequestValidationErrorMessages.STATE_INVALID_REGEX)
    private String state;

    @Column("country")
    @NotNull(message = RequestValidationErrorMessages.COUNTRY_NULL)
    @Size(max = 100, message = RequestValidationErrorMessages.COUNTRY_INVALID_SIZE)
    @Pattern(regexp = RegexConstants.CHARACTER_REGEX, message = RequestValidationErrorMessages.COUNTRY_INVALID_REGEX)
    private String country;

    @Column("zip_code")
    @NotNull(message = RequestValidationErrorMessages.ZIP_CODE_NULL)
    private int zipCode;

    @Column("zip_plus4")
    private int zipPlus4;

    @Column("attention_line1")
    @Size(max = 150)
    private String attentionLine1;

    @Column("attention_line2")
    @Size(max = 150)
    private String attentionLine2;

    @Column("address_category")
    @Enumerated
    private AddressCategory addressCategory;

    @Column("effective_date")
    private long effectiveDate;

    @Column("updated_date")
    private long updatedDate;

    @Column("termination_date")
    private long terminationDate;

    public String getLine1()
    {
        return line1;
    }

    public void setLine1(String line1)
    {
        this.line1 = line1;
    }

    public String getLine2()
    {
        return line2;
    }

    public void setLine2(String line2)
    {
        this.line2 = line2;
    }

    public String getLine3()
    {
        return line3;
    }

    public void setLine3(String line3)
    {
        this.line3 = line3;
    }

    public String getPostal()
    {
        return postal;
    }

    public void setPostal(String postal)
    {
        this.postal = postal;
    }

    public String getProvince()
    {
        return province;
    }

    public void setProvince(String province)
    {
        this.province = province;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public int getZipCode()
    {
        return zipCode;
    }

    public void setZipCode(int zipCode)
    {
        this.zipCode = zipCode;
    }

    public int getZipPlus4()
    {
        return zipPlus4;
    }

    public void setZipPlus4(int zipPlus4)
    {
        this.zipPlus4 = zipPlus4;
    }

    public String getAttentionLine1()
    {
        return attentionLine1;
    }

    public void setAttentionLine1(String attentionLine1)
    {
        this.attentionLine1 = attentionLine1;
    }

    public String getAttentionLine2()
    {
        return attentionLine2;
    }

    public void setAttentionLine2(String attentionLine2)
    {
        this.attentionLine2 = attentionLine2;
    }

    public AddressCategory getAddressCategory()
    {
        return addressCategory;
    }

    public void setAddressCategory(AddressCategory addressCategory)
    {
        this.addressCategory = addressCategory;
    }

    public long getEffectiveDate()
    {
        return effectiveDate;
    }

    public void setEffectiveDate(long effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }

    public long getUpdatedDate()
    {
        return updatedDate;
    }

    public void setUpdatedDate(long updatedDate)
    {
        this.updatedDate = updatedDate;
    }

    public long getTerminationDate()
    {
        return terminationDate;
    }

    public void setTerminationDate(long terminationDate)
    {
        this.terminationDate = terminationDate;
    }
}
