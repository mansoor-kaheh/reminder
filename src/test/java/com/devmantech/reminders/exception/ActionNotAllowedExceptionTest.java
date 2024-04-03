package com.devmantech.reminders.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("ActionNotAllowedException Tests")
public class ActionNotAllowedExceptionTest {

    @Test
    @DisplayName("Should create exception with default constructor with no message")
    void ShouldCreateExceptionWithoutMessage() {
        ActionNotAllowedException exception = new ActionNotAllowedException();
        assertNull(exception.getMessage());
    }

    @Test
    @DisplayName("Should create exception with message")
    void ShouldCreateExceptionWithMessage() {
        String message = "Test message";
        ActionNotAllowedException exception = new ActionNotAllowedException(message);
        assertEquals(message, exception.getMessage());
    }
}
