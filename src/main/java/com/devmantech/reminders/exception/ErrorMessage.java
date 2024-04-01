package com.devmantech.reminders.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Schema(name = "ErrorResponse", description = "Custom errors for handled exceptions")
public class ErrorMessage {

    @Schema(name = "errorCode", example = "NOT_FOUND_ERROR", description = "Error code of application")
    private final String errorCode;

    @Schema(name = "errorMessage", example = "Reminder not found with id 111111", description = "Error description of application")
    private final String errorMessage;

    @Schema(name = "httpStatus", example = "NOT_FOUND", description = "Http status code")
    private final HttpStatus httpStatus;

    @Schema(name = "timestamp", example = "2024-03-31 22:50:14", description = "Timestamp of error", pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime timestamp;

    public ErrorMessage(String errorCode, String errorMessage, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
        this.timestamp = LocalDateTime.now();
    }
}
