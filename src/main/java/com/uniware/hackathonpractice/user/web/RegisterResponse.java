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

    public RegisterResponse(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}
