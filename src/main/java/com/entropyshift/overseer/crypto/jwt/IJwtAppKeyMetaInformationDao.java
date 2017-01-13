package com.entropyshift.overseer.crypto.jwt;

import java.util.List;

/**
 * Created by chaitanya.m on 1/13/17.
 */
public interface IJwtAppKeyMetaInformationDao
{
    List<JwtAppKeyMetaInformation> getAll();
}
