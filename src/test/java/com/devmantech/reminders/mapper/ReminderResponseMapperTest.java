package com.devmantech.reminders.mapper;

import com.devmantech.reminders.dto.ReminderResponse;
import com.devmantech.reminders.model.CompletionStatus;
import com.devmantech.reminders.model.Priority;
import com.devmantech.reminders.model.Reminder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("ReminderResponseMapper Tests")
class ReminderResponseMapperTest {

    private final ModelMapper modelMapper = new ModelMapper();
    private final ReminderResponseMapper reminderResponseMapper = new ReminderResponseMapper(modelMapper);

    @Test
    @DisplayName("Should map a Reminder entity to ReminderResponse")
    void shouldMapReminderEntityToReminderResponse() {
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
        ReminderResponse reminderRequest = reminderResponseMapper.toDTO(reminder);

        // Then
        assertNotNull(reminderRequest);
        assertEquals(reminder.getId(), reminderRequest.getId());
        assertEquals(reminder.getTitle(), reminderRequest.getTitle());
        assertEquals(reminder.getNotes(), reminderRequest.getNotes());
        assertEquals(reminder.getCategory(), reminderRequest.getCategory());
        assertEquals(reminder.getLocation(), reminderRequest.getLocation());
        assertEquals(reminder.getPriority(), reminderRequest.getPriority());
        assertEquals(reminder.getCompletionStatus(), reminderRequest.getCompletionStatus());
        assertEquals(reminder.getDueDateTime(), reminderRequest.getDueDateTime());
    }

    @Test
    @DisplayName("Should map ReminderResponse to Reminder entity")
    void shouldMapReminderResponseToEntity() {
        // Given
        ReminderResponse reminderResponse = new ReminderResponse();
        reminderResponse.setId(1L);
        reminderResponse.setTitle("Test Reminder");
        reminderResponse.setNotes("Test notes");
        reminderResponse.setCategory("Test category");
        reminderResponse.setLocation("Test location");
        reminderResponse.setPriority(Priority.HIGH);
        reminderResponse.setCompletionStatus(CompletionStatus.NOT_COMPLETE);
        reminderResponse.setDueDateTime(LocalDateTime.of(2024, 3, 25, 12, 0));

        // When
        Reminder reminder = reminderResponseMapper.toEntity(reminderResponse);

        // Then
        assertNotNull(reminder);
        assertEquals(reminderResponse.getId(), reminder.getId());
        assertEquals(reminderResponse.getTitle(), reminder.getTitle());
        assertEquals(reminderResponse.getNotes(), reminder.getNotes());
        assertEquals(reminderResponse.getCategory(), reminder.getCategory());
        assertEquals(reminderResponse.getLocation(), reminder.getLocation());
        assertEquals(reminderResponse.getPriority(), reminder.getPriority());
        assertEquals(reminderResponse.getCompletionStatus(), reminder.getCompletionStatus());
        assertEquals(reminderResponse.getDueDateTime(), reminder.getDueDateTime());
    }
}
