package com.devmantech.reminders.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ErrorMessage {

    private final String errorCode;
    private final String errorMessage;
    private final HttpStatus httpStatus;
    private final LocalDateTime timestamp;

    public ErrorMessage(String errorCode, String errorMessage, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
        this.timestamp = LocalDateTime.now();
    }
}
