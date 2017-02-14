package com.entropyshift.overseer.credentials;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by chaitanya.m on 2/9/17.
 */
public class PBKDF2PasswordHashGenerator implements IPasswordHashGenerator
{
    @Override
    public byte[] generatePasswordHash(String password, byte[] passwordSalt, String hashAlgorithm, int iterations, int derivedKeyLength)
    {
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmac" + hashAlgorithm);
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), passwordSalt, iterations, derivedKeyLength);
            SecretKey key = skf.generateSecret(spec);
            byte[] res = key.getEncoded();
            return res;

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
