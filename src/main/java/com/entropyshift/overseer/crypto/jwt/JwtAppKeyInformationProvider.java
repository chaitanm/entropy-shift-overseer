package com.entropyshift.overseer.crypto.jwt;

import com.entropyshift.overseer.crypto.key.IAsymmetricKeyPairInformationProvider;
import com.entropyshift.overseer.crypto.key.KeyNotFoundException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.Instant;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Created by chaitanya.m on 1/13/17.
 */
public class JwtAppKeyInformationProvider implements IJwtAppKeyInformationProvider
{
    private static NavigableMap<Long, Range> jwtAppKeyInformationLookup;
    static
    {
        jwtAppKeyInformationLookup = new TreeMap<>();
        ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        IAsymmetricKeyPairInformationProvider asymmetricKeyPairInformationProvider =
                (IAsymmetricKeyPairInformationProvider) appContext.getBean("asymmetricKeyPairInformationProvider");
        IJwtAppKeyMetaInformationDao jwtAppKeyMetaInformationDao =
                (IJwtAppKeyMetaInformationDao) appContext.getBean("jwtAppKeyMetaInformationDao");
        populateJwtAppKeyInformationLookup(asymmetricKeyPairInformationProvider, jwtAppKeyMetaInformationDao);
    }

    @Override
    public JwtAppKeyInformation getCurrentJwtAppKeyInformation() throws KeyNotFoundException
    {
        Map.Entry<Long,Range> entry = jwtAppKeyInformationLookup.floorEntry(Instant.now().toEpochMilli());
        if(entry == null) throw new KeyNotFoundException();
        return entry.getValue().val;
    }

    @Override
    public JwtAppKeyInformation getLastJwtAppKeyInformation() throws KeyNotFoundException
    {
        Long key = jwtAppKeyInformationLookup.floorKey(Instant.now().toEpochMilli());
        Map.Entry<Long, Range> entry = jwtAppKeyInformationLookup.lowerEntry(key);
        if(entry == null) throw new KeyNotFoundException();
        return entry.getValue().val;
    }

    private static void populateJwtAppKeyInformationLookup(IAsymmetricKeyPairInformationProvider asymmetricKeyPairInformationProvider
            ,IJwtAppKeyMetaInformationDao jwtAppKeyMetaInformationDao )
    {
        jwtAppKeyMetaInformationDao.getAll().forEach(jwtAppKeyMetaInformation -> {
            JwtAppKeyInformation jwtAppKeyInformation = new JwtAppKeyInformation(
                    asymmetricKeyPairInformationProvider.getAsymmetricKeyPairInformation(jwtAppKeyMetaInformation.getEncryptionAndDecryptionKeyPairId())
                    , jwtAppKeyMetaInformation.getEncryptionAndDecryptionKeyPairMethod()
                    ,asymmetricKeyPairInformationProvider.getAsymmetricKeyPairInformation(jwtAppKeyMetaInformation.getSignatureAndVerificationKeyPairId())
                    , jwtAppKeyMetaInformation.getEncryptionAndDecryptionKeyPairMethod(), jwtAppKeyMetaInformation.getContentEncryptionAlgorithm());
            jwtAppKeyInformationLookup.put(jwtAppKeyMetaInformation.getStartTime(), new Range(jwtAppKeyMetaInformation.getEndTime(), jwtAppKeyInformation));
        });
    }

    private static class Range
    {
        public long upper;
        public JwtAppKeyInformation val;
        public Range(long upper, JwtAppKeyInformation jwtAppKeyInformation)
        {
            this.upper = upper;
            this.val = jwtAppKeyInformation;
        }
    }

}
