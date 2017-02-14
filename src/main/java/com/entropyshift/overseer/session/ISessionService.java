package com.entropyshift.overseer.session;

/**
 * Created by chaitanya.m on 2/13/17.
 */
public interface ISessionService
{
    String createSession(final String userId, final String ipAddress, final String deviceId, final String browserId);

    void extendSession(String sessionId);

    void deleteSession(String sessionId);

    void deleteUserSessions(String userId);
}
