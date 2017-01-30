package com.entropyshift.overseer.oauth2.constants;

/**
 * Created by chaitanya.m on 1/10/17.
 */
public enum OAuthErrorCodeDescriptors
{
    UNDEFINED,
    INVALID_REQUEST_INVALID_HTTP_METHOD,
    INVALID_REQUEST_MISSING_PARAMETER,
    INVALID_REQUEST_INVALID_PARAMETER_VALUE,
    INVALID_REQUEST_PARAMETER_MORE_THAN_ONCE,
    INVALID_REQUEST_INVALID_CONTENT_TYPE,
    INVALID_REQUEST_MALFORMED_REQUEST,
    INVALID_REQUEST_INVALID_GRANT_TYPE,
    UNSUPPORTED_RESPONSE_TYPE,
    UNAUTHORIZED_CLIENT,
    INVALID_SCOPE,
    ACCESS_DENIED,
    SERVER_ERROR,
    TEMPORARILY_UNAVAILABLE,
    AUTHORIZATION_DETAILS_NOT_AVAILABLE,
    AUTHORIZATION_CODE_EXPIRED,
    CLIENT_NOT_MATCHED,
    USER_NOT_MATCHED,
    AUTHORIZATION_CODE_PRESENTED_MORE_THAN_ONCE
}

