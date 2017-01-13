package com.entropyshift.overseer.crypto.rsa;

import java.util.List;

/**
 * Created by chaitanya.m on 1/12/17.
 */
public interface IRsaKeyInformationDao
{
    List<RsaKeyInformation> getAll();
}
