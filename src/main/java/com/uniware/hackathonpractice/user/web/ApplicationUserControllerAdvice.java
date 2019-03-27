package com.uniware.hackathonpractice.user.web;

import com.uniware.hackathonpractice.user.exceptions.EmailNotValidException;
import com.uniware.hackathonpractice.user.exceptions.UserNameOrEmailIsTakenException;
import com.uniware.hackathonpractice.user.exceptions.UserRoleNotFoundException;
import com.uniware.hackathonpractice.user.util.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(assignableTypes = ApplicationUserController.class)
public class ApplicationUserControllerAdvice {

    @ResponseBody
    @ExceptionHandler(UserNameOrEmailIsTakenException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorResponse usernameIsTakenHandler() {
        return new ErrorResponse("Username or Email already taken, please choose another one.");
    }

    @ResponseBody
    @ExceptionHandler(UserRoleNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse userRoleNotFoundHandler(UserRoleNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ErrorResponse userRoleNotFoundHandler(AccessDeniedException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(EmailNotValidException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ErrorResponse emailNotValidHandler(EmailNotValidException ex) {
        return new ErrorResponse(ex.getMessage());
    }

}
