package com.entropyshift.overseer.oauth2.validation;

import com.entropyshift.overseer.oauth2.OAuthRequest;
import com.entropyshift.overseer.oauth2.exceptions.OAuthException;

/**
 * Created by chaitanya.m on 1/12/17.
 */
public class ScopeValidator<T extends OAuthRequest> implements IOAuthValidator<T>
{
    @Override
    public void validate(T request) throws OAuthException
    {

    }
}
