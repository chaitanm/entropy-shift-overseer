package com.entropyshift.rest;

import com.entropyshift.PropertyNameConstants;
import com.entropyshift.configuration.IPropertiesProvider;
import com.entropyshift.overseer.auth.AuthenticateUserRequest;
import com.entropyshift.overseer.auth.AuthenticateUserResponse;
import com.entropyshift.overseer.auth.constants.AuthParameters;
import com.entropyshift.overseer.auth.constants.AuthStatusCodes;
import com.entropyshift.overseer.credentials.ICredentialService;
import com.entropyshift.overseer.credentials.exceptions.IncorrectPasswordException;
import com.entropyshift.overseer.credentials.exceptions.UserCredentialsNotFoundException;
import com.entropyshift.overseer.crypto.jwt.IJsonWebTokenProvider;
import com.entropyshift.overseer.session.CreateSessionResult;
import com.entropyshift.overseer.session.ISessionService;
import com.entropyshift.overseer.session.exceptions.SessionExpiredException;
import com.entropyshift.overseer.session.exceptions.SessionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
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

    private final String AUTH_SESSION_JWT_SUBJECT = "AUTH_SESSION";
    private static final String[] AUTH_SESSION_JWT_AUDIENCE = new String[]{"RESOURCE_ACCESS"};

    @Path("/authenticate")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response authenticate(AuthenticateUserRequest request, @Context HttpServletRequest servletRequest)
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
                    this.propertiesProvider.getProperty(PropertyNameConstants.OAUTH_ISSUER),
                    AUTH_SESSION_JWT_SUBJECT,
                    new ArrayList<>(Arrays.asList(AUTH_SESSION_JWT_AUDIENCE)),
                    result.getCreatedTimestamp(), claims
            );
            return Response.ok(new AuthenticateUserResponse(requestId, AuthStatusCodes.SUCCESS)).status(Response.Status.OK)
                    .cookie(new NewCookie(this.propertiesProvider.getProperty(PropertyNameConstants.SESSION_COOKIE_NAME),token)).build();
        }
        catch (UserCredentialsNotFoundException e)
        {
            return Response.ok(new AuthenticateUserResponse(requestId, AuthStatusCodes.USER_NOT_FOUND)).status(Response.Status.UNAUTHORIZED).build();
        }
        catch (IncorrectPasswordException e)
        {
            return Response.ok(new AuthenticateUserResponse(requestId, AuthStatusCodes.INCORRECT_PASSWORD)).status(Response.Status.UNAUTHORIZED).build();
        }
        catch (Exception e)
        {
            return Response.ok(new AuthenticateUserResponse(requestId, AuthStatusCodes.SERVER_ERROR)).status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path("/keepsessionalive")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response keepSessionAlive(@Context HttpServletRequest servletRequest)
    {
        try
        {
            this.sessionService.extendSession(servletRequest.getParameter(this.propertiesProvider.getProperty(PropertyNameConstants.SESSION_KEY_PARAMETER_NAME)));
            return Response.ok().status(Response.Status.OK).build();
        }
        catch (SessionNotFoundException | SessionExpiredException e)
        {
            return Response.ok().status(Response.Status.UNAUTHORIZED).build();
        }

    }

    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response logout(@Context HttpServletRequest servletRequest)
    {
        try
        {
            this.sessionService.deleteSession(servletRequest.getParameter(this.propertiesProvider.getProperty(PropertyNameConstants.SESSION_KEY_PARAMETER_NAME)));
            return Response.ok().status(Response.Status.OK).build();
        }
        catch(Exception e)
        {
            return Response.ok().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path("/deleteusersessions")
    @Produces(MediaType.APPLICATION_JSON)
    @DELETE
    public Response deleteUserSessions(@Context HttpServletRequest servletRequest)
    {
        String userId = servletRequest.getParameter(this.propertiesProvider.getProperty(PropertyNameConstants.SESSION_USER_ID_PARAMETER_NAME));
        try
        {
            this.sessionService.deleteUserSessions(userId);
            return Response.ok().status(Response.Status.OK).build();
        }
        catch(Exception e)
        {
            return Response.ok().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
