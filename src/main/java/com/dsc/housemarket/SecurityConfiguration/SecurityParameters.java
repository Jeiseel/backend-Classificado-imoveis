package com.dsc.housemarket.SecurityConfiguration;

public class SecurityParameters {

    static final String SECRET = "TestingP4ssw0rD";
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
    static final String SIGNUP_URL = "/login";
    static final long EXPIRATION_TIME = 86400000L;
}
