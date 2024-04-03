package com.devmantech.reminders.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ReminderNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleReminderNotFoundException(ReminderNotFoundException ex) {
        ErrorMessage errorMessage = new ErrorMessage("NOT_FOUND_ERROR", ex.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ActionNotAllowedException.class})
    public ResponseEntity<ErrorMessage> handleActionNotAllowedException(ActionNotAllowedException ex) {
        ErrorMessage errorMessage = new ErrorMessage("ACTION_NOT_ALLOWED", ex.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(errorMessage, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorMessage> handleJsonConvertException(HttpMessageNotReadableException ex) {
        ErrorMessage errorMessage = new ErrorMessage("BAD_REQUEST_ERROR", ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
