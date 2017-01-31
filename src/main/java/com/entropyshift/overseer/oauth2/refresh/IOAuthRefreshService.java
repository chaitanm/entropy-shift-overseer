package com.entropyshift.overseer.oauth2.refresh;

import com.entropyshift.overseer.oauth2.exceptions.OAuthException;

/**
 * Created by chaitanya.m on 1/31/17.
 */
public interface IOAuthRefreshService
{
    OAuthRefreshResult refresh(OAuthRefreshRequest request) throws OAuthException;
}
