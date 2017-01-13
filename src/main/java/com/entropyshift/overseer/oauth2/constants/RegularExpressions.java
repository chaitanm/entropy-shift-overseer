package com.entropyshift.overseer.oauth2.constants;

/**
 * Created by chaitanya.m on 1/10/17.
 */
public final class RegularExpressions
{
    public static final String UrlWithoutFragment = "(@)?(href=')?(HREF=')?(HREF=\\\")?(href=\\\")?(http://)?" +
            "[a-zA-Z_0-9\\\\-]+(\\\\.\\\\w[a-zA-Z_0-9\\\\-]+)+(/[&\\\\n\\\\-=?\\\\+\\\\%/\\\\.\\\\w]+)?";
}
