package com.devmantech.reminders.exception;

public class ReminderNotFoundException extends RuntimeException {
    public ReminderNotFoundException() {
    }

    public ReminderNotFoundException(Long id) {
        this(String.format("Reminder not found with id %d", id));
    }

    public ReminderNotFoundException(String message) {
        super(message);
    }

}
