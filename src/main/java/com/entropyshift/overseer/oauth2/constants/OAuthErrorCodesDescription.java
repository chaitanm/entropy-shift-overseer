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
        errorDescriptionLookup = new HashMap<OAuthErrorCodeDescriptors, String>();

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

    }

    public String getErrorDescription(OAuthErrorCodeDescriptors errorCodeDescriptor, Object... args)
    {
        return String.format(errorDescriptionLookup.get(errorCodeDescriptor), args);
    }
}
