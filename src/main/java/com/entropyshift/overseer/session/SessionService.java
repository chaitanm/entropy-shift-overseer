package com.entropyshift.overseer.session;

import com.entropyshift.configuration.IPropertiesProvider;
import com.entropyshift.overseer.oauth2.IRandomTokenGenerator;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

/**
 * Created by chaitanya.m on 2/13/17.
 */
public class SessionService implements ISessionService
{
    private IRandomTokenGenerator randomTokenGenerator;

    private IPropertiesProvider propertiesProvider;

    MessageDigest digest = MessageDigest.getInstance("SHA-256");

    public SessionService(IRandomTokenGenerator randomTokenGenerator, IPropertiesProvider propertiesProvider) throws NoSuchAlgorithmException
    {
        this.randomTokenGenerator = randomTokenGenerator;
        this.propertiesProvider = propertiesProvider;
    }

    @Override
    public String createSession(String userId, String ipAddress, String deviceId, String browserId)
    {
        long currentTimestamp = Instant.now().toEpochMilli();
        Session session = new Session();
        String sessionKey = this.randomTokenGenerator.generateRandomToken();
        byte[] sessionKeyHash = digest.digest(sessionKey.getBytes(StandardCharsets.UTF_8));
        session.setSessionKeyHash(sessionKeyHash);
        session.setUserId(userId);
        session.setIpAddress(ipAddress);
        session.setDeviceId(deviceId);
        session.setBrowserId(browserId);
        session.setCreatedTimestamp(currentTimestamp);
        session.setExpires(currentTimestamp + (Long.parseLong(this.propertiesProvider.getProperty("SESSION_EXPIRY_TIME_IN_SECONDS")) * 1000));
        session.setAbsoluteExpiry( currentTimestamp + (Long.parseLong(this.propertiesProvider.getProperty("SESSION_ABSOLUTE_EXPIRY_TIME_IN_SECONDS")) * 1000);
        return sessionKey;
    }


    @Override
    public void extendSession(String sessionId)
    {

    }

    @Override
    public void deleteSession(String sessionId)
    {

    }

    @Override
    public void deleteUserSessions(String userId)
    {

    }
}
