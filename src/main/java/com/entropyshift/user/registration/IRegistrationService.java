package com.entropyshift.user.registration;

import com.entropyshift.overseer.credentials.exceptions.PasswordHashGeneratorNotFoundException;
import com.entropyshift.user.exceptions.UserValidationException;
import com.entropyshift.user.profile.GeoLocationInformation;

import java.util.UUID;

/**
 * Created by chaitanya.m on 2/21/17.
 */
public interface IRegistrationService
{
    boolean isUserIdAvailable(String userId);

    boolean isEmailAddressAlreadyRegistered(String emailAddress);

    void registerUser(RegisterUserRequest request, UUID deviceId, UUID browserId, String ipAddress, GeoLocationInformation geoLocationInformation) throws UserValidationException, PasswordHashGeneratorNotFoundException;

    void emailConfirmation(String token);
}
