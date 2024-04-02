package com.devmantech.reminders.mapper;

import com.devmantech.reminders.dto.ReminderRequest;
import com.devmantech.reminders.model.CompletionStatus;
import com.devmantech.reminders.model.Priority;
import com.devmantech.reminders.model.Reminder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ReminderRequestMapper Tests")
class ReminderRequestMapperTest {

    private final ModelMapper modelMapper = new ModelMapper();
    private final ReminderRequestMapper reminderRequestMapper = new ReminderRequestMapper(modelMapper);

    @Value("${reminder.default-category-name}")
    private String defaultCategory;

    @Test
    @DisplayName("Should map a Reminder entity to ReminderRequest")
    void shouldMapReminderEntityToReminderRequest() {
        // Given
        Reminder reminder = new Reminder();
        reminder.setId(1L);
        reminder.setTitle("Test Reminder");
        reminder.setNotes("Test notes");
        reminder.setCategory("Test category");
        reminder.setLocation("Test location");
        reminder.setPriority(Priority.HIGH);
        reminder.setCompletionStatus(CompletionStatus.NOT_COMPLETE);
        reminder.setDueDateTime(LocalDateTime.of(2024, 3, 25, 12, 0));

        // When
        ReminderRequest reminderRequest = reminderRequestMapper.toDTO(reminder);

        // Then
        assertNotNull(reminderRequest);
        assertEquals(reminder.getTitle(), reminderRequest.getTitle());
        assertEquals(reminder.getNotes(), reminderRequest.getNotes());
        assertEquals(reminder.getCategory(), reminderRequest.getCategory());
        assertEquals(reminder.getLocation(), reminderRequest.getLocation());
        assertEquals(reminder.getPriority(), reminderRequest.getPriority());
        assertEquals(reminder.getCompletionStatus(), reminderRequest.getCompletionStatus());
        assertEquals(reminder.getDueDateTime(), reminderRequest.getDueDateTime());
    }

    @Test
    @DisplayName("Should map a full ReminderRequest (all field are set) to entity")
    void shouldMapFullReminderRequestToEntity() {
        // Given
        ReminderRequest reminderRequest = new ReminderRequest();
        reminderRequest.setTitle("Test Reminder");
        reminderRequest.setNotes("Test notes");
        reminderRequest.setCategory("Test category");
        reminderRequest.setLocation("Test location");
        reminderRequest.setPriority(Priority.HIGH);
        reminderRequest.setCompletionStatus(CompletionStatus.NOT_COMPLETE);
        reminderRequest.setDueDateTime(LocalDateTime.of(2024, 3, 25, 12, 0));

        // When
        Reminder reminder = reminderRequestMapper.toEntity(reminderRequest);

        // Then
        assertNotNull(reminder);
        assertEquals(reminderRequest.getTitle(), reminder.getTitle());
        assertEquals(reminderRequest.getNotes(), reminder.getNotes());
        assertEquals(reminderRequest.getCategory(), reminder.getCategory());
        assertEquals(reminderRequest.getLocation(), reminder.getLocation());
        assertEquals(reminderRequest.getPriority(), reminder.getPriority());
        assertEquals(reminderRequest.getCompletionStatus(), reminder.getCompletionStatus());
        assertEquals(reminderRequest.getDueDateTime(), reminder.getDueDateTime());
    }

    @Test
    @DisplayName("Should map a partial ReminderRequest (some fields are set) to entity")
    void shouldMapPartialReminderRequestToEntity() {
        // Given
        ReminderRequest reminderRequest = new ReminderRequest();
        reminderRequest.setTitle("Test Reminder");

        // When
        Reminder reminder = reminderRequestMapper.toEntity(reminderRequest);

        // Then
        assertNotNull(reminder);
        assertEquals(reminderRequest.getTitle(), reminder.getTitle());
        assertNull(reminder.getNotes());
        assertEquals(defaultCategory, reminder.getCategory());
        assertNull(reminder.getLocation());
        assertEquals(Priority.NONE, reminder.getPriority());
        assertEquals(CompletionStatus.NOT_COMPLETE, reminder.getCompletionStatus());
        assertNull(reminder.getDueDateTime());
    }
}
