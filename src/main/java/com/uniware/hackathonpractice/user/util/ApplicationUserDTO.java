package com.uniware.hackathonpractice.user.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationUserDTO {

    Long id;

    @NotBlank
    String username;
    @NotBlank
    String password;

    List<Role> roles;

}