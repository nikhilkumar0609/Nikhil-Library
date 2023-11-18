package com.springboot.processors.response;

import org.springframework.http.HttpStatus;

public class JsonResponse {
    private HttpStatus status;
    private int code;
    private String message;

    public JsonResponse(HttpStatus status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}


