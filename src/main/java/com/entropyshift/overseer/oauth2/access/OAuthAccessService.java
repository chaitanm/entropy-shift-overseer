package com.entropyshift.overseer.oauth2.access;

import com.entropyshift.PropertyNameConstants;
import com.entropyshift.configuration.IPropertiesProvider;
import com.entropyshift.overseer.oauth2.IRandomTokenGenerator;
import com.entropyshift.overseer.oauth2.authorize.IOAuthAuthorizationDao;
import com.entropyshift.overseer.oauth2.authorize.OAuthAuthorization;
import com.entropyshift.overseer.oauth2.constants.OAuthErrorCodeDescriptors;
import com.entropyshift.overseer.oauth2.constants.OAuthErrorCodesDescription;
import com.entropyshift.overseer.oauth2.constants.OAuthGrantType;
import com.entropyshift.overseer.oauth2.constants.OAuthTokenType;
import com.entropyshift.overseer.oauth2.exceptions.OAuthException;
import com.entropyshift.overseer.oauth2.refresh.IOAuthRefreshDao;
import com.entropyshift.overseer.oauth2.refresh.OAuthRefresh;
import com.entropyshift.overseer.oauth2.validation.AllowedRegexValidator;
import com.entropyshift.overseer.oauth2.validation.IOAuthValidator;
import com.entropyshift.overseer.oauth2.validation.RequiredFieldValidator;

import javax.ws.rs.core.UriBuilder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.entropyshift.LambdaExceptionUtil.rethrowConsumer;

/**
 * Created by chaitanya.m on 1/29/17.
 */
public class OAuthAccessService implements IOAuthAccessService
{
    private List<IOAuthValidator<OAuthAccessRequest>> validators;
    private IOAuthAuthorizationDao oAuthAuthorizationDao;
    private IOAuthAccessDao oAuthAccessDao;
    private IOAuthRefreshDao oAuthRefreshDao;
    private IRandomTokenGenerator randomTokenGenerator;
    private IPropertiesProvider propertiesProvider;

    MessageDigest digest = MessageDigest.getInstance("SHA-256");

    public OAuthAccessService(IOAuthAccessDao oAuthAccessDao, IOAuthAuthorizationDao oAuthAuthorizationDao
            , IOAuthRefreshDao oAuthRefreshDao, IRandomTokenGenerator randomTokenGenerator, IPropertiesProvider propertiesProvider) throws NoSuchAlgorithmException
    {
        validators = new ArrayList<>();
        validators.add(new RequiredFieldValidator<>());
        validators.add(new AllowedRegexValidator<>());
        this.oAuthAccessDao = oAuthAccessDao;
        this.oAuthAuthorizationDao = oAuthAuthorizationDao;
        this.oAuthRefreshDao = oAuthRefreshDao;
        this.randomTokenGenerator = randomTokenGenerator;
        this.propertiesProvider = propertiesProvider;

    }

    @Override
    public OAuthAccessResult grantAccess(final OAuthAccessRequest request) throws OAuthException
    {
        validators.forEach(rethrowConsumer(validator -> validator.validate(request)));
        OAuthAuthorization oAuthAuthorization = oAuthAuthorizationDao
                .getByAuthorizationCodeHash(digest.digest(request.getAuthorizationCode().getBytes(StandardCharsets.UTF_8)));
        oAuthAuthorizationValidation(request, oAuthAuthorization);
        long currentTimestamp = Instant.now().toEpochMilli();

        String token = this.randomTokenGenerator.generateRandomToken();
        byte[] tokenHash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
        OAuthAccess oAuthAccess = new OAuthAccess();
        oAuthAccess.setAccessTokenHash(tokenHash);
        oAuthAccess.setClientId(request.getClientId());
        oAuthAccess.setUserId(request.getUserId());
        oAuthAccess.setClientState(oAuthAuthorization.getClientState());
        oAuthAccess.setCreatedTimestamp(currentTimestamp);
        oAuthAccess.setScope(oAuthAuthorization.getScope());
        oAuthAccess.setExpires(currentTimestamp + (Long.parseLong(this.propertiesProvider.getProperty(PropertyNameConstants.OAUTH_ACCESS_TOKEN_EXPIRES_IN_SECONDS)) * 1000));

        String refreshToken = this.randomTokenGenerator.generateRandomToken();
        byte[] refreshTokenHash = digest.digest(refreshToken.getBytes(StandardCharsets.UTF_8));
        OAuthRefresh oAuthRefresh = new OAuthRefresh();
        oAuthRefresh.setRefreshTokenHash(refreshTokenHash);
        oAuthRefresh.setAccessTokenHash(tokenHash);
        oAuthRefresh.setUserId(request.getUserId());
        oAuthRefresh.setClientId(request.getClientId());
        oAuthRefresh.setCreatedTimestamp(currentTimestamp);
        oAuthRefresh.setClientState(oAuthAccess.getClientState());
        oAuthRefresh.setScope(oAuthAccess.getScope());
        oAuthRefresh.setExpires(currentTimestamp + (Long.parseLong(this.propertiesProvider.getProperty(PropertyNameConstants.OAUTH_REFRESH_TOKEN_EXPIRES_IN_SECONDS)) * 1000));

        oAuthAuthorization.setAccessTokenHash(tokenHash);
        oAuthAuthorization.setRefreshTokenHash(refreshTokenHash);
        this.oAuthAccessDao.insertOrUpdate(oAuthAccess);
        this.oAuthRefreshDao.insertOrUpdate(oAuthRefresh);
        this.oAuthAuthorizationDao.insertOrUpdate(oAuthAuthorization);
        return new OAuthAccessResult(oAuthAccess.getUserId(), oAuthAccess.getClientId(), token, OAuthTokenType.BEARER, currentTimestamp, refreshToken);
    }

