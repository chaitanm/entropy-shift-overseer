package com.entropyshift.overseer.oauth2.refresh;

import com.entropyshift.configuration.IPropertiesProvider;
import com.entropyshift.overseer.oauth2.IRandomTokenGenerator;
import com.entropyshift.overseer.oauth2.access.IOAuthAccessDao;
import com.entropyshift.overseer.oauth2.exceptions.OAuthException;
import com.entropyshift.overseer.oauth2.validation.AllowedRegexValidator;
import com.entropyshift.overseer.oauth2.validation.IOAuthValidator;
import com.entropyshift.overseer.oauth2.validation.RequiredFieldValidator;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static com.entropyshift.LambdaExceptionUtil.rethrowConsumer;

/**
 * Created by chaitanya.m on 1/31/17.
 */
public class OAuthRefreshService implements IOAuthRefreshService
{
    private List<IOAuthValidator<OAuthRefreshRequest>> validators;
    private IOAuthRefreshDao oAuthRefreshDao;
    private IOAuthAccessDao oAuthAccessDao;
    private IRandomTokenGenerator randomTokenGenerator;
    private IPropertiesProvider propertiesProvider;
    MessageDigest digest = MessageDigest.getInstance("SHA-256");

    public OAuthRefreshService(IOAuthRefreshDao oAuthRefreshDao, IOAuthAccessDao oAuthAccessDao
            , IRandomTokenGenerator randomTokenGenerator, IPropertiesProvider propertiesProvider) throws NoSuchAlgorithmException
    {
        validators = new ArrayList<>();
        validators.add(new RequiredFieldValidator<>());
        validators.add(new AllowedRegexValidator<>());
        this.oAuthRefreshDao = oAuthRefreshDao;
        this.oAuthAccessDao = oAuthAccessDao;
        this.randomTokenGenerator = randomTokenGenerator;
        this.propertiesProvider = propertiesProvider;
    }

    @Override
    public OAuthRefreshResult refresh(OAuthRefreshRequest request) throws OAuthException
    {
        validators.forEach(rethrowConsumer(validator -> validator.validate(request)));
        OAuthRefresh oAuthRefresh = this.oAuthRefreshDao.getByRefreshCodeHash(digest.digest(request.getRefreshToken().getBytes(StandardCharsets.UTF_8)));
        return null;

    }

    private void oAuthRefreshValidation()
    {

    }
}
