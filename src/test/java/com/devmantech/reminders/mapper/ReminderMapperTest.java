package com.devmantech.reminders.mapper;

import com.devmantech.reminders.dto.ReminderDTO;
import com.devmantech.reminders.model.CompletionStatus;
import com.devmantech.reminders.model.Priority;
import com.devmantech.reminders.model.Reminder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ReminderMapper Tests")
class ReminderMapperTest {

    private final ModelMapper modelMapper = new ModelMapper();
    private final ReminderMapper reminderMapper = new ReminderMapper(modelMapper);

    @Value("${reminder.default-category-name}")
    private String defaultCategory;

    @Test
    @DisplayName("Should map a Reminder entity to ReminderDTO")
    void shouldMapReminderEntityToReminderDTO() {
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
        ReminderDTO reminderDTO = reminderMapper.toDTO(reminder);

        // Then
        assertNotNull(reminderDTO);
        assertEquals(reminder.getId(), reminderDTO.getId());
        assertEquals(reminder.getTitle(), reminderDTO.getTitle());
        assertEquals(reminder.getNotes(), reminderDTO.getNotes());
        assertEquals(reminder.getCategory(), reminderDTO.getCategory());
        assertEquals(reminder.getLocation(), reminderDTO.getLocation());
        assertEquals(reminder.getPriority(), reminderDTO.getPriority());
        assertEquals(reminder.getCompletionStatus(), reminderDTO.getCompletionStatus());
        assertEquals(reminder.getDueDateTime(), reminderDTO.getDueDateTime());
    }

    @Test
    @DisplayName("Should map a full ReminderDTO (all field are set) to entity")
    void shouldMapFullReminderDtoToEntity() {
        // Given
        ReminderDTO reminderDTO = new ReminderDTO();
        reminderDTO.setId(1L);
        reminderDTO.setTitle("Test Reminder");
        reminderDTO.setNotes("Test notes");
        reminderDTO.setCategory("Test category");
        reminderDTO.setLocation("Test location");
        reminderDTO.setPriority(Priority.HIGH);
        reminderDTO.setCompletionStatus(CompletionStatus.NOT_COMPLETE);
        reminderDTO.setDueDateTime(LocalDateTime.of(2024, 3, 25, 12, 0));

        // When
        Reminder reminder = reminderMapper.toEntity(reminderDTO);

        // Then
        assertNotNull(reminder);
        assertEquals(reminderDTO.getId(), reminder.getId());
        assertEquals(reminderDTO.getTitle(), reminder.getTitle());
        assertEquals(reminderDTO.getNotes(), reminder.getNotes());
        assertEquals(reminderDTO.getCategory(), reminder.getCategory());
        assertEquals(reminderDTO.getLocation(), reminder.getLocation());
        assertEquals(reminderDTO.getPriority(), reminder.getPriority());
        assertEquals(reminderDTO.getCompletionStatus(), reminder.getCompletionStatus());
        assertEquals(reminderDTO.getDueDateTime(), reminder.getDueDateTime());
    }

    @Test
    @DisplayName("Should map a partial ReminderDTO (some fields are set) to entity")
    void shouldMapPartialReminderDtoToEntity() {
        // Given
        ReminderDTO reminderDTO = new ReminderDTO();
        reminderDTO.setId(1L);
        reminderDTO.setTitle("Test Reminder");

        // When
        Reminder reminder = reminderMapper.toEntity(reminderDTO);

        // Then
        assertNotNull(reminder);
        assertEquals(reminderDTO.getId(), reminder.getId());
        assertEquals(reminderDTO.getTitle(), reminder.getTitle());
        assertNull(reminder.getNotes());
        assertEquals(defaultCategory, reminder.getCategory());
        assertNull(reminder.getLocation());
        assertEquals(Priority.NONE, reminder.getPriority());
        assertEquals(CompletionStatus.NOT_COMPLETE, reminder.getCompletionStatus());
        assertNull(reminder.getDueDateTime());
    }
}
