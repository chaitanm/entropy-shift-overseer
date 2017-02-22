package com.entropyshift.user.validation;

import com.entropyshift.annotations.ParamName;
import com.entropyshift.annotations.Required;
import com.entropyshift.user.UserBaseRequest;
import com.entropyshift.user.constants.UserValidationErrorCodeDescriptors;
import com.entropyshift.user.constants.UserValidationErrorCodesDescription;
import com.entropyshift.user.exceptions.UserValidationException;

import java.util.Arrays;

import static com.entropyshift.LambdaExceptionUtil.rethrowConsumer;

/**
 * Created by chaitanya.m on 2/21/17.
 */
public class RequiredFieldValidator<T extends UserBaseRequest> implements IUserValidator<T>
{
    @Override
    public void validate(T request) throws UserValidationException
    {
        Arrays.stream(request.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Required.class))
                .forEach(rethrowConsumer(field -> {
                    field.setAccessible(true);
                    Object value = field.get(request);
                    if(value == null || (value instanceof String && ((String)value).trim().equals(""))){
                        throw new UserValidationException(UserValidationErrorCodeDescriptors.INVALID_REQUEST_MISSING_PARAMETER
                                , UserValidationErrorCodesDescription.getErrorDescription(UserValidationErrorCodeDescriptors.INVALID_REQUEST_MISSING_PARAMETER
                                , field.isAnnotationPresent(ParamName.class)? field.getAnnotation(ParamName.class).value(): field.getName()));
                    }
                }));
    }
}
