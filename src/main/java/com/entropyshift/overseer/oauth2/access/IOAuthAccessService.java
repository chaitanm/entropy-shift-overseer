package com.entropyshift.overseer.oauth2.access;

import com.entropyshift.overseer.oauth2.exceptions.OAuthException;

/**
 * Created by chaitanya.m on 1/29/17.
 */
public interface IOAuthAccessService
{
    OAuthAccessResult grantAccess(OAuthAccessRequest request) throws OAuthException;
}
