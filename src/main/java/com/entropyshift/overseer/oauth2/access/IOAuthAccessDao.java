package com.entropyshift.overseer.oauth2.access;

import java.util.List;

/**
 * Created by chaitanya.m on 1/20/17.
 */
public interface IOAuthAccessDao
{
    void insertOrUpdate(OAuthAccess oAuthAccess);

    OAuthAccess getByAccessCodeHash(byte[] accessCodeHash);

    List<OAuthAccess> getByUserId(String userId);

    List<OAuthAccess> getByClientId(String clientId);

    void deleteByAccessCodeHash(byte[] accessCodeHash);

    void bulkDelete(List<byte[]> accessCodeHashList);
}
