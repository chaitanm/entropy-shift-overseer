package com.entropyshift.overseer.crypto.key;

/**
 * Created by chaitanya.m on 1/13/17.
 */
public interface IAsymmetricKeyPairInformationProvider
{
    AsymmetricKeyPairInformation getAsymmetricKeyPairInformation(String keyPairId);
}
