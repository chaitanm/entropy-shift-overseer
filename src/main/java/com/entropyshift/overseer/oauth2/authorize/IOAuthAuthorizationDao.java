package com.entropyshift.overseer.oauth2.authorize;

import java.util.List;
import java.util.UUID;

/**
 * Created by chaitanya.m on 1/18/17.
 */
public interface IOAuthAuthorizationDao
{
    void insert(OAuthAuthorization oAuthAuthorization);

    OAuthAuthorization getByAuthorizationCodeHash(byte[] authorizationCodeHash);

    List<OAuthAuthorization> getByUserId(String userId);

    List<OAuthAuthorization> getByClientId(UUID clientId);

    void updateClientValidatedFlag(byte[] authorizationCodeHash, boolean flag);

    void deleteByAuthorizationCodeHash(byte[] authorizationCodeHash);

    void bulkDelete(List<byte[]> authorizationCodeHashList);

}
