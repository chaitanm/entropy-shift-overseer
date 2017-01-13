package com.entropyshift.overseer.oauth2.validation;

import com.entropyshift.overseer.oauth2.OAuthRequest;
import com.entropyshift.overseer.oauth2.exceptions.OAuthException;

/**
 * Created by chaitanya.m on 1/10/17.
 */
public interface IOAuthValidator<T extends OAuthRequest>
{
    void validate(T request) throws OAuthException;
}
