package com.entropyshift.overseer.oauth2.authorize;

import com.entropyshift.overseer.oauth2.exceptions.OAuthException;
import com.entropyshift.overseer.oauth2.validation.AllowedRegexValidator;
import com.entropyshift.overseer.oauth2.validation.IOAuthValidator;
import com.entropyshift.overseer.oauth2.validation.RequiredFieldValidator;
import com.entropyshift.overseer.oauth2.validation.ScopeValidator;


import java.util.ArrayList;
import java.util.List;

import static com.entropyshift.LambdaExceptionUtil.rethrowConsumer;

/**
 * Created by chaitanya.m on 1/10/17.
 */
public class OAuthAuthorizationService implements IOAuthAuthorizationService
{
    private List<IOAuthValidator<OAuthAuthorizeRequest>> validators;

    public OAuthAuthorizationService()
    {
        validators = new ArrayList<>();
        validators.add(new RequiredFieldValidator<>());
        validators.add(new AllowedRegexValidator<>());
        validators.add(new ScopeValidator<>());
    }

    @Override
    public OAuthAuthorizeSuccessResponse authorize(OAuthAuthorizeRequest request) throws OAuthException
    {
        validators.forEach(rethrowConsumer(validator -> validator.validate(request)));
        return null;
    }

}
