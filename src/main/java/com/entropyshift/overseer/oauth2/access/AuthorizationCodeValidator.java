package com.entropyshift.overseer.oauth2.access;

import com.entropyshift.overseer.oauth2.OAuthRequest;
import com.entropyshift.overseer.oauth2.exceptions.OAuthException;
import com.entropyshift.overseer.oauth2.validation.IOAuthValidator;

/**
 * Created by chaitanya.m on 1/29/17.
 */
public class AuthorizationCodeValidator<T extends OAuthAccessRequest> implements IOAuthValidator
{
    @Override
    public void validate(OAuthRequest request) throws OAuthException
    {

    }
}
