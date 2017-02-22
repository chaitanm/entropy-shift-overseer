package com.entropyshift;

/**
 * Created by chaitanya.m on 2/22/17.
 */
public class RegexConstants
{
    public static final String USER_ID_REGEX = "^[a-z0-9_-]{6,30}$";
    public static final String PASSWORD_REGEX = "^(?=.{8,50}$).*\\d.*$";
    public static final String ALPHANUMERIC_REGEX = "^[a-zA-Z0-9]*$";
    public static final String NUMERIC_REGEX = "^[0-9]*$";
    public static final String CHARACTER_REGEX = "^[a-zA-Z]*$";
}
