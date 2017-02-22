package com.entropyshift.user.validation;

import com.entropyshift.annotations.AllowedRegex;
import com.entropyshift.annotations.ParamName;
import com.entropyshift.user.UserBaseRequest;
import com.entropyshift.user.constants.UserValidationErrorCodeDescriptors;
import com.entropyshift.user.constants.UserValidationErrorCodesDescription;
import com.entropyshift.user.exceptions.UserValidationException;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.entropyshift.LambdaExceptionUtil.rethrowConsumer;

/**
 * Created by chaitanya.m on 2/21/17.
 */
public class AllowedRegexValidator<T extends UserBaseRequest> implements IUserValidator<T>
{
    @Override
    public void validate(T request) throws UserValidationException
    {
        Arrays.stream(request.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(AllowedRegex.class))
                .forEach(rethrowConsumer(field ->
                {
                    field.setAccessible(true);
                    Object value = field.get(request);
                    if (value != null && value instanceof String)
                    {
                        String regex = field.getAnnotation(AllowedRegex.class).value();
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(value.toString());
                        if (!matcher.find())
                        {
                            throw new UserValidationException(UserValidationErrorCodeDescriptors.INVALID_REQUEST_INVALID_PARAMETER_VALUE
                                    , UserValidationErrorCodesDescription.getErrorDescription(UserValidationErrorCodeDescriptors.INVALID_REQUEST_INVALID_PARAMETER_VALUE
                                    , value, field.isAnnotationPresent(ParamName.class) ? field.getAnnotation(ParamName.class).value() : field.getName()));
                        }
                    }
                }));
    }
}
