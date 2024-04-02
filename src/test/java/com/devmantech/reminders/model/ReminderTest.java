package com.devmantech.reminders.model;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DisplayName("Reminder test")
public class ReminderTest {

    @Test
    @DisplayName("Should objects be equal when all fields are the same")
    public void shouldBeEqualWithSameValues() {
        LocalDateTime now = LocalDateTime.now();
        Reminder reminder1 = getReminder(now);
        Reminder reminder2 = getReminder(now);

        assertEquals(reminder1, reminder2);
        assertEquals(reminder1.hashCode(), reminder2.hashCode());
    }

    @Test
    @DisplayName("Should objects not be equal when some fields differ")
    public void shouldNotBeEqualWithDifferentValues() {
        LocalDateTime now = LocalDateTime.now();
        Reminder reminder1 = getReminder(now);
        Reminder reminder2 = getReminder(now);
        reminder2.setCategory("category");
        assertNotEquals(reminder1, reminder2);
    }

    private Reminder getReminder(LocalDateTime now) {
        Reminder reminder = new Reminder();
        reminder.setTitle("Title");
        reminder.setNotes("Notes");
        reminder.setCategory("Category");
        reminder.setLocation("Location");
        reminder.setPriority(Priority.MEDIUM);
        reminder.setCompletionStatus(CompletionStatus.NOT_COMPLETE);
        reminder.setDueDateTime(now);
        return reminder;
    }
}
