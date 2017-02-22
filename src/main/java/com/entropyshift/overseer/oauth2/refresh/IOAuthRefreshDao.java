package com.entropyshift.overseer.oauth2.refresh;

import java.util.List;
import java.util.UUID;

/**
 * Created by chaitanya.m on 1/29/17.
 */
public interface IOAuthRefreshDao
{
    void insertOrUpdate(OAuthRefresh oAuthRefresh);

    OAuthRefresh getByRefreshCodeHash(byte[] refreshCodeHash);

    OAuthRefresh getByAccessTokenHash(byte[] accessTokenHash);

    List<OAuthRefresh> getByUserId(String userId);

    List<OAuthRefresh> getByClientId(UUID clientId);

    void deleteByRefreshCodeHash(byte[] refreshCodeHash);

    void bulkDelete(List<byte[]> refreshCodeHashList);

}
