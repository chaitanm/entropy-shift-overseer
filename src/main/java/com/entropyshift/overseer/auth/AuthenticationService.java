package com.entropyshift.overseer.auth;

import com.entropyshift.PropertyNameConstants;
import com.entropyshift.configuration.IPropertiesProvider;
import com.entropyshift.overseer.auth.constants.AuthStatusCodes;
import com.entropyshift.overseer.auth.request.AuthenticateUserRequest;
import com.entropyshift.overseer.auth.result.AuthenticateUserResult;
import com.entropyshift.overseer.credentials.ICredentialService;
import com.entropyshift.overseer.credentials.exceptions.IncorrectPasswordException;
import com.entropyshift.overseer.credentials.exceptions.PasswordHashGeneratorNotFoundException;
import com.entropyshift.overseer.credentials.exceptions.UserCredentialsNotFoundException;
import com.entropyshift.overseer.oauth2.authorize.IOAuthAuthorizationDao;
import com.entropyshift.overseer.session.CreateSessionResult;
import com.entropyshift.overseer.session.ISessionService;
import com.entropyshift.overseer.session.exceptions.SessionExpiredException;
import com.entropyshift.overseer.session.exceptions.SessionNotFoundException;
import com.entropyshift.user.profile.IUserInformationDao;
import com.entropyshift.user.profile.UserInformation;
import com.entropyshift.user.profile.UserStatus;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by chaitanya.m on 4/28/17.
 */
public class AuthenticationService implements IAuthenticationService
{
    private ICredentialService credentialService;

    private IUserInformationDao userInformationDao;

    private IPropertiesProvider propertiesProvider;

    private ISessionService sessionService;

    private IOAuthAuthorizationDao oAuthAuthorizationDao;

    public AuthenticationService(ICredentialService credentialService, ISessionService sessionService
            , IUserInformationDao userInformationDao, IPropertiesProvider propertiesProvider
            , IOAuthAuthorizationDao oAuthAuthorizationDao)
    {
        this.credentialService = credentialService;
        this.sessionService = sessionService;
        this.userInformationDao = userInformationDao;
        this.propertiesProvider = propertiesProvider;
        this.oAuthAuthorizationDao = oAuthAuthorizationDao;

    }

    @Override
    public AuthenticateUserResult authenticate(AuthenticateUserRequest request)
            throws PasswordHashGeneratorNotFoundException, IncorrectPasswordException
            , UserCredentialsNotFoundException
    {
        final String requestId = request.getRequestId();
        this.credentialService.authenticateCredentials(request.getUserId(), request.getPassword());
        final UserInformation userInformation = userInformationDao.getByUserId(request.getUserId());
        if (userInformation.getStatus() != UserStatus.ACTIVE)
        {
            String authStatus;
            switch (userInformation.getStatus())
            {
                case PENDING_EMAIL_VERIFICATION:
                    authStatus = AuthStatusCodes.PENDING_EMAIL_VERIFICATION;
                    break;
                case INACTIVE:
                    authStatus = AuthStatusCodes.USER_INACTIVE;
                    break;
                case DISABLED:
                    authStatus = AuthStatusCodes.USER_DISABLED;
                    break;
                default:
                    authStatus = AuthStatusCodes.SERVER_ERROR;
                    break;
            }
            return new AuthenticateUserResult(requestId, authStatus, userInformation);
        }
        CreateSessionResult result = this.sessionService.createSession(request.getUserId()
                , this.propertiesProvider.getProperty(PropertyNameConstants.SESSION_DEFAULT_SCOPE), null, null, null);
        return new AuthenticateUserResult(requestId, AuthStatusCodes.SUCCESS, userInformation, result);

    }

    @Override
    public void authenticateAuthorizationCode(final String requestId
            , final String authorizationCode, final String username, final String password) throws PasswordHashGeneratorNotFoundException
            , IncorrectPasswordException, UserCredentialsNotFoundException, NoSuchAlgorithmException
    {
        this.credentialService.authenticateCredentials(username, password);
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        final byte[] authorizationCodeHash = digest.digest(authorizationCode.getBytes(StandardCharsets.UTF_8));
        this.oAuthAuthorizationDao.updateUserValidatedFlag(authorizationCodeHash, true);
    }

    @Override
    public void keepSessionAlive(final String sessionKey) throws SessionExpiredException, SessionNotFoundException
    {
        this.sessionService.extendSession(sessionKey);
    }

    @Override
    public void logout(final String sessionKey)
    {
        this.sessionService.deleteSession(sessionKey);
    }

    @Override
    public void deleteUserSessions(final String userId)
    {
        this.sessionService.deleteUserSessions(userId);
    }
}
