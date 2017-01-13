package com.entropyshift.overseer.crypto.key;

import java.security.KeyPair;

/**
 * Created by chaitanya.m on 1/13/17.
 */
public final class AsymmetricKeyPairInformation
{
    private final String id;

    private final KeyPair keyPair;

    private final AsymmetricKeyAlgorithms algorithm;

    public AsymmetricKeyPairInformation(String id, KeyPair keyPair, AsymmetricKeyAlgorithms algorithm)
    {
        this.id = id;
        this.keyPair = keyPair;
        this.algorithm = algorithm;
    }

    public String getId()
    {
        return id;
    }

    public KeyPair getKeyPair()
    {
        return keyPair;
    }

    public AsymmetricKeyAlgorithms getAlgorithm()
    {
        return algorithm;
    }
}
