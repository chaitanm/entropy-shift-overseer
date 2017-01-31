package com.entropyshift.overseer.crypto.jwt;

import com.entropyshift.overseer.crypto.key.KeyNotFoundException;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.lang.JoseException;

import java.util.List;
import java.util.Map;

/**
 * Created by chaitanya.m on 1/12/17.
 */
public interface IJsonWebTokenProvider
{
    String generateToken(String issuer, String subject, List<String> audience, long issueTime,  Map<String, Object> claims) throws JoseException, KeyNotFoundException;
    Map<String, Object> consumeToken(String token, String expectedIssuer, String expectedAudience, long expiryTimeInMilliSeconds) throws InvalidJwtException, MalformedClaimException, KeyNotFoundException;
}
