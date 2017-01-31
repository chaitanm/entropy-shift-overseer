package com.entropyshift.overseer.oauth2.authorize;

import com.entropyshift.configuration.IPropertiesProvider;
import com.entropyshift.overseer.oauth2.IRandomTokenGenerator;
import com.entropyshift.overseer.oauth2.exceptions.OAuthException;
import com.entropyshift.overseer.oauth2.validation.AllowedRegexValidator;
import com.entropyshift.overseer.oauth2.validation.IOAuthValidator;
import com.entropyshift.overseer.oauth2.validation.RequiredFieldValidator;
import com.entropyshift.overseer.oauth2.validation.ScopeValidator;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.entropyshift.LambdaExceptionUtil.rethrowConsumer;

/**
 * Created by chaitanya.m on 1/10/17.
 */
public class OAuthAuthorizationService implements IOAuthAuthorizationService
{
    private List<IOAuthValidator<OAuthAuthorizeRequest>> validators;
    private IOAuthAuthorizationDao oAuthAuthorizationDao;
    private IRandomTokenGenerator randomTokenGenerator;
    private IPropertiesProvider propertiesProvider;
    MessageDigest digest = MessageDigest.getInstance("SHA-256");

    public OAuthAuthorizationService(IOAuthAuthorizationDao oAuthAuthorizationDao, IRandomTokenGenerator randomTokenGenerator,
                                     IPropertiesProvider propertiesProvider)
            throws NoSuchAlgorithmException
    {
        validators = new ArrayList<>();
        validators.add(new RequiredFieldValidator<>());
        validators.add(new AllowedRegexValidator<>());
        validators.add(new ScopeValidator<>());
        this.oAuthAuthorizationDao = oAuthAuthorizationDao;
        this.randomTokenGenerator = randomTokenGenerator;
        this.propertiesProvider = propertiesProvider;
    }

    @Override
    public OAuthAuthorizeResult authorize(OAuthAuthorizeRequest request) throws OAuthException
    {
        validators.forEach(rethrowConsumer(validator -> validator.validate(request)));
        String token = this.randomTokenGenerator.generateRandomToken();
        byte[] tokenHash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
        long currentTimestamp = Instant.now().toEpochMilli();
        OAuthAuthorization oAuthAuthorization = new OAuthAuthorization();
        oAuthAuthorization.setAuthorizationCodeHash(tokenHash);
        oAuthAuthorization.setClientId(request.getClientId());
        oAuthAuthorization.setScope(request.getScope());
        oAuthAuthorization.setUserId(request.getUserId());
        oAuthAuthorization.setClientState(request.getState());
        oAuthAuthorization.setCreatedTimestamp(currentTimestamp);
        oAuthAuthorization.setExpires(currentTimestamp
                + (Long.parseLong(this.propertiesProvider.getProperty("OAUTH_AUTHORIZATION_CODE_EXPIRES_IN_SECONDS")) * 1000));
        oAuthAuthorization.setUserValidated(false);
        oAuthAuthorization.setRedirectUri(request.getRedirectUri());
        this.oAuthAuthorizationDao.insert(oAuthAuthorization);
        return new OAuthAuthorizeResult(request.getUserId(), request.getClientId(), token, request.getState()
                , oAuthAuthorization.getCreatedTimestamp() );
    }

}
