package com.entropyshift.user.registration;

import com.entropyshift.overseer.credentials.ICredentialService;
import com.entropyshift.overseer.credentials.exceptions.PasswordHashGeneratorNotFoundException;
import com.entropyshift.user.exceptions.UserValidationException;
import com.entropyshift.user.profile.*;
import com.entropyshift.user.validation.IUserValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.entropyshift.LambdaExceptionUtil.rethrowConsumer;

/**
 * Created by chaitanya.m on 2/21/17.
 */
public class RegistrationService implements IRegistrationService
{
    private ICredentialService credentialService;

    private IUserInformationDao userInformationDao;

    private List<IUserValidator<RegisterUserRequest>> validators;

    public RegistrationService(ICredentialService credentialService, IUserInformationDao userInformationDao)
    {
        this.credentialService = credentialService;
        this.userInformationDao = userInformationDao;
        validators = new ArrayList<>();
        validators.add(new RegistrationValidator(userInformationDao));
    }


    @Override
    public boolean isUserIdAvailable(String userId)
    {
        return this.userInformationDao.getByUserId(userId) != null;
    }

    @Override
    public boolean isEmailAddressAlreadyRegistered(String emailAddress)
    {
        return this.userInformationDao.getByEmailAddress(emailAddress) != null;
    }

    @Override
    public void registerUser(RegisterUserRequest request, UUID deviceId, UUID browserId, String ipAddress, GeoLocationInformation geoLocationInformation) throws UserValidationException, PasswordHashGeneratorNotFoundException
    {
        validators.forEach(rethrowConsumer(validator -> validator.validate(request)));
        UserInformation userInformation = new UserInformation();
        userInformation.setUserId(request.getUserId());
        userInformation.setEmailAddress(request.getEmailAddress());
        userInformation.setNameInformation(new NameInformation(request.getFirstName(), request.getMiddleName(), request.getLastName()));
        userInformation.setDateOfBirth(request.getDateOfBirth());
        userInformation.setPhoneNumberInformationList(request.getPhoneNumberInformationList());
        userInformation.setAddressInformationList(request.getAddressInformationList());
        userInformation.setRegistrationDeviceId(deviceId);
        userInformation.setRegistrationBrowserId(browserId);
        userInformation.setRegistrationIpAddress(ipAddress);
        userInformation.setRegistrationGeoLocationInformation(geoLocationInformation);
        userInformation.setPasswordRejectionCount(0);
        UUID uuid = this.credentialService.saveNewCredentials(request.getUserId(), request.getPassword());
        userInformation.setUuid(uuid);
        userInformation.setStatus(UserStatus.PENDING_EMAIL_VERIFICATION);
        this.userInformationDao.insertOrUpdate(userInformation);
    }

    @Override
    public void emailConfirmation(String token)
    {

    }


}