    private void oAuthAuthorizationValidation(final OAuthAccessRequest request, final OAuthAuthorization oAuthAuthorization) throws OAuthException
    {
        if (oAuthAuthorization == null)
        {
            throw new OAuthException(OAuthErrorCodeDescriptors.AUTHORIZATION_DETAILS_NOT_AVAILABLE
                    , OAuthErrorCodesDescription.getErrorDescription(OAuthErrorCodeDescriptors.AUTHORIZATION_DETAILS_NOT_AVAILABLE));
        }
        else if (oAuthAuthorization.getAccessTokenHash() != null)
        {
            processAuthorizationCodePresentedMoreThanOnce(oAuthAuthorization);
            throw new OAuthException(OAuthErrorCodeDescriptors.AUTHORIZATION_CODE_PRESENTED_MORE_THAN_ONCE
                    , OAuthErrorCodesDescription.getErrorDescription(OAuthErrorCodeDescriptors.AUTHORIZATION_CODE_PRESENTED_MORE_THAN_ONCE));
        }
        else if (Instant.now().toEpochMilli() > oAuthAuthorization.getExpires())
        {
            throw new OAuthException(OAuthErrorCodeDescriptors.AUTHORIZATION_CODE_EXPIRED
                    , OAuthErrorCodesDescription.getErrorDescription(OAuthErrorCodeDescriptors.AUTHORIZATION_CODE_EXPIRED));
        }
            else if (!request.getClientId().equals(oAuthAuthorization.getClientId()))
            {
                throw new OAuthException(OAuthErrorCodeDescriptors.CLIENT_NOT_MATCHED
                        , OAuthErrorCodesDescription.getErrorDescription(OAuthErrorCodeDescriptors.CLIENT_NOT_MATCHED));
            }
            else if (!request.getUserId().equals(oAuthAuthorization.getUserId()))
            {
                throw new OAuthException(OAuthErrorCodeDescriptors.USER_NOT_MATCHED
                        , OAuthErrorCodesDescription.getErrorDescription(OAuthErrorCodeDescriptors.USER_NOT_MATCHED));
            }
        else if(!oAuthAuthorization.isUserValidated())
        {
            throw new OAuthException(OAuthErrorCodeDescriptors.USER_NOT_VALIDATED
            ,OAuthErrorCodesDescription.getErrorDescription(OAuthErrorCodeDescriptors.USER_NOT_VALIDATED));
        }
        else if (!UriBuilder.fromUri(request.getRedirectUri()).build().getHost().equals(UriBuilder.fromUri(oAuthAuthorization.getRedirectUri()).build().getHost()))
        {
            throw new OAuthException(OAuthErrorCodeDescriptors.REDIRECT_URI_NOT_MATCHED
                    , OAuthErrorCodesDescription.getErrorDescription(OAuthErrorCodeDescriptors.REDIRECT_URI_NOT_MATCHED));
        }
        else if (!request.getGrantType().equals(OAuthGrantType.AUTHORIZATION_CODE))
        {
            throw new OAuthException(OAuthErrorCodeDescriptors.UNSUPPORTED_GRANT_TYPE
                    , OAuthErrorCodesDescription.getErrorDescription(OAuthErrorCodeDescriptors.UNSUPPORTED_GRANT_TYPE));
        }

    }

    private void processAuthorizationCodePresentedMoreThanOnce(final OAuthAuthorization oAuthAuthorization)
    {
        this.oAuthAccessDao.deleteByAccessCodeHash(oAuthAuthorization.getAccessTokenHash());
        this.oAuthRefreshDao.deleteByRefreshCodeHash(oAuthAuthorization.getRefreshTokenHash());
        this.oAuthAuthorizationDao.deleteByAuthorizationCodeHash(oAuthAuthorization.getAuthorizationCodeHash());
    }
}
