package com.uniware.hackathonpractice.material.utility;

import lombok.Data;

@Data
public class ErrorResponse {
    String errorMessage;

    public ErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
