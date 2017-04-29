package com.entropyshift.overseer.auth.filter;

import com.entropyshift.PropertyNameConstants;
import com.entropyshift.configuration.IPropertiesProvider;
import com.entropyshift.overseer.auth.constants.AuthParameters;
import com.entropyshift.overseer.crypto.jwt.IJsonWebTokenProvider;
import com.entropyshift.overseer.session.ISessionService;
import com.entropyshift.overseer.session.Session;
import com.entropyshift.overseer.session.exceptions.InvalidSessionException;
import com.entropyshift.util.Lazy;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by chaitanya.m on 4/25/17.
 */
public class AuthorizationFilter implements ContainerRequestFilter
{
    @Autowired
    private IPropertiesProvider propertiesProvider;

    @Autowired
    private IJsonWebTokenProvider jsonWebTokenProvider;

    @Autowired
    private ISessionService sessionService;

    private final String AUTH_SESSION_JWT_AUDIENCE = "RESOURCE_ACCESS";

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException
    {
        final Lazy<String> sessionTokenHeader = new Lazy<>(() ->
        {
           List<String> sessionCookieHeaders =  containerRequestContext.getHeaders()
                    .get(propertiesProvider.getProperty(PropertyNameConstants.SESSION_COOKIE_NAME));

            return sessionCookieHeaders == null ? null : sessionCookieHeaders.stream().findFirst().get();
        });

        final Lazy<String> sessionCookie = new Lazy<>(() -> {
            Cookie cookie = containerRequestContext.getCookies()
                    .get(propertiesProvider.getProperty(PropertyNameConstants.SESSION_COOKIE_NAME));
            return  cookie == null ? null : cookie.getValue();

        } );
        final String sessionToken = sessionTokenHeader.get() != null ? sessionTokenHeader.get() : sessionCookie.get();

        try
        {
            if (sessionToken == null)
            {
                throw new Exception("Session Token Not Available");
            }

            Map<String, Object> claims = jsonWebTokenProvider.consumeToken(sessionToken
                    , propertiesProvider.getProperty(PropertyNameConstants.AUTH_ISSUER)
                    , AUTH_SESSION_JWT_AUDIENCE
                    , Long.parseLong(propertiesProvider.getProperty(PropertyNameConstants.SESSION_EXPIRES_IN_SECONDS)) * 1000);
            final String userId = claims.get(AuthParameters.USER_ID).toString();
            final String sessionKey = claims.get(AuthParameters.SESSION_KEY).toString();
            Session session = sessionService.validateSession(sessionKey);
            if (!session.getUserId().equals(userId))
            {
                throw new InvalidSessionException();
            }
            containerRequestContext.setProperty(PropertyNameConstants.SESSION_DATA, session);
            containerRequestContext.setProperty(PropertyNameConstants.SESSION_KEY, sessionKey);

        }
        catch (Exception e)
        {
            containerRequestContext.abortWith(Response
                    .status(Response.Status.UNAUTHORIZED)
                    .build());
        }
    }
}
