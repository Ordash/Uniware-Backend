package com.uniware.hackathonpractice.user.web;

import com.uniware.hackathonpractice.user.exceptions.EmailNotValidException;
import com.uniware.hackathonpractice.user.exceptions.UserNameIsTakenException;
import com.uniware.hackathonpractice.user.exceptions.UserRoleNotFoundException;
import com.uniware.hackathonpractice.user.service.ApplicationUserService;
import com.uniware.hackathonpractice.user.util.ApplicationUserDTO;
import com.uniware.hackathonpractice.user.util.UserListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class ApplicationUserController {

    private ApplicationUserService applicationUserService;

    @Autowired
    public ApplicationUserController(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public RegisterResponse registerUser(@RequestBody @Valid ApplicationUserDTO applicationUserDTO)
            throws MethodArgumentNotValidException, UserNameIsTakenException, UserRoleNotFoundException, EmailNotValidException {
        return applicationUserService.registerApplicationUser(applicationUserDTO);
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public UserListDTO listUsers() {
        return applicationUserService.listUsers();
    }
}
