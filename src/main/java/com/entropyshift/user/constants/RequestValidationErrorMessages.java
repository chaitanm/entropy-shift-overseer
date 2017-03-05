package com.entropyshift.user.constants;

/**
 * Created by chaitanya.m on 2/26/17.
 */
public class RequestValidationErrorMessages
{
    public static final String USER_ID_NULL = "UserId cannot be empty.";
    public static final String USER_ID_INVALID_SIZE = "UserId should be between 6 and 30 characters.";
    public static final String USER_ID_INVALID_REGEX = "UserId should consist of alphabets or digits or special symbols - and _";

    public static final String PASSWORD_NULL = "Password cannot be empty.";
    public static final String PASSWORD_INVALID_SIZE = "Password should be between 8 and 50 characters.";
    public static final String PASSWORD_INVALID_REGEX = "Password should be combination of alphabets and digits. It can include special characters.";

    public static final String FIRST_NAME_NULL = "First name cannot be empty.";
    public static final String FIRST_NAME_INVALID_SIZE = "First name should be between 1 and 25 characters.";
    public static final String FIRST_NAME_INVALID_REGEX = "First name should contain only characters.";

    public static final String MIDDLE_NAME_INVALID_SIZE = "Middle name should be between 1 and 25 characters.";
    public static final String MIDDLE_NAME_INVALID_REGEX = "Middle name should contain only characters.";

    public static final String LAST_NAME_NULL = "Last name cannot be empty.";
    public static final String LAST_NAME_INVALID_SIZE = "Last name should be between 1 and 25 characters.";
    public static final String LAST_NAME_INVALID_REGEX = "Last name should contain only characters.";

    public static final String EMAIL_NULL = "Email cannot be empty.";
    public static final String EMAIL_INVALID_REGEX = "Email address is not in valid format.";

    public static final String DOB_INVALID = "Date of Birth is not valid.";

    public static final String PHONE_NUMBER_COUNTRY_CODE_NULL = "Country code cannot be empty.";
    public static final String PHONE_NUMBER_COUNTRY_CODE_INVALID_SIZE = "Country code is invalid.";
    public static final String PHONE_NUMBER_COUNTRY_CODE_INVALID_REGEX = "Country code is invalid.";

    public static final String PHONE_NUMBER_NATIONAL_IDENTIFICATION_NUMBER_NULL = "Phone Number cannot be empty.";
    public static final String PHONE_NUMBER_NATIONAL_IDENTIFICATION_NUMBER_INVALID_SIZE = "Phone Number not valid.";
    public static final String PHONE_NUMBER_NATIONAL_IDENTIFICATION_NUMBER_INVALID_REGEX = "Phone Number not valid.";

    public static final String ADDRESS_LINE1_NULL = "Line 1 of Address cannot be empty.";
    public static final String ADDRESS_LINE_INVALID_SIZE = "Address Line should not be more than 100 characters";

    public static final String CITY_NULL = "City cannot be empty.";
    public static final String CITY_INVALID_SIZE = "City name cannot be more than 100 characters.";
    public static final String CITY_INVALID_REGEX = "Invalid City name.";

    public static final String STATE_NULL = "State cannot be empty.";
    public static final String STATE_INVALID_SIZE = "State name cannot be more than 100 characters.";
    public static final String STATE_INVALID_REGEX = "Invalid State name.";

    public static final String COUNTRY_NULL = "Country cannot be empty.";
    public static final String COUNTRY_INVALID_SIZE = "Country name cannot be more than 100 characters.";
    public static final String COUNTRY_INVALID_REGEX = "Invalid Country name.";

    public static final String ZIP_CODE_NULL = "Zip code cannot be empty.";

}
