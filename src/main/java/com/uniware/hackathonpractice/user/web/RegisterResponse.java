package com.uniware.hackathonpractice.user.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterResponse {

    private Long id;
    private String username;
    private String verification;

    public RegisterResponse(Long id, String username, String verification) {
        this.id = id;
        this.username = username;
        this.verification = verification;
    }

    public RegisterResponse(String verification) {
        this.verification = verification;
    }
}
