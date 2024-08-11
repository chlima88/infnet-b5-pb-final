package com.chlima.catalog.controller;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class RestErrorMessage {

    private LocalDateTime time;
    private int code;
    private HttpStatus status;
    private String message;


    public RestErrorMessage() {
        this.time = LocalDateTime.now();
    }


    public RestErrorMessage(HttpStatus status, String message) {
        this();
        this.code = status.value();
        this.status = status;
        this.message = message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
