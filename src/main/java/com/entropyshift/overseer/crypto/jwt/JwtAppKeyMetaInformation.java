package com.entropyshift.overseer.crypto.jwt;

/**
 * Created by chaitanya.m on 1/13/17.
 */
public class JwtAppKeyMetaInformation
{
    private int metaId;

    private String encryptionAndDecryptionKeyPairId;

    private String encryptionAndDecryptionKeyPairMethod;

    private String signatureAndVerificationKeyPairId;

    private String signatureAndVerificationKeyPairMethod;

    private String contentEncryptionAlgorithm;

    private long startTime;

    private long endTime;

    public int getMetaId()
    {
        return metaId;
    }

    public void setMetaId(int metaId)
    {
        this.metaId = metaId;
    }

    public String getEncryptionAndDecryptionKeyPairId()
    {
        return encryptionAndDecryptionKeyPairId;
    }

    public void setEncryptionAndDecryptionKeyPairId(String encryptionAndDecryptionKeyPairId)
    {
        this.encryptionAndDecryptionKeyPairId = encryptionAndDecryptionKeyPairId;
    }

    public String getSignatureAndVerificationKeyPairId()
    {
        return signatureAndVerificationKeyPairId;
    }

    public void setSignatureAndVerificationKeyPairId(String signatureAndVerificationKeyPairId)
    {
        this.signatureAndVerificationKeyPairId = signatureAndVerificationKeyPairId;
    }

    public String getContentEncryptionAlgorithm()
    {
        return contentEncryptionAlgorithm;
    }

    public void setContentEncryptionAlgorithm(String contentEncryptionAlgorithm)
    {
        this.contentEncryptionAlgorithm = contentEncryptionAlgorithm;
    }

    public long getStartTime()
    {
        return startTime;
    }

    public void setStartTime(long startTime)
    {
        this.startTime = startTime;
    }

    public long getEndTime()
    {
        return endTime;
    }

    public void setEndTime(long endTime)
    {
        this.endTime = endTime;
    }

    public String getEncryptionAndDecryptionKeyPairMethod()
    {
        return encryptionAndDecryptionKeyPairMethod;
    }

    public void setEncryptionAndDecryptionKeyPairMethod(String encryptionAndDecryptionKeyPairMethod)
    {
        this.encryptionAndDecryptionKeyPairMethod = encryptionAndDecryptionKeyPairMethod;
    }

    public String getSignatureAndVerificationKeyPairMethod()
    {
        return signatureAndVerificationKeyPairMethod;
    }

    public void setSignatureAndVerificationKeyPairMethod(String signatureAndVerificationKeyPairMethod)
    {
        this.signatureAndVerificationKeyPairMethod = signatureAndVerificationKeyPairMethod;
    }
}
