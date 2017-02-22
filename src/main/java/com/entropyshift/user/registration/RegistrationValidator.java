package com.entropyshift.user.registration;

import com.entropyshift.user.constants.UserValidationErrorCodeDescriptors;
import com.entropyshift.user.exceptions.UserValidationException;
import com.entropyshift.user.profile.IUserInformationDao;
import com.entropyshift.user.validation.IUserValidator;

/**
 * Created by chaitanya.m on 2/21/17.
 */
public class RegistrationValidator implements IUserValidator<RegisterUserRequest>
{

    private IUserInformationDao userInformationDao;

    public RegistrationValidator(IUserInformationDao userInformationDao)
    {
        this.userInformationDao = userInformationDao;
    }

    @Override
    public void validate(RegisterUserRequest request) throws UserValidationException
    {
        if (this.userInformationDao.getByUserId(request.getUserId()) != null)
        {
            throw new UserValidationException(UserValidationErrorCodeDescriptors.USER_ID_NOT_AVAILABLE_FOR_REGISTRATION, request.getUserId());
        }

        if (this.userInformationDao.getByEmailAddress(request.getEmailAddress()) != null)
        {
            throw new UserValidationException(UserValidationErrorCodeDescriptors.EMAIL_ADDRESS_ALREADY_REGISTERED, request.getEmailAddress());
        }

        if(!request.getPassword().equals(request.getConfirmPassword()))
        {
            throw new UserValidationException(UserValidationErrorCodeDescriptors.CONFIRM_PASSWORD_DOES_NOT_MATCH);
        }
    }
}
