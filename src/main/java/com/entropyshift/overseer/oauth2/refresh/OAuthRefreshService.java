package com.entropyshift.overseer.oauth2.refresh;

import com.entropyshift.PropertyNameConstants;
import com.entropyshift.configuration.IPropertiesProvider;
import com.entropyshift.overseer.oauth2.IRandomTokenGenerator;
import com.entropyshift.overseer.oauth2.access.IOAuthAccessDao;
import com.entropyshift.overseer.oauth2.access.OAuthAccess;
import com.entropyshift.overseer.oauth2.constants.OAuthErrorCodeDescriptors;
import com.entropyshift.overseer.oauth2.constants.OAuthErrorCodesDescription;
import com.entropyshift.overseer.oauth2.exceptions.OAuthException;
import com.entropyshift.overseer.oauth2.validation.AllowedRegexValidator;
import com.entropyshift.overseer.oauth2.validation.IOAuthValidator;
import com.entropyshift.overseer.oauth2.validation.RequiredFieldValidator;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
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
        oAuthRefreshValidation(request, oAuthRefresh);
        long currentTimestamp = Instant.now().toEpochMilli();

        String token = this.randomTokenGenerator.generateRandomToken();
        byte[] tokenHash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
        OAuthAccess oAuthAccess = new OAuthAccess();
        oAuthAccess.setAccessTokenHash(tokenHash);
        oAuthAccess.setClientId(request.getClientId());
        oAuthAccess.setUserId(request.getUserId());
        oAuthAccess.setCreatedTimestamp(currentTimestamp);
        oAuthAccess.setScope(oAuthRefresh.getScope());
        oAuthAccess.setClientState(oAuthRefresh.getClientState());
        oAuthAccess.setExpires(currentTimestamp + (Long.parseLong(this.propertiesProvider.getProperty(PropertyNameConstants.OAUTH_ACCESS_TOKEN_EXPIRES_IN_SECONDS)) * 1000));

        String refreshToken = this.randomTokenGenerator.generateRandomToken();
        byte[] refreshTokenHash = digest.digest(refreshToken.getBytes(StandardCharsets.UTF_8));
        OAuthRefresh nextOAuthRefresh = new OAuthRefresh();
        nextOAuthRefresh.setRefreshTokenHash(refreshTokenHash);
        nextOAuthRefresh.setAccessTokenHash(tokenHash);
        nextOAuthRefresh.setUserId(request.getUserId());
        nextOAuthRefresh.setClientId(request.getClientId());
        nextOAuthRefresh.setCreatedTimestamp(currentTimestamp);
        nextOAuthRefresh.setClientState(oAuthRefresh.getClientState());
        nextOAuthRefresh.setScope(oAuthRefresh.getScope());
        nextOAuthRefresh.setExpires(currentTimestamp + (Long.parseLong(this.propertiesProvider.getProperty(PropertyNameConstants.OAUTH_REFRESH_TOKEN_EXPIRES_IN_SECONDS)) * 1000));

        oAuthRefresh.setIssuedNextAccessTokenHash(tokenHash);
        oAuthRefresh.setIssuedNextRefreshTokenHash(refreshTokenHash);

        OAuthAccess currentOAuthAccessHash = this.oAuthAccessDao.getByAccessCodeHash(oAuthRefresh.getAccessTokenHash());
        if (currentOAuthAccessHash != null)
        {
            this.oAuthAccessDao.deleteByAccessCodeHash(currentOAuthAccessHash.getAccessTokenHash());
        }

        this.oAuthRefreshDao.insertOrUpdate(oAuthRefresh);
        this.oAuthAccessDao.insertOrUpdate(oAuthAccess);
        this.oAuthRefreshDao.insertOrUpdate(nextOAuthRefresh);

        return new OAuthRefreshResult(nextOAuthRefresh.getUserId(), nextOAuthRefresh.getClientId(), nextOAuthRefresh.getCreatedTimestamp()
                , token, refreshToken);

    }

    private void oAuthRefreshValidation(final OAuthRefreshRequest request, OAuthRefresh oAuthRefresh) throws OAuthException
    {
        if (oAuthRefresh == null)
        {
            throw new OAuthException(OAuthErrorCodeDescriptors.OAUTH_REFRESH_DETAILS_NOT_AVAILABLE
                    , OAuthErrorCodesDescription.getErrorDescription(OAuthErrorCodeDescriptors.AUTHORIZATION_DETAILS_NOT_AVAILABLE));
        }
        else if (oAuthRefresh.getIssuedNextAccessTokenHash() != null)
        {
            processRefreshTokenPresentedMoreThanOnce(oAuthRefresh);
            throw new OAuthException(OAuthErrorCodeDescriptors.REFRESH_TOKEN_PRESENTED_MORE_THAN_ONCE
                    , OAuthErrorCodesDescription.getErrorDescription(OAuthErrorCodeDescriptors.REFRESH_TOKEN_PRESENTED_MORE_THAN_ONCE));
        }
        else if (Instant.now().toEpochMilli() > oAuthRefresh.getExpires())
        {
            throw new OAuthException(OAuthErrorCodeDescriptors.REFRESH_TOKEN_EXPIRED
                    , OAuthErrorCodesDescription.getErrorDescription(OAuthErrorCodeDescriptors.REFRESH_TOKEN_EXPIRED));
        }
        else if (!request.getClientId().equals(oAuthRefresh.getClientId()))
        {
            throw new OAuthException(OAuthErrorCodeDescriptors.CLIENT_NOT_MATCHED
                    , OAuthErrorCodesDescription.getErrorDescription(OAuthErrorCodeDescriptors.CLIENT_NOT_MATCHED));
        }
        else if (!request.getUserId().equals(oAuthRefresh.getUserId()))
        {
            throw new OAuthException(OAuthErrorCodeDescriptors.USER_NOT_MATCHED
                    , OAuthErrorCodesDescription.getErrorDescription(OAuthErrorCodeDescriptors.USER_NOT_MATCHED));
        }
    }

    private void processRefreshTokenPresentedMoreThanOnce(final OAuthRefresh oAuthRefresh)
    {
        this.oAuthAccessDao.deleteByAccessCodeHash(oAuthRefresh.getIssuedNextAccessTokenHash());
        this.oAuthRefreshDao.deleteByRefreshCodeHash(oAuthRefresh.getIssuedNextRefreshTokenHash());
        this.oAuthRefreshDao.deleteByRefreshCodeHash(oAuthRefresh.getRefreshTokenHash());
    }
}
