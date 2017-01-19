package com.entropyshift.overseer.oauth2.validation;

import com.entropyshift.annotations.AllowedRegex;
import com.entropyshift.annotations.ParamName;
import com.entropyshift.overseer.oauth2.OAuthRequest;
import com.entropyshift.overseer.oauth2.constants.OAuthErrorCodeDescriptors;
import com.entropyshift.overseer.oauth2.constants.OAuthErrorCodesDescription;
import com.entropyshift.overseer.oauth2.exceptions.OAuthException;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.entropyshift.LambdaExceptionUtil.rethrowConsumer;

/**
 * Created by chaitanya.m on 1/11/17.
 */
public class AllowedRegexValidator<T extends OAuthRequest> implements IOAuthValidator<T>
{
    private final OAuthErrorCodesDescription errorCodesDescription;

    public AllowedRegexValidator()
    {
        errorCodesDescription = new OAuthErrorCodesDescription();
    }

    @Override
    public void validate(T request) throws OAuthException
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
                            throw new OAuthException(OAuthErrorCodeDescriptors.INVALID_REQUEST_INVALID_PARAMETER_VALUE
                                    , errorCodesDescription.getErrorDescription(OAuthErrorCodeDescriptors.INVALID_REQUEST_INVALID_PARAMETER_VALUE
                                    , value, field.isAnnotationPresent(ParamName.class) ? field.getAnnotation(ParamName.class).value() : field.getName()));
                        }
                    }
                }));
    }
}
