package com.entropyshift.overseer.crypto.jwt;

import com.entropyshift.overseer.crypto.key.AppKeyInformation;
import com.entropyshift.overseer.crypto.key.AsymmetricKeyPairInformation;

/**
 * Created by chaitanya.m on 1/13/17.
 */
public class JwtAppKeyInformation extends AppKeyInformation
{
    private final AsymmetricKeyPairInformation encryptionAndDecryptionKeyPairInfo;

    private final String encryptionAndDecryptionMethodIdentifier;

    private final AsymmetricKeyPairInformation signatureAndVerificationKeyPairInfo;

    private final String signatureAndVerificationMethodIdentifier;

    private final String contentEncryptionAlgorithm;

    public JwtAppKeyInformation(AsymmetricKeyPairInformation encryptionAndDecryptionKeyPairInfo
            , String encryptionAndDecryptionMethodIdentifier
            , AsymmetricKeyPairInformation signatureAndVerificationKeyPairInfo
            , String signatureAndVerificationMethodIdentifier
            , String contentEncryptionAlgorithm)
    {
        this.encryptionAndDecryptionKeyPairInfo = encryptionAndDecryptionKeyPairInfo;
        this.encryptionAndDecryptionMethodIdentifier = encryptionAndDecryptionMethodIdentifier;
        this.signatureAndVerificationKeyPairInfo = signatureAndVerificationKeyPairInfo;
        this.signatureAndVerificationMethodIdentifier = signatureAndVerificationMethodIdentifier;
        this.contentEncryptionAlgorithm = contentEncryptionAlgorithm;
    }

    public AsymmetricKeyPairInformation getEncryptionAndDecryptionKeyPairInfo()
    {
        return encryptionAndDecryptionKeyPairInfo;
    }

    public AsymmetricKeyPairInformation getSignatureAndVerificationKeyPairInfo()
    {
        return signatureAndVerificationKeyPairInfo;
    }

    public String getContentEncryptionAlgorithm()
    {
        return contentEncryptionAlgorithm;
    }

    public String getEncryptionAndDecryptionMethodIdentifier()
    {
        return encryptionAndDecryptionMethodIdentifier;
    }

    public String getSignatureAndVerificationMethodIdentifier()
    {
        return signatureAndVerificationMethodIdentifier;
    }
}
