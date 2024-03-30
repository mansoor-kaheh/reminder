package com.devmantech.reminders.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("GlobalExceptionHandler Tests")
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    @DisplayName("Should handle ReminderNotFound exception and return a NOT_FOUND http status")
    void shouldHandleReminderNotFoundException() {
        ReminderNotFoundException exception = new ReminderNotFoundException(1L);
        ResponseEntity<ErrorMessage> responseEntity = globalExceptionHandler.handleReminderNotFoundException(exception);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
