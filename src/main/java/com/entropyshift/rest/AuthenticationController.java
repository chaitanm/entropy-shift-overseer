package com.entropyshift.rest;

import com.entropyshift.PropertyNameConstants;
import com.entropyshift.configuration.IPropertiesProvider;
import com.entropyshift.overseer.auth.*;
import com.entropyshift.overseer.auth.annotations.AuthorizeSession;
import com.entropyshift.overseer.auth.constants.AuthParameters;
import com.entropyshift.overseer.auth.constants.AuthStatusCodes;
import com.entropyshift.overseer.credentials.ICredentialService;
import com.entropyshift.overseer.credentials.exceptions.IncorrectPasswordException;
import com.entropyshift.overseer.credentials.exceptions.UserCredentialsNotFoundException;
import com.entropyshift.overseer.crypto.jwt.IJsonWebTokenProvider;
import com.entropyshift.overseer.oauth2.authorize.IOAuthAuthorizationDao;
import com.entropyshift.overseer.oauth2.constants.OAuthParameters;
import com.entropyshift.overseer.session.CreateSessionResult;
import com.entropyshift.overseer.session.ISessionService;
import com.entropyshift.overseer.session.Session;
import com.entropyshift.user.profile.IUserInformationDao;
import com.entropyshift.user.profile.UserInformation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chaitanya.m on 2/9/17.
 */
@Path("/auth")
@Api(value = "/auth", description = "User Authentication")
public class AuthenticationController
{
    @Autowired
    private ICredentialService credentialService;

    @Autowired
    private ISessionService sessionService;

    @Autowired
    private IJsonWebTokenProvider jsonWebTokenProvider;

    @Autowired
    private IPropertiesProvider propertiesProvider;

    @Autowired
    private IUserInformationDao userInformationDao;

    @Autowired
    private IOAuthAuthorizationDao oAuthAuthorizationDao;

    private final String AUTH_SESSION_JWT_SUBJECT = "AUTH_SESSION";
    private static final String[] AUTH_SESSION_JWT_AUDIENCE = new String[]{"RESOURCE_ACCESS"};
    private static final String AUTH_AUTHORIZATION_CODE_SUBJECT = "AUTH_AUTHORIZATION_CODE";

