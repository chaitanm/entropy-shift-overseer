package com.entropyshift.overseer.credentials;

import com.entropyshift.overseer.credentials.exceptions.PasswordHashGeneratorNotFoundException;

/**
 * Created by chaitanya.m on 2/9/17.
 */
public interface IPasswordHashGeneratorFactory
{
    IPasswordHashGenerator getPasswordHashCalculator(final String passwordHashGeneratorName) throws PasswordHashGeneratorNotFoundException;
}
