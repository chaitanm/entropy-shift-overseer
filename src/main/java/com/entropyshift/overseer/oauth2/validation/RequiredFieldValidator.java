package com.entropyshift.overseer.oauth2.validation;

import com.entropyshift.annotations.ParamName;
import com.entropyshift.annotations.Required;
import com.entropyshift.overseer.oauth2.OAuthRequest;
import com.entropyshift.overseer.oauth2.constants.OAuthErrorCodeDescriptors;
import com.entropyshift.overseer.oauth2.constants.OAuthErrorCodesDescription;
import com.entropyshift.overseer.oauth2.exceptions.OAuthException;
import java.util.Arrays;

import static com.entropyshift.LambdaExceptionUtil.rethrowConsumer;

/**
 * Created by chaitanya.m on 1/10/17.
 */
public class RequiredFieldValidator<T extends OAuthRequest> implements IOAuthValidator
{
    private final OAuthErrorCodesDescription errorCodesDescription;

    public RequiredFieldValidator()
    {
        errorCodesDescription = new OAuthErrorCodesDescription();
    }

    @Override
    public void validate(OAuthRequest request) throws OAuthException
    {
        Arrays.stream(request.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Required.class))
                .forEach(rethrowConsumer(field -> {
                        field.setAccessible(true);
                        Object value = field.get(request);
                        if(value == null || (value instanceof String && ((String)value).trim().equals(""))){
                            throw new OAuthException(OAuthErrorCodeDescriptors.INVALID_REQUEST_MISSING_PARAMETER
                                    , errorCodesDescription.getErrorDescription(OAuthErrorCodeDescriptors.INVALID_REQUEST_MISSING_PARAMETER
                                    , field.isAnnotationPresent(ParamName.class)? field.getAnnotation(ParamName.class).value(): field.getName()));
                    }
                }));
    }
}
