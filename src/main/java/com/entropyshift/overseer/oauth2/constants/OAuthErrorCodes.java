package com.entropyshift.overseer.oauth2.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chaitanya.m on 1/10/17.
 */
public final class OAuthErrorCodes
{
    public static final String INVALID_REQUEST = "invalid_request";
    public static final String UNAUTHORIZED_CLIENT = "unauthorized_client";
    public static final String ACCESS_DENIED = "access_denied";
    public static final String UNSUPPORTED_RESPONSE_TYPE = "unsupported_response_type";
    public static final String INVALID_SCOPE = "invalid_scope";
    public static final String SERVER_ERROR = "server_error";
    public static final String TEMPORARILY_UNAVAILABLE = "temporarily_unavailable";
    public static final String INVALID_GRANT = "invalid_grant";
    public static final String UNSUPPORTED_GRANT_TYPE = "unsupported_grant_type";

    public static final Map<OAuthErrorCodeDescriptors, String>  errorCodeLookup;

    static
    {
        errorCodeLookup = new HashMap<>();
        errorCodeLookup.put(OAuthErrorCodeDescriptors.INVALID_REQUEST_INVALID_CONTENT_TYPE, INVALID_REQUEST);
        errorCodeLookup.put(OAuthErrorCodeDescriptors.INVALID_REQUEST_INVALID_HTTP_METHOD, INVALID_REQUEST);
        errorCodeLookup.put(OAuthErrorCodeDescriptors.INVALID_REQUEST_INVALID_PARAMETER_VALUE, INVALID_REQUEST);
        errorCodeLookup.put(OAuthErrorCodeDescriptors.INVALID_REQUEST_MALFORMED_REQUEST, INVALID_REQUEST);
        errorCodeLookup.put(OAuthErrorCodeDescriptors.INVALID_REQUEST_MISSING_PARAMETER, INVALID_REQUEST);
        errorCodeLookup.put(OAuthErrorCodeDescriptors.INVALID_REQUEST_PARAMETER_MORE_THAN_ONCE, INVALID_REQUEST);
        errorCodeLookup.put(OAuthErrorCodeDescriptors.UNAUTHORIZED_CLIENT, UNAUTHORIZED_CLIENT);
        errorCodeLookup.put(OAuthErrorCodeDescriptors.ACCESS_DENIED, ACCESS_DENIED);
        errorCodeLookup.put(OAuthErrorCodeDescriptors.UNSUPPORTED_RESPONSE_TYPE, UNSUPPORTED_RESPONSE_TYPE);
        errorCodeLookup.put(OAuthErrorCodeDescriptors.INVALID_SCOPE, INVALID_SCOPE);
        errorCodeLookup.put(OAuthErrorCodeDescriptors.SERVER_ERROR, SERVER_ERROR);
        errorCodeLookup.put(OAuthErrorCodeDescriptors.TEMPORARILY_UNAVAILABLE, TEMPORARILY_UNAVAILABLE);
        errorCodeLookup.put(OAuthErrorCodeDescriptors.AUTHORIZATION_DETAILS_NOT_AVAILABLE, INVALID_GRANT);
        errorCodeLookup.put(OAuthErrorCodeDescriptors.AUTHORIZATION_CODE_EXPIRED, INVALID_GRANT);
        errorCodeLookup.put(OAuthErrorCodeDescriptors.CLIENT_NOT_MATCHED, INVALID_GRANT);
        errorCodeLookup.put(OAuthErrorCodeDescriptors.USER_NOT_MATCHED, INVALID_GRANT);
        errorCodeLookup.put(OAuthErrorCodeDescriptors.AUTHORIZATION_CODE_PRESENTED_MORE_THAN_ONCE, INVALID_GRANT);
        errorCodeLookup.put(OAuthErrorCodeDescriptors.UNSUPPORTED_GRANT_TYPE, UNSUPPORTED_GRANT_TYPE);
        errorCodeLookup.put(OAuthErrorCodeDescriptors.REDIRECT_URI_NOT_MATCHED, INVALID_GRANT);
        errorCodeLookup.put(OAuthErrorCodeDescriptors.USER_NOT_VALIDATED, INVALID_GRANT);
    }
}
