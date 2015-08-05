package com.testtasks.blackJack.server.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse {

    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("success")
    private Boolean success = false;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorResponse(Error error) {
        this.code = error.getCode();
        this.message = error.getMessage();
    }
}
