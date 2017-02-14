package com.entropyshift.overseer.credentials;

/**
 * Created by chaitanya.m on 2/9/17.
 */
public interface IPasswordHashGenerator
{
    byte[] generatePasswordHash(final String password, final byte[] passwordSalt, final String hashAlgorithm,
                                final int iterations, final int derivedKeyLength);
}
