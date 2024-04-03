package com.devmantech.reminders.exception;

public class ActionNotAllowedException extends RuntimeException {
    public ActionNotAllowedException() {
        super();
    }

    public ActionNotAllowedException(String message) {
        super(message);
    }
}
