package com.entropyshift.overseer.credentials;

import com.entropyshift.overseer.credentials.exceptions.PasswordHashGeneratorNotFoundException;

/**
 * Created by chaitanya.m on 2/9/17.
 */
public class DefaultPasswordHashGeneratorFactory implements IPasswordHashGeneratorFactory
{

    @Override
    public IPasswordHashGenerator getPasswordHashCalculator(String passwordHashGeneratorName) throws PasswordHashGeneratorNotFoundException
    {
        switch (passwordHashGeneratorName) {
            case "PBKDF2":
                return new PBKDF2PasswordHashGenerator();
            default:
                throw new PasswordHashGeneratorNotFoundException();
        }
    }
}
