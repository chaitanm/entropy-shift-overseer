package com.entropyshift.overseer.session;

import com.entropyshift.overseer.session.exceptions.SessionExpiredException;
import com.entropyshift.overseer.session.exceptions.SessionNotFoundException;

import java.util.UUID;

/**
 * Created by chaitanya.m on 2/13/17.
 */
public interface ISessionService
{
    CreateSessionResult createSession(final String userId, final String scope, final String ipAddress, final UUID deviceId, final UUID browserId);

    void extendSession(String sessionKey) throws SessionNotFoundException, SessionExpiredException;

    void deleteSession(String sessionKey);

    void deleteUserSessions(String userId);
}
