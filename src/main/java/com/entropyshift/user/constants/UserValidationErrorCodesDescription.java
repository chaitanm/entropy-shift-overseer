package com.entropyshift.user.constants;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by chaitanya.m on 2/21/17.
 */
public class UserValidationErrorCodesDescription
{
    private static final Map<UserValidationErrorCodeDescriptors, String> errorDescriptionLookup;

    static
    {
        errorDescriptionLookup = new HashMap<>();

        errorDescriptionLookup.put(UserValidationErrorCodeDescriptors.USER_ID_NOT_AVAILABLE_FOR_REGISTRATION
                , "Provided User Id is not available for registration : %s");
        errorDescriptionLookup.put(UserValidationErrorCodeDescriptors.EMAIL_ADDRESS_ALREADY_REGISTERED
                , "Provided Email Address already registered : %s");
        errorDescriptionLookup.put(UserValidationErrorCodeDescriptors.CONFIRM_PASSWORD_DOES_NOT_MATCH
                , "Password and Confirm Password does not match.");
    }

    public static String getErrorDescription(UserValidationErrorCodeDescriptors errorCodeDescriptor, Object... args)
    {
        return String.format(errorDescriptionLookup.get(errorCodeDescriptor), args);
    }
}
