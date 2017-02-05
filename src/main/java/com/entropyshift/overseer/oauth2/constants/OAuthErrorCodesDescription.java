package com.entropyshift.overseer.oauth2.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chaitanya.m on 1/10/17.
 */
public final class OAuthErrorCodesDescription
{
    private static final Map<OAuthErrorCodeDescriptors, String> errorDescriptionLookup;

    static
    {
        errorDescriptionLookup = new HashMap<>();

        errorDescriptionLookup.put(OAuthErrorCodeDescriptors.INVALID_REQUEST_INVALID_HTTP_METHOD
                , "The request http method is invalid.");
        errorDescriptionLookup.put(OAuthErrorCodeDescriptors.INVALID_REQUEST_MISSING_PARAMETER
                , "The request is missing a required parameter : %s");
        errorDescriptionLookup.put(OAuthErrorCodeDescriptors.INVALID_REQUEST_INVALID_PARAMETER_VALUE
                , "The request includes an invalid parameter value %s for parameter %s");
        errorDescriptionLookup.put(OAuthErrorCodeDescriptors.INVALID_REQUEST_MALFORMED_REQUEST
                , "The request is malformed ");
        errorDescriptionLookup.put(OAuthErrorCodeDescriptors.INVALID_REQUEST_PARAMETER_MORE_THAN_ONCE
                , "The request includes a parameter more than once : %s");
        errorDescriptionLookup.put(OAuthErrorCodeDescriptors.INVALID_REQUEST_INVALID_CONTENT_TYPE
                , "The request has invalid content type");
        errorDescriptionLookup.put(OAuthErrorCodeDescriptors.INVALID_REQUEST_INVALID_GRANT_TYPE
                , "The request has invalid grant type");
        errorDescriptionLookup.put(OAuthErrorCodeDescriptors.UNSUPPORTED_RESPONSE_TYPE
                , "The authorization server does not support obtaining an authorization code using this method.");
        errorDescriptionLookup.put(OAuthErrorCodeDescriptors.ACCESS_DENIED
                , "The resource owner or authorization server denied the request.");
        errorDescriptionLookup.put(OAuthErrorCodeDescriptors.UNAUTHORIZED_CLIENT
                , "Not Authorized.");
        errorDescriptionLookup.put(OAuthErrorCodeDescriptors.INVALID_SCOPE
                , "Invalid Scope.");
        errorDescriptionLookup.put(OAuthErrorCodeDescriptors.TEMPORARILY_UNAVAILABLE
                , "Service Temporarily Unavailable.");
        errorDescriptionLookup.put(OAuthErrorCodeDescriptors.SERVER_ERROR
                , "Server Error.");
        errorDescriptionLookup.put(OAuthErrorCodeDescriptors.AUTHORIZATION_DETAILS_NOT_AVAILABLE
                , "Authorization details not available.");
        errorDescriptionLookup.put(OAuthErrorCodeDescriptors.AUTHORIZATION_CODE_EXPIRED
                , "Authorization code expired.");
        errorDescriptionLookup.put(OAuthErrorCodeDescriptors.CLIENT_NOT_MATCHED
                , "Client not matched.");
        errorDescriptionLookup.put(OAuthErrorCodeDescriptors.USER_NOT_MATCHED
                , "User not matched.");
        errorDescriptionLookup.put(OAuthErrorCodeDescriptors.AUTHORIZATION_CODE_PRESENTED_MORE_THAN_ONCE
                , "Authorization code presented more than once.");
        errorDescriptionLookup.put(OAuthErrorCodeDescriptors.UNSUPPORTED_GRANT_TYPE
                , "Unsupported grant type.");
        errorDescriptionLookup.put(OAuthErrorCodeDescriptors.REDIRECT_URI_NOT_MATCHED
                , "Redirect Uri not matched.");
        errorDescriptionLookup.put(OAuthErrorCodeDescriptors.USER_NOT_VALIDATED
                , "User not validated.");
        errorDescriptionLookup.put(OAuthErrorCodeDescriptors.OAUTH_REFRESH_DETAILS_NOT_AVAILABLE
                , "OAuth Refresh details not available.");
        errorDescriptionLookup.put(OAuthErrorCodeDescriptors.REFRESH_TOKEN_PRESENTED_MORE_THAN_ONCE
                , "Refresh token presented more than once.");
        errorDescriptionLookup.put(OAuthErrorCodeDescriptors.REFRESH_TOKEN_EXPIRED
                , "Refresh token expired");

    }

    public static String getErrorDescription(OAuthErrorCodeDescriptors errorCodeDescriptor, Object... args)
    {
        return String.format(errorDescriptionLookup.get(errorCodeDescriptor), args);
    }
}
