package com.entropyshift.user.exceptions;

import com.entropyshift.user.constants.UserValidationErrorCodeDescriptors;
import com.entropyshift.user.constants.UserValidationErrorCodesDescription;

/**
 * Created by chaitanya.m on 2/21/17.
 */
public class UserValidationException extends Exception
{
    private final UserValidationErrorCodeDescriptors errorCodeDescriptor;

    private final String message;

    public UserValidationException(final UserValidationErrorCodeDescriptors errorCodeDescriptor, Object... args)
    {
        this.errorCodeDescriptor = errorCodeDescriptor;
        this.message = UserValidationErrorCodesDescription.getErrorDescription(errorCodeDescriptor, args);
    }

    public UserValidationErrorCodeDescriptors getErrorCodeDescriptor()
    {
        return errorCodeDescriptor;
    }

    @Override
    public String getMessage()
    {
        return message;
    }
}
