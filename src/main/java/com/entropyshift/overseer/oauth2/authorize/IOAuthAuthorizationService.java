package com.entropyshift.overseer.oauth2.authorize;

import com.entropyshift.overseer.oauth2.exceptions.OAuthException;
import info.archinnov.achilles.generated.manager.OAuthAccess_Manager;

/**
 * Created by chaitanya.m on 1/10/17.
 */
public interface IOAuthAuthorizationService
{
    OAuthAuthorizeSuccessResponse authorize(OAuthAuthorizeRequest request) throws OAuthException;
}
