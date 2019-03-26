package com.uniware.hackathonpractice.security;

public class SecurityConstants {
    public static final String TOKEN_SIGNING_KEY = System.getenv("SIGNING_KEY"); // environment variable
    public static final String AUTHENTICATION_HEADER_NAME = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTHENTICATION_URL = "/api/auth/login";
    public static final String REGISTRATION_URL = "/api/user/register";
    public static final String REFRESH_TOKEN_URL = "/api/auth/token";
    public static final String API_ROOT_URL = "/api/**";
    public static final String TOKEN_ISSUER = "UNIWARE";
    public static final Long ACCESS_TOKEN_EXP_TIME = 60L; // 10min
    public static final Long REFRESH_TOKEN_EXP_TIME = 20L; //20min

}
