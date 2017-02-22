package com.entropyshift.overseer.credentials;

import com.entropyshift.PropertyNameConstants;
import com.entropyshift.configuration.IPropertiesProvider;
import com.entropyshift.overseer.credentials.exceptions.IncorrectPasswordException;
import com.entropyshift.overseer.credentials.exceptions.NewPasswordSameAsCurrentPasswordException;
import com.entropyshift.overseer.credentials.exceptions.PasswordHashGeneratorNotFoundException;
import com.entropyshift.overseer.credentials.exceptions.UserCredentialsNotFoundException;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.UUID;

/**
 * Created by chaitanya.m on 2/9/17.
 */
public class CredentialService implements ICredentialService
{
    private IPasswordHashGeneratorFactory passwordHashGeneratorFactory;
    private IUserCredentialsDao userCredentialsDao;
    private IPropertiesProvider propertiesProvider;
    private final String passwordKeyDerivationFunction;
    private final String passwordHashAlgorithm;
    private final int passwordSaltLength;
    private final int passwordHashIterationCount;
    private final int passwordHashDerivedKeyLength;

    public CredentialService(IPasswordHashGeneratorFactory passwordHashGeneratorFactory, IUserCredentialsDao userCredentialsDao
            , IPropertiesProvider propertiesProvider)
    {
        this.passwordHashGeneratorFactory = passwordHashGeneratorFactory;
        this.userCredentialsDao = userCredentialsDao;
        this.propertiesProvider = propertiesProvider;
        passwordKeyDerivationFunction = this.propertiesProvider.getProperty(PropertyNameConstants.PASSWORD_HASH_CALCULATOR);
        passwordHashAlgorithm = this.propertiesProvider.getProperty(PropertyNameConstants.PASSWORD_HASH_ALGORITHM);
        passwordSaltLength = Integer.parseInt(this.propertiesProvider.getProperty(PropertyNameConstants.PASSWORD_SALT_LENGTH));
        passwordHashIterationCount = Integer.parseInt(this.propertiesProvider.getProperty(PropertyNameConstants.PASSWORD_HASH_ITERATION_COUNT));
        passwordHashDerivedKeyLength = Integer.parseInt(this.propertiesProvider.getProperty(PropertyNameConstants.PASSWORD_HASH_DERIVED_KEY_LENGTH));
    }

    @Override
    public UUID saveNewCredentials(String username, String password) throws PasswordHashGeneratorNotFoundException
    {
        UserCredentials userCredentials = new UserCredentials();
        UUID uuid = UUID.randomUUID();
        userCredentials.setUuid(uuid.toString());
        userCredentials.setUserId(username);
        this.applyPasswordPolicy(userCredentials, password);
        userCredentialsDao.insertUserCredentials(userCredentials);
        return uuid;
    }

    @Override
    public void authenticateCredentials(String username, String password) throws UserCredentialsNotFoundException, PasswordHashGeneratorNotFoundException, IncorrectPasswordException
    {
        UserCredentials userCredentials = userCredentialsDao.getUserCredentialsByUsername(username);
        if (!this.verifyPassword(userCredentials, password))
        {
            throw new IncorrectPasswordException();
        }
    }

    @Override
    public void changePassword(String username, String currentPassword, String newPassword) throws NewPasswordSameAsCurrentPasswordException, UserCredentialsNotFoundException, PasswordHashGeneratorNotFoundException, IncorrectPasswordException
    {
        if (currentPassword.equals(newPassword))
        {
            throw new NewPasswordSameAsCurrentPasswordException();
        }
        UserCredentials userCredentials = userCredentialsDao.getUserCredentialsByUsername(username);
        if (!this.verifyPassword(userCredentials, currentPassword))
        {
            throw new IncorrectPasswordException();
        }
        this.applyPasswordPolicy(userCredentials, newPassword);
        userCredentialsDao.updateUserCredentials(userCredentials);
    }

    @Override
    public void resetPassword(String username, String newPassword) throws UserCredentialsNotFoundException, PasswordHashGeneratorNotFoundException
    {
        UserCredentials userCredentials = userCredentialsDao.getUserCredentialsByUsername(username);
        this.applyPasswordPolicy(userCredentials, newPassword);
        userCredentialsDao.updateUserCredentials(userCredentials);
    }

    @Override
    public String getUuidFromUsername(final String username) throws UserCredentialsNotFoundException
    {
        UserCredentials userCredentials = userCredentialsDao.getUserCredentialsByUsername(username);
        return userCredentials.getUuid();
    }

    private boolean verifyPassword(UserCredentials userCredentials, String password)
            throws PasswordHashGeneratorNotFoundException
    {
        IPasswordHashGenerator passwordHashGenerator = passwordHashGeneratorFactory
                .getPasswordHashCalculator(userCredentials.getPasswordKeyDerivationFunction());
        byte[] passwordHash = passwordHashGenerator.generatePasswordHash(password, userCredentials.getPasswordSalt(),
                userCredentials.getPasswordHashAlgorithm(), userCredentials.getPasswordHashIterationCount(),
                userCredentials.getPasswordHashDerivedKeyLength());
        return Arrays.equals(passwordHash, userCredentials.getPasswordHash());

    }

    private void applyPasswordPolicy(UserCredentials userCredentials, String password)
            throws PasswordHashGeneratorNotFoundException
    {
        IPasswordHashGenerator passwordHashGenerator = passwordHashGeneratorFactory
                .getPasswordHashCalculator(passwordKeyDerivationFunction);
        SecureRandom sr = new SecureRandom();
        byte[] passwordSalt = new byte[passwordSaltLength];
        sr.nextBytes(passwordSalt);
        byte[] passwordHash = passwordHashGenerator.generatePasswordHash(password, passwordSalt, passwordHashAlgorithm,
                passwordHashIterationCount, passwordHashDerivedKeyLength);
        userCredentials.setPasswordHash(passwordHash);
        userCredentials.setPasswordKeyDerivationFunction(passwordKeyDerivationFunction);
        userCredentials.setPasswordHashAlgorithm(passwordHashAlgorithm);
        userCredentials.setPasswordSalt(passwordSalt);
        userCredentials.setPasswordHashIterationCount(passwordHashIterationCount);
        userCredentials.setPasswordHashDerivedKeyLength(passwordHashDerivedKeyLength);
        userCredentials.setPasswordSaltLength(passwordSaltLength);
    }
}
