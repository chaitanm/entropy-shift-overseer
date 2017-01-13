package com.entropyshift.overseer.crypto.key;

import com.entropyshift.overseer.crypto.ellipticalcurve.EllipticalCurveKeyInformation;
import com.entropyshift.overseer.crypto.ellipticalcurve.IEllipticalCurveKeyInformationDao;
import com.entropyshift.overseer.crypto.rsa.IRsaKeyInformationDao;
import com.entropyshift.overseer.crypto.rsa.RsaKeyInformation;
import org.jose4j.jwk.EllipticCurveJsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.security.KeyPair;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.entropyshift.LambdaExceptionUtil.rethrowConsumer;

/**
 * Created by chaitanya.m on 1/13/17.
 */
public class AsymmetricKeyPairInformationProvider implements IAsymmetricKeyPairInformationProvider
{

    private static Map<String, AsymmetricKeyPairInformation> asymmetricKeyPairInformationMap;

    static
    {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        asymmetricKeyPairInformationMap = new HashMap<>();
        populateEllipticalCurveKeyInformation((IEllipticalCurveKeyInformationDao) appContext.getBean("ellipticalCurveKeyInformationDao"));
        populateRsaKeyInformation((IRsaKeyInformationDao) appContext.getBean("rsaKeyInformationDao"));
    }


    @Override
    public AsymmetricKeyPairInformation getAsymmetricKeyPairInformation(String keyPairId)
    {
        return asymmetricKeyPairInformationMap.get(keyPairId);
    }

    private static void populateRsaKeyInformation(IRsaKeyInformationDao rsaKeyInformationDao)
    {
        List<RsaKeyInformation> rsaKeyInformationList = rsaKeyInformationDao.getAll();
        rsaKeyInformationList.forEach(rethrowConsumer(rsaKeyInformation ->
        {
            RsaJsonWebKey rsaJsonWebKey = new RsaJsonWebKey(rsaKeyInformation.toMap());
            KeyPair keyPair = new KeyPair(rsaJsonWebKey.getRsaPublicKey(), rsaJsonWebKey.getRsaPrivateKey());
            AsymmetricKeyPairInformation asymmetricKeyPairInformation =
                    new AsymmetricKeyPairInformation(rsaKeyInformation.getId(), keyPair, AsymmetricKeyAlgorithms.RSA);
            asymmetricKeyPairInformationMap.put(rsaKeyInformation.getId(), asymmetricKeyPairInformation);
        }));
    }

    private static void populateEllipticalCurveKeyInformation(IEllipticalCurveKeyInformationDao ellipticalCurveKeyInformationDao)
    {
        List<EllipticalCurveKeyInformation> ellipticalCurveKeyInformationList = ellipticalCurveKeyInformationDao.getAll();
        ellipticalCurveKeyInformationList.forEach(rethrowConsumer(ellipticalCurveKeyInformation ->
        {
            EllipticCurveJsonWebKey ellipticCurveJsonWebKey = new EllipticCurveJsonWebKey(ellipticalCurveKeyInformation.toMap());
            KeyPair keyPair = new KeyPair(ellipticCurveJsonWebKey.getECPublicKey(), ellipticCurveJsonWebKey.getPrivateKey());
            AsymmetricKeyPairInformation asymmetricKeyPairInformation =
                    new AsymmetricKeyPairInformation(ellipticalCurveKeyInformation.getId(), keyPair, AsymmetricKeyAlgorithms.ELLIPTICAL_CURVE);
            asymmetricKeyPairInformationMap.put(ellipticalCurveKeyInformation.getId(), asymmetricKeyPairInformation);
        }));
    }

}
