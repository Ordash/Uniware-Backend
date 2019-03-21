package com.uniware.hackathonpractice.security.auth.jwt.verifier;

public interface TokenVerifier {
    public boolean verify(String jti);
}