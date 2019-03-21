package com.uniware.hackathonpractice.security.auth.jwt.extractor;

public interface TokenExtractor {
    String extract(String payload);
}
