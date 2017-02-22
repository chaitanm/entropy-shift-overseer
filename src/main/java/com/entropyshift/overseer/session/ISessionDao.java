package com.entropyshift.overseer.session;

import java.util.List;

/**
 * Created by chaitanya.m on 2/14/17.
 */
public interface ISessionDao
{
    void insertOrUpdate(Session session);

    Session getBySessionKeyHash(byte[] sessionKeyHash);

    List<Session> getByUserId(String userId);

    void deleteBySessionKeyhash(byte[] sessionKeyHash);

    void bulkDelete(List<byte[]> sessionKeyHashList);
}
