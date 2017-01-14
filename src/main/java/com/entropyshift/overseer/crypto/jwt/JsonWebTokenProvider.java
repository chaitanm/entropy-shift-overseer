package com.entropyshift.overseer.crypto.jwt;

import com.entropyshift.overseer.crypto.key.KeyNotFoundException;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Created by chaitanya.m on 1/12/17.
 */
public class JsonWebTokenProvider implements IJsonWebTokenProvider
{

    private IJwtAppKeyInformationProvider jwtAppKeyInformationProvider;

    private static final String tokenIssuedTime = "TOKEN_ISSUED_TIME";

    public JsonWebTokenProvider(IJwtAppKeyInformationProvider jwtAppKeyInformationProvider)
    {
        this.jwtAppKeyInformationProvider = jwtAppKeyInformationProvider;
    }

    @Override
    public String generateToken(String issuer, String subject, List<String> audience, long issueTime , Map<String, Object> claims) throws JoseException, KeyNotFoundException
    {
        JwtAppKeyInformation currentAppKeyInfo = this.jwtAppKeyInformationProvider.getCurrentJwtAppKeyInformation();
        JwtClaims jwtClaims = new JwtClaims();
        jwtClaims.setIssuer(issuer);
        jwtClaims.setSubject(subject);
        jwtClaims.setAudience(audience);
        jwtClaims.setClaim(tokenIssuedTime, issueTime);
        claims.forEach((key, value) -> jwtClaims.setClaim(key, value));
        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(jwtClaims.toJson());
        jws.setKey(currentAppKeyInfo.getSignatureAndVerificationKeyPairInfo().getKeyPair().getPrivate());
        jws.setKeyIdHeaderValue(currentAppKeyInfo.getSignatureAndVerificationKeyPairInfo().getId());
        jws.setAlgorithmHeaderValue(currentAppKeyInfo.getSignatureAndVerificationMethodIdentifier());
        String signedJwt = jws.getCompactSerialization();
        JsonWebEncryption jwe = new JsonWebEncryption();
        jwe.setAlgorithmHeaderValue(currentAppKeyInfo.getEncryptionAndDecryptionMethodIdentifier());
        jwe.setEncryptionMethodHeaderParameter(currentAppKeyInfo.getContentEncryptionAlgorithm());
        jwe.setKey(currentAppKeyInfo.getEncryptionAndDecryptionKeyPairInfo().getKeyPair().getPublic());
        jwe.setKeyIdHeaderValue(currentAppKeyInfo.getEncryptionAndDecryptionKeyPairInfo().getId());
        jwe.setContentTypeHeaderValue("JWT");
        jwe.setPayload(signedJwt);
        return jwe.getCompactSerialization();
    }

    @Override
    public Map<String, Object> consumeToken(String token, String expectedIssuer, List<String> expectedAudience, long expiryTimeInMilliSeconds) throws InvalidJwtException, MalformedClaimException, KeyNotFoundException
    {
        JwtClaims claims;
        try
        {
            claims = consumeTokenUtil(token, this.jwtAppKeyInformationProvider.getCurrentJwtAppKeyInformation());
        }
        catch (InvalidJwtException e)
        {
            claims = consumeTokenUtil(token, this.jwtAppKeyInformationProvider.getLastJwtAppKeyInformation());
        }
        if(!claims.getIssuer().equals(expectedIssuer) || !claims.getAudience().containsAll(expectedAudience)
                || (Instant.now().toEpochMilli() - Long.parseLong(claims.getClaimValue(tokenIssuedTime).toString()) > expiryTimeInMilliSeconds))
        {
            throw new InvalidJwtException("Claims validation failed");
        }
        return claims.getClaimsMap();

    }

    private JwtClaims consumeTokenUtil(String token, JwtAppKeyInformation jwtAppKeyInformation) throws InvalidJwtException
    {
        JwtConsumer consumer = new JwtConsumerBuilder()
                .setDecryptionKey(jwtAppKeyInformation.getEncryptionAndDecryptionKeyPairInfo().getKeyPair().getPrivate())
                .setVerificationKey(jwtAppKeyInformation.getSignatureAndVerificationKeyPairInfo().getKeyPair().getPublic())
                .build();
        return consumer.processToClaims(token);
    }
}
