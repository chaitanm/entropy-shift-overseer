package com.entropyshift.rest;


import com.entropyshift.PropertyNameConstants;
import com.entropyshift.configuration.IPropertiesProvider;
import com.entropyshift.overseer.crypto.jwt.IJsonWebTokenProvider;
import com.entropyshift.overseer.oauth2.OAuthResponse;
import com.entropyshift.overseer.oauth2.access.*;
import com.entropyshift.overseer.oauth2.authorize.*;
import com.entropyshift.overseer.oauth2.constants.OAuthErrorCodes;
import com.entropyshift.overseer.oauth2.constants.OAuthParameters;
import com.entropyshift.overseer.oauth2.exceptions.OAuthException;
import com.entropyshift.overseer.oauth2.refresh.*;
import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.*;

/**
 * Created by chaitanya.m on 1/8/17.
 */
@Path("/oauth")
@Api(value = "/oauth", description = "OAuth 2.0 Specification - Implementation")
public class OAuth2Controller
{
    @Autowired
    private IOAuthAuthorizationService oAuthAuthorizationService;

    @Autowired
    private IOAuthAccessService oAuthAccessService;

    @Autowired
    private IOAuthRefreshService oAuthRefreshService;

    @Autowired
    private IJsonWebTokenProvider jsonWebTokenProvider;

    @Autowired
    private IPropertiesProvider propertiesProvider;


    private static final String OAUTH_AUTHORIZATION_JWT_SUBJECT = "OAUTH_AUTHORIZATION";
    private static final String[] OAUTH_AUTHORIZATION_JWT_AUDIENCE = new String[]{"OAUTH_ACCESS", "AUTH_AUTHORIZATION_CODE"};
    private static final String OAUTH_ACCESS_JWT_SUBJECT = "OAUTH_ACCESS";
    private static final String[] OAUTH_ACCESS_JWT_AUDIENCE = new String[]{"RESOURCE_ACCESS"};
    private static final String OAUTH_REFRESH_JWT_SUBJECT = "OAUTH_REFRESH";
    private static final String[] OAUTH_REFRESH_JWT_AUDIENCE = new String[]{"RESOURCE_ACCESS", "OAUTH_REFRESH"};

    @Path("/authorize")
    @GET
    @ApiOperation(value = "OAuth 2.0 Authorize Endpoint", code = 303)
    public Response authorize(
            @QueryParam("client_id") @ApiParam(value = "The Client's Id ", required = true) String clientId
            , @QueryParam("user_id") @ApiParam(value = "The User Id (Resource Owner Id) for which Client need Access", required = true) String userId
            , @QueryParam("scope") @ApiParam(value = "The Access Scope that defines Entitlements on User's Resources", required = true) String scope
            , @QueryParam("state") @ApiParam(value = "Can be the User's Session Hash on Client's Side - This Helps to Prevent CSRF Attacks", required = true) String state
            , @QueryParam("redirect_uri") @ApiParam(value = "Redirect URI of the Client", required = true) String redirectUri
            , @QueryParam("response_type") @ApiParam(value = "The value be one of \"code\" for requesting an authorization code and \"token\" for" +
            " requesting an access token (implicit grant)", required = true) String responseType)
    {
        OAuthAuthorizeRequest request = new OAuthAuthorizeRequest(responseType, UUID.fromString(clientId), userId, redirectUri, scope, state);
        OAuthResponse response = authorize(request);
        if (response instanceof OAuthAuthorizeErrorResponse)
        {
            OAuthAuthorizeErrorResponse errorResponse = (OAuthAuthorizeErrorResponse) response;
            URI uri = UriBuilder.fromUri(redirectUri)
                    .queryParam(OAuthParameters.ERROR, errorResponse.getError())
                    .queryParam(OAuthParameters.ERROR_DESCRIPTION, errorResponse.getErrorDescription() != null ? errorResponse.getErrorDescription() : "" )
                    .queryParam(OAuthParameters.ERROR_URI, errorResponse.getErrorUri() != null ? errorResponse.getErrorUri() : "")
                    .queryParam(OAuthParameters.STATE, errorResponse.getState())
                    .build();
            return Response.seeOther(uri).build();
        }
        else
        {
            OAuthAuthorizeSuccessResponse successResponse = (OAuthAuthorizeSuccessResponse) response;
            URI uri = UriBuilder.fromUri(redirectUri)
                    .queryParam(OAuthParameters.AUTHORIZATION_CODE, successResponse.getCode())
                    .queryParam(OAuthParameters.STATE, successResponse.getState())
                    .build();
            return Response.seeOther(uri).build();
        }
    }

