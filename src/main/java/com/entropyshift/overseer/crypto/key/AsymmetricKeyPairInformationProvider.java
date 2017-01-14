package com.entropyshift.overseer.crypto.key;

import com.entropyshift.overseer.crypto.ellipticalcurve.EllipticalCurveKeyInformation;
import com.entropyshift.overseer.crypto.ellipticalcurve.IEllipticalCurveKeyInformationDao;
import com.entropyshift.overseer.crypto.rsa.IRsaKeyInformationDao;
import com.entropyshift.overseer.crypto.rsa.RsaKeyInformation;
import org.jose4j.jwk.EllipticCurveJsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;

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

    private  Map<String, AsymmetricKeyPairInformation> asymmetricKeyPairInformationMap;

    public AsymmetricKeyPairInformationProvider(IRsaKeyInformationDao rsaKeyInformationDao
            , IEllipticalCurveKeyInformationDao ellipticalCurveKeyInformationDao)
    {
        asymmetricKeyPairInformationMap = new HashMap<>();
        populateRsaKeyInformation(rsaKeyInformationDao);
        populateEllipticalCurveKeyInformation(ellipticalCurveKeyInformationDao);
    }

    @Override
    public AsymmetricKeyPairInformation getAsymmetricKeyPairInformation(String keyPairId)
    {
        return asymmetricKeyPairInformationMap.get(keyPairId);
    }

    private void populateRsaKeyInformation(IRsaKeyInformationDao rsaKeyInformationDao)
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

    private void populateEllipticalCurveKeyInformation(IEllipticalCurveKeyInformationDao ellipticalCurveKeyInformationDao)
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
