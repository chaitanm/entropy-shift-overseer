package com.entropyshift.user.validation;

import com.entropyshift.user.UserBaseRequest;
import com.entropyshift.user.exceptions.UserValidationException;

/**
 * Created by chaitanya.m on 2/21/17.
 */
public interface IUserValidator<T extends UserBaseRequest>
{
    void validate(T request) throws UserValidationException;
}
