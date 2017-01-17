package com.entropyshift.overseer.oauth2.authorize;

import com.entropyshift.overseer.oauth2.exceptions.OAuthException;

/**
 * Created by chaitanya.m on 1/10/17.
 */
public interface IOAuthAuthorizationService
{
    OAuthAuthorizeResult authorize(OAuthAuthorizeRequest request) throws OAuthException;
}