    @Path("/access")
    @GET
    @ApiOperation(value = "OAuth 2.0 Access Token Endpoint", code = 303)
    public Response access(
            @QueryParam("grant_type") @ApiParam(value = "The value must be \"authorization_code\"", required = true) String grantType
            , @QueryParam("code") @ApiParam(value = "The authorization code received from the authorization server", required = true) String code
            , @QueryParam("redirect_uri") @ApiParam(value = "Redirect URI of the Client. The value must be same as " +
            "Redirect URI mentioned at the time of authorization", required = true) String redirectUri
            , @QueryParam("client_id") @ApiParam(value = "The Client's Id ", required = true) String clientId
            , @QueryParam("user_id") @ApiParam(value = "The User Id (Resource Owner Id) for which Client need Access", required = true) String userId)
    {
        String authorizationCode;
        try
        {
            Map<String, Object> claims = jsonWebTokenProvider.consumeToken(code, propertiesProvider.getProperty(PropertyNameConstants.OAUTH_ISSUER)
                    , OAUTH_ACCESS_JWT_SUBJECT
                    , Long.parseLong(propertiesProvider.getProperty(PropertyNameConstants.OAUTH_AUTHORIZATION_CODE_EXPIRES_IN_SECONDS)) * 1000);
            authorizationCode = claims.get(OAuthParameters.AUTHORIZATION_CODE).toString();
        }
        catch (Exception e)
        {
            URI uri = UriBuilder.fromUri(redirectUri)
                    .queryParam(OAuthParameters.ERROR, OAuthErrorCodes.SERVER_ERROR)
                    .queryParam(OAuthParameters.ERROR_DESCRIPTION, "")
                    .queryParam(OAuthParameters.ERROR_URI, "")
                    .build();
            return Response.seeOther(uri).build();
        }
        OAuthAccessRequest request = new OAuthAccessRequest(grantType, UUID.fromString(clientId), userId, redirectUri, authorizationCode);
        OAuthResponse response = access(request);
        if (response instanceof OAuthAccessErrorResponse)
        {
            OAuthAccessErrorResponse errorResponse = (OAuthAccessErrorResponse) response;
            URI uri = UriBuilder.fromUri(redirectUri)
                    .queryParam(OAuthParameters.ERROR, errorResponse.getError())
                    .queryParam(OAuthParameters.ERROR_DESCRIPTION, errorResponse.getErrorDescription() != null ? errorResponse.getError(): "")
                    .queryParam(OAuthParameters.ERROR_URI, errorResponse.getErrorUri() != null ? errorResponse.getErrorUri() : "")
                    .build();
            return Response.seeOther(uri).build();
        }
        else
        {
            OAuthAccessSuccessResponse successResponse = (OAuthAccessSuccessResponse) response;
            URI uri = UriBuilder.fromUri(redirectUri)
                    .queryParam(OAuthParameters.ACCESS_TOKEN, successResponse.getAccessToken())
                    .queryParam(OAuthParameters.REFRESH_TOKEN, successResponse.getRefreshToken())
                    .queryParam(OAuthParameters.TOKEN_TYPE, successResponse.getTokenType())
                    .queryParam(OAuthParameters.EXPIRES_IN, successResponse.getExpiresIn())
                    .build();
            return Response.seeOther(uri).build();
        }
    }

