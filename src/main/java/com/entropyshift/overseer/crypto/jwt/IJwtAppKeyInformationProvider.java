package com.entropyshift.overseer.crypto.jwt;

import com.entropyshift.overseer.crypto.key.KeyNotFoundException;

/**
 * Created by chaitanya.m on 1/13/17.
 */
public interface IJwtAppKeyInformationProvider
{
    JwtAppKeyInformation getCurrentJwtAppKeyInformation() throws KeyNotFoundException;
    JwtAppKeyInformation getLastJwtAppKeyInformation() throws KeyNotFoundException;
}
