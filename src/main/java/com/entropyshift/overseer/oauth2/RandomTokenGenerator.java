package com.entropyshift.overseer.oauth2;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by chaitanya.m on 1/15/17.
 */
public final class RandomTokenGenerator implements  IRandomTokenGenerator
{
    private SecureRandom random = new SecureRandom();

    @Override
    public String generateRandomToken()
    {
        return new BigInteger(130, random).toString(32);
    }
}
