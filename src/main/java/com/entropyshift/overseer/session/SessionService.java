package com.entropyshift.overseer.session;

import com.entropyshift.PropertyNameConstants;
import com.entropyshift.configuration.IPropertiesProvider;
import com.entropyshift.overseer.oauth2.IRandomTokenGenerator;
import com.entropyshift.overseer.session.exceptions.SessionExpiredException;
import com.entropyshift.overseer.session.exceptions.SessionNotFoundException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by chaitanya.m on 2/13/17.
 */
public class SessionService implements ISessionService
{
    private ISessionDao sessionDao;

    private IRandomTokenGenerator randomTokenGenerator;

    private IPropertiesProvider propertiesProvider;

    MessageDigest digest = MessageDigest.getInstance("SHA-256");

    public SessionService(ISessionDao sessionDao, IRandomTokenGenerator randomTokenGenerator, IPropertiesProvider propertiesProvider) throws NoSuchAlgorithmException
    {
        this.sessionDao = sessionDao;
        this.randomTokenGenerator = randomTokenGenerator;
        this.propertiesProvider = propertiesProvider;
    }

    @Override
    public CreateSessionResult createSession(final String userId, final String scope, final String ipAddress
            , final UUID deviceId, final UUID browserId)
    {
        long currentTimestamp = Instant.now().toEpochMilli();
        Session session = new Session();
        String sessionKey = this.randomTokenGenerator.generateRandomToken();
        byte[] sessionKeyHash = digest.digest(sessionKey.getBytes(StandardCharsets.UTF_8));
        session.setSessionKeyHash(sessionKeyHash);
        session.setUserId(userId);
        session.setScope(scope);
        session.setIpAddress(ipAddress);
        session.setDeviceId(deviceId);
        session.setBrowserId(browserId);
        session.setCreatedTimestamp(currentTimestamp);
        session.setExpires(currentTimestamp + (Long.parseLong(this.propertiesProvider.getProperty(PropertyNameConstants.SESSION_EXPIRES_IN_SECONDS)) * 1000));
        session.setAbsoluteExpiry(currentTimestamp + (Long.parseLong(this.propertiesProvider.getProperty(PropertyNameConstants.SESSION_ABSOLUTE_EXPIRES_IN_SECONDS)) * 1000));
        this.sessionDao.insertOrUpdate(session);
        return new CreateSessionResult(sessionKey, currentTimestamp, scope);
    }

    @Override
    public void extendSession(final String sessionKey) throws SessionNotFoundException, SessionExpiredException
    {
        long currentTimestamp = Instant.now().toEpochMilli();
        byte[] sessionKeyHash = digest.digest(sessionKey.getBytes(StandardCharsets.UTF_8));
        Session session = sessionDao.getBySessionKeyHash(sessionKeyHash);
        if (session == null)
        {
            throw new SessionNotFoundException();
        }
        long sessionExtensionTime = Long.parseLong(this.propertiesProvider.getProperty(PropertyNameConstants.SESSION_EXTENSION_TIME_IN_SECONDS)) * 1000;
        if (currentTimestamp > session.getExpires())
        {
            throw new SessionExpiredException();
        }
        else if ((currentTimestamp + sessionExtensionTime) < session.getExpires())
        {
            return;
        }
        else
        {
            session.setExpires(currentTimestamp + sessionExtensionTime);
            this.sessionDao.insertOrUpdate(session);
        }
    }

    @Override
    public void deleteSession(final String sessionKey)
    {
        byte[] sessionKeyHash = digest.digest(sessionKey.getBytes(StandardCharsets.UTF_8));
        this.sessionDao.deleteBySessionKeyhash(sessionKeyHash);
    }

    @Override
    public void deleteUserSessions(final String userId)
    {
        List<Session> sessionList = this.sessionDao.getByUserId(userId);
        if (sessionList == null || sessionList.size() == 0)
        {
            return;
        }
        List<byte[]> sessionKeyHashList = new ArrayList<>();
        sessionList.forEach(session -> sessionKeyHashList.add(session.getSessionKeyHash()));
        this.sessionDao.bulkDelete(sessionKeyHashList);
    }

    @Override
    public Session validateSession(final String sessionKey) throws SessionExpiredException
    {
        byte[] sessionKeyHash = digest.digest(sessionKey.getBytes(StandardCharsets.UTF_8));
        final Session session = sessionDao.getBySessionKeyHash(sessionKeyHash);
        if (session.getExpires() < Instant.now().toEpochMilli())
        {
            throw new SessionExpiredException();
        }
        return session;


    }

}
