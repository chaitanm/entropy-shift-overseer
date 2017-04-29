package com.entropyshift.rest;

import com.entropyshift.PropertyNameConstants;
import com.entropyshift.configuration.IPropertiesProvider;
import com.entropyshift.overseer.auth.IAuthenticationService;
import com.entropyshift.overseer.auth.annotations.AuthorizeSession;
import com.entropyshift.overseer.auth.constants.AuthParameters;
import com.entropyshift.overseer.auth.constants.AuthStatusCodes;
import com.entropyshift.overseer.auth.request.AuthenticateAuthorizationCodeRequest;
import com.entropyshift.overseer.auth.request.AuthenticateUserRequest;
import com.entropyshift.overseer.auth.response.AuthenticateAuthorizationCodeResponse;
import com.entropyshift.overseer.auth.response.AuthenticateUserResponse;
import com.entropyshift.overseer.auth.response.ValidateSessionResponse;
import com.entropyshift.overseer.auth.result.AuthenticateUserResult;
import com.entropyshift.overseer.credentials.exceptions.IncorrectPasswordException;
import com.entropyshift.overseer.credentials.exceptions.UserCredentialsNotFoundException;
import com.entropyshift.overseer.crypto.jwt.IJsonWebTokenProvider;
import com.entropyshift.overseer.oauth2.constants.OAuthParameters;
import com.entropyshift.overseer.session.Session;
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
    private IJsonWebTokenProvider jsonWebTokenProvider;

    @Autowired
    private IPropertiesProvider propertiesProvider;


    @Autowired
    private IAuthenticationService authenticationService;

    private final String AUTH_SESSION_JWT_SUBJECT = "AUTH_SESSION";
    private static final String[] AUTH_SESSION_JWT_AUDIENCE = new String[]{"RESOURCE_ACCESS"};
    private static final String AUTH_AUTHORIZATION_CODE_SUBJECT = "AUTH_AUTHORIZATION_CODE";

    @Path("/authenticate")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @ApiOperation(value = "Authenticate User", response = AuthenticateUserResponse.class)
    public Response authenticate(@ApiParam AuthenticateUserRequest request, @Context HttpServletRequest servletRequest)
    {
        try
        {
            AuthenticateUserResult result = authenticationService.authenticate(request);
            if (!result.getStatus().equals(AuthStatusCodes.SUCCESS))
            {
                return Response.ok(new AuthenticateUserResponse(result.getRequestId(), result.getStatus()
                        , result.getUserInformation())).build();
            }
            Map<String, Object> claims = new HashMap<>();
            claims.put(AuthParameters.USER_ID, request.getUserId());
            claims.put(AuthParameters.SCOPE, result.getCreateSessionResult().getScope());
            claims.put(AuthParameters.WEBSITE, new URI(servletRequest.getRequestURI()).getHost());
            claims.put(AuthParameters.SESSION_KEY, result.getCreateSessionResult().getSessionKey());
            String token = this.jsonWebTokenProvider.generateToken(
                    this.propertiesProvider.getProperty(PropertyNameConstants.AUTH_ISSUER),
                    AUTH_SESSION_JWT_SUBJECT,
                    new ArrayList<>(Arrays.asList(AUTH_SESSION_JWT_AUDIENCE)),
                    result.getCreateSessionResult().getCreatedTimestamp(), claims
            );
            return Response.ok(new AuthenticateUserResponse(result.getRequestId(), AuthStatusCodes.SUCCESS
                    , result.getUserInformation()))
                    .status(Response.Status.OK)
                    .cookie(new NewCookie(this.propertiesProvider.getProperty(PropertyNameConstants.SESSION_COOKIE_NAME)
                            , token, "/", null, null, -1, false, true))
                    .build();
        }
        catch (UserCredentialsNotFoundException e)
        {
            return Response.ok(new AuthenticateUserResponse(request.getRequestId(), AuthStatusCodes.USER_NOT_FOUND, null))
                    .status(Response.Status.UNAUTHORIZED).build();
        }
        catch (IncorrectPasswordException e)
        {
            return Response.ok(new AuthenticateUserResponse(request.getRequestId(), AuthStatusCodes.INCORRECT_PASSWORD, null))
                    .status(Response.Status.UNAUTHORIZED).build();
        }
        catch (Exception e)
        {
            return Response.ok(new AuthenticateUserResponse(request.getRequestId(), AuthStatusCodes.SERVER_ERROR, null))
                    .status(Response.Status.INTERNAL_SERVER_ERROR).build();
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
            this.authenticationService.authenticateAuthorizationCode(request.getRequestId(), authorizationCode
                    , request.getUserId(), request.getPassword());
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
            final String sessionKey = (String) containerRequestContext.getProperty(PropertyNameConstants.SESSION_KEY);
            this.authenticationService.keepSessionAlive(sessionKey);
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
            this.authenticationService.logout(sessionKey);
            return Response.ok()
                    .status(Response.Status.OK)
                    .cookie(new NewCookie(this.propertiesProvider.getProperty(PropertyNameConstants.SESSION_COOKIE_NAME)
                            , "", "/", null, null, 0, false, true))
                    .build();
        }
        catch (Exception e)
        {
            return Response
                    .ok()
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
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
            this.authenticationService.deleteUserSessions(userId);
            return Response.ok().status(Response.Status.OK).build();
        }
        catch (Exception e)
        {
            return Response.ok().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