    @Path("/authenticate")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @ApiOperation(value = "Authenticate User", response = AuthenticateUserResponse.class)
    public Response authenticate(@ApiParam AuthenticateUserRequest request, @Context HttpServletRequest servletRequest)
    {
        String requestId = request.getRequestId();
        try
        {
            this.credentialService.authenticateCredentials(request.getUserId(), request.getPassword());
            CreateSessionResult result = this.sessionService.createSession(request.getUserId()
                    , this.propertiesProvider.getProperty(PropertyNameConstants.SESSION_DEFAULT_SCOPE), null, null, null);
            Map<String, Object> claims = new HashMap<>();
            claims.put(AuthParameters.USER_ID, request.getUserId());
            claims.put(AuthParameters.SCOPE, result.getScope());
            claims.put(AuthParameters.WEBSITE, new URI(servletRequest.getRequestURI()).getHost());
            claims.put(AuthParameters.SESSION_KEY, result.getSessionKey());
            String token = this.jsonWebTokenProvider.generateToken(
                    this.propertiesProvider.getProperty(PropertyNameConstants.AUTH_ISSUER),
                    AUTH_SESSION_JWT_SUBJECT,
                    new ArrayList<>(Arrays.asList(AUTH_SESSION_JWT_AUDIENCE)),
                    result.getCreatedTimestamp(), claims
            );
            final UserInformation userInformation = userInformationDao.getByUserId(request.getUserId());
            return Response.ok(new AuthenticateUserResponse(requestId, AuthStatusCodes.SUCCESS, userInformation)).status(Response.Status.OK)
                    .cookie(new NewCookie(this.propertiesProvider.getProperty(PropertyNameConstants.SESSION_COOKIE_NAME), token)).build();
        }
        catch (UserCredentialsNotFoundException e)
        {
            return Response.ok(new AuthenticateUserResponse(requestId, AuthStatusCodes.USER_NOT_FOUND, null)).status(Response.Status.UNAUTHORIZED).build();
        }
        catch (IncorrectPasswordException e)
        {
            return Response.ok(new AuthenticateUserResponse(requestId, AuthStatusCodes.INCORRECT_PASSWORD, null)).status(Response.Status.UNAUTHORIZED).build();
        }
        catch (Exception e)
        {
            return Response.ok(new AuthenticateUserResponse(requestId, AuthStatusCodes.SERVER_ERROR, null)).status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path("/authenticate-authorization-code")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @ApiOperation(value = "Authenticate the Authorization Code Issued by OAuth API", response = AuthenticateUserResponse.class)
    public Response authenticateAuthorizationCode(@ApiParam AuthenticateAuthorizationCodeRequest request)
    {
        try
        {
            Map<String, Object> claims = jsonWebTokenProvider.consumeToken(request.getAuthorizationCode()
                    , propertiesProvider.getProperty(PropertyNameConstants.AUTH_ISSUER)
                    , AUTH_AUTHORIZATION_CODE_SUBJECT
                    , Long.parseLong(propertiesProvider.getProperty(PropertyNameConstants.OAUTH_AUTHORIZATION_CODE_EXPIRES_IN_SECONDS)) * 1000);
            final String authorizationCode = claims.get(OAuthParameters.AUTHORIZATION_CODE).toString();
            this.credentialService.authenticateCredentials(request.getUserId(), request.getPassword());
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] authorizationCodeHash = digest.digest(authorizationCode.getBytes(StandardCharsets.UTF_8));
            this.oAuthAuthorizationDao.updateUserValidatedFlag(authorizationCodeHash, true);
            return Response.ok(new AuthenticateAuthorizationCodeResponse(request.getRequestId(), AuthStatusCodes.SUCCESS)).build();
        }
        catch (IncorrectPasswordException e)
        {
            return Response.ok(new AuthenticateAuthorizationCodeResponse(request.getRequestId(), AuthStatusCodes.INCORRECT_PASSWORD))
                    .status(Response.Status.UNAUTHORIZED).build();
        }
        catch (Exception e)
        {
            return Response.ok(new AuthenticateAuthorizationCodeResponse(request.getRequestId(), AuthStatusCodes.SERVER_ERROR))
                    .status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path("/validate-session")
    @POST
    @AuthorizeSession
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Validates the session and Sets the session attributes in Response headers", response = Object.class)
    public Response validateSession(@Context ContainerRequestContext containerRequestContext)
    {
        final Session session = (Session) containerRequestContext.getProperty(PropertyNameConstants.SESSION_DATA);
        return Response.ok(new ValidateSessionResponse("", session.getUserId(), session.getScope())).build();
    }


    @Path("/keep-session-alive")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @AuthorizeSession
    @ApiOperation(value = "Keep Session Alive")
    public Response keepSessionAlive(@Context ContainerRequestContext containerRequestContext)
    {
        try
        {
            final String sessionKey = (String) containerRequestContext
                    .getProperty(PropertyNameConstants.SESSION_KEY);
            this.sessionService.extendSession(sessionKey);
            return Response.ok().status(Response.Status.OK).build();
        }
        catch (Exception ex)
        {
            return Response.ok().status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @AuthorizeSession
    @ApiOperation(value = "Logging Out The User")
    public Response logout(@Context ContainerRequestContext containerRequestContext)
    {
        try
        {
            final String sessionKey = (String) containerRequestContext
                    .getProperty(PropertyNameConstants.SESSION_KEY);
            this.sessionService.deleteSession(sessionKey);
            return Response.ok().status(Response.Status.OK).build();
        }
        catch (Exception e)
        {
            return Response.ok().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path("/delete-user-sessions")
    @Produces(MediaType.APPLICATION_JSON)
    @DELETE
    @ApiOperation(value = "Delete All Active Sessions of the User")
    public Response deleteUserSessions(@Context HttpServletRequest servletRequest)
    {
        String userId = servletRequest.getParameter(this.propertiesProvider
                .getProperty(PropertyNameConstants.SESSION_USER_ID_PARAMETER_NAME));
        try
        {
            this.sessionService.deleteUserSessions(userId);
            return Response.ok().status(Response.Status.OK).build();
        }
        catch (Exception e)
        {
            return Response.ok().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