    @Path("/refresh")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "OAuth 2.0 Refresh Token Endpoint", response = Object.class)
    public JsonObject refresh(
            @QueryParam("client_id") @ApiParam(value = "The Client's Id ", required = true) String clientId
            , @QueryParam("user_id") @ApiParam(value = "The User Id (Resource Owner Id) for which Client need Access", required = true) String userId
            , @QueryParam("grant_type") @ApiParam(value = "The value MUST be set to \"refresh_token\"", required = true) String grantType
            , @QueryParam("refresh_token") @ApiParam(value = "Refresh Token", required = true) String refreshToken)
    {
        String extractedRefreshToken;
        try
        {
            Map<String, Object> claims = jsonWebTokenProvider.consumeToken(refreshToken, propertiesProvider.getProperty(PropertyNameConstants.OAUTH_ISSUER)
                    , OAUTH_REFRESH_JWT_SUBJECT
                    , Long.parseLong(propertiesProvider.getProperty(PropertyNameConstants.OAUTH_REFRESH_TOKEN_EXPIRES_IN_SECONDS)) * 1000);
            extractedRefreshToken = claims.get(OAuthParameters.REFRESH_TOKEN).toString();
        }
        catch (Exception exception)
        {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(OAuthParameters.ERROR, OAuthErrorCodes.SERVER_ERROR);
            jsonObject.addProperty(OAuthParameters.ERROR_DESCRIPTION, "");
            jsonObject.addProperty(OAuthParameters.ERROR_URI, "");
            return jsonObject;
        }
        OAuthRefreshRequest request = new OAuthRefreshRequest(UUID.fromString(clientId), userId, grantType, extractedRefreshToken);
        OAuthResponse response = refresh(request);
        if (response instanceof OAuthRefreshErrorResponse)
        {
            OAuthRefreshErrorResponse errorResponse = (OAuthRefreshErrorResponse) response;
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(OAuthParameters.ERROR, errorResponse.getError());
            jsonObject.addProperty(OAuthParameters.ERROR_DESCRIPTION, errorResponse.getErrorDescription());
            jsonObject.addProperty(OAuthParameters.ERROR_URI, errorResponse.getErrorUri());
            return jsonObject;
        }
        else
        {
            OAuthRefreshSuccessResponse successResponse = (OAuthRefreshSuccessResponse) response;
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(OAuthParameters.ACCESS_TOKEN, successResponse.getAccessToken());
            jsonObject.addProperty(OAuthParameters.REFRESH_TOKEN, successResponse.getRefreshToken());
            return jsonObject;
        }
    }

    private OAuthResponse authorize(OAuthAuthorizeRequest request)
    {
        try
        {
            OAuthAuthorizeResult result = oAuthAuthorizationService.authorize(request);
            Map<String, Object> claims = new HashMap<>();
            claims.put(OAuthParameters.AUTHORIZATION_CODE, result.getCode());
            claims.put(OAuthParameters.CLIENT_ID, result.getClientId());
            claims.put(OAuthParameters.USER_ID, result.getUserId());
            String token = jsonWebTokenProvider.generateToken(
                    propertiesProvider.getProperty(PropertyNameConstants.OAUTH_ISSUER),
                    OAUTH_AUTHORIZATION_JWT_SUBJECT,
                    new ArrayList<>(Arrays.asList(OAUTH_AUTHORIZATION_JWT_AUDIENCE)),
                    result.getCreatedTimeStamp(), claims);
            return new OAuthAuthorizeSuccessResponse(token, result.getClientState());
        }
        catch (OAuthException e)
        {
            return new OAuthAuthorizeErrorResponse(OAuthErrorCodes.errorCodeLookup.get(e.getErrorCodeDescriptor()).toString()
                    , e.getMessage(), null, request.getState());
        }
        catch (Exception e)
        {
            return new OAuthAuthorizeErrorResponse(OAuthErrorCodes.SERVER_ERROR, null, null, request.getState());
        }
    }

    private OAuthResponse access(OAuthAccessRequest request)
    {
        try
        {
            OAuthAccessResult result = oAuthAccessService.grantAccess(request);
            Map<String, Object> claims = new HashMap<>();
            claims.put(OAuthParameters.ACCESS_TOKEN, result.getAccessToken());
            claims.put(OAuthParameters.CLIENT_ID, result.getClientId());
            claims.put(OAuthParameters.USER_ID, result.getUserId());
            String token = jsonWebTokenProvider.generateToken(
                    propertiesProvider.getProperty(PropertyNameConstants.OAUTH_ISSUER),
                    OAUTH_ACCESS_JWT_SUBJECT,
                    new ArrayList<>(Arrays.asList(OAUTH_ACCESS_JWT_AUDIENCE)),
                    result.getCreatedTimestamp(), claims);
            Map<String, Object> refreshTokenClaims = new HashMap<>();
            refreshTokenClaims.put(OAuthParameters.REFRESH_TOKEN, result.getRefreshToken());
            refreshTokenClaims.put(OAuthParameters.CLIENT_ID, result.getClientId());
            refreshTokenClaims.put(OAuthParameters.USER_ID, result.getUserId());
            String refreshToken = jsonWebTokenProvider.generateToken(
                    propertiesProvider.getProperty(PropertyNameConstants.OAUTH_ISSUER),
                    OAUTH_REFRESH_JWT_SUBJECT,
                    new ArrayList<>(Arrays.asList(OAUTH_REFRESH_JWT_AUDIENCE)),
                    result.getCreatedTimestamp(), refreshTokenClaims);
            return new OAuthAccessSuccessResponse(token, result.getTokenType()
                    , Long.parseLong(propertiesProvider.getProperty(PropertyNameConstants.OAUTH_ACCESS_TOKEN_EXPIRES_IN_SECONDS)), refreshToken);

        }
        catch (OAuthException e)
        {
            return new OAuthAccessErrorResponse(OAuthErrorCodes.errorCodeLookup.get(e.getErrorCodeDescriptor()).toString()
                    , e.getMessage(), null, null);
        }
        catch (Exception e)
        {
            return new OAuthAccessErrorResponse(OAuthErrorCodes.SERVER_ERROR, null, null, null);
        }
    }

    private OAuthResponse refresh(OAuthRefreshRequest request)
    {
        try
        {
            OAuthRefreshResult result = oAuthRefreshService.refresh(request);
            Map<String, Object> claims = new HashMap<>();
            claims.put(OAuthParameters.ACCESS_TOKEN, result.getNextAccessToken());
            claims.put(OAuthParameters.CLIENT_ID, result.getClientId());
            claims.put(OAuthParameters.USER_ID, result.getUserId());
            String token = jsonWebTokenProvider.generateToken(
                    propertiesProvider.getProperty(PropertyNameConstants.OAUTH_ISSUER),
                    OAUTH_ACCESS_JWT_SUBJECT,
                    new ArrayList<>(Arrays.asList(OAUTH_ACCESS_JWT_AUDIENCE)),
                    result.getCreatedTimestamp(), claims);
            Map<String, Object> refreshTokenClaims = new HashMap<>();
            refreshTokenClaims.put(OAuthParameters.REFRESH_TOKEN, result.getNextRefreshToken());
            refreshTokenClaims.put(OAuthParameters.CLIENT_ID, result.getClientId());
            refreshTokenClaims.put(OAuthParameters.USER_ID, result.getUserId());
            String refreshToken = jsonWebTokenProvider.generateToken(
                    propertiesProvider.getProperty(PropertyNameConstants.OAUTH_ISSUER),
                    OAUTH_REFRESH_JWT_SUBJECT,
                    new ArrayList<>(Arrays.asList(OAUTH_REFRESH_JWT_AUDIENCE)),
                    result.getCreatedTimestamp(), refreshTokenClaims);
            return new OAuthRefreshSuccessResponse(token, refreshToken);
        }
        catch (OAuthException e)
        {
            return new OAuthRefreshErrorResponse(OAuthErrorCodes.errorCodeLookup.get(e.getErrorCodeDescriptor()).toString()
                    , e.getMessage(), null, null);
        }
        catch (Exception e)
        {
            return new OAuthRefreshErrorResponse(OAuthErrorCodes.SERVER_ERROR, null, null, null);
        }
    }

}
