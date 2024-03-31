package com.devmantech.reminders.service;

import com.devmantech.reminders.dto.ReminderDTO;
import com.devmantech.reminders.model.CompletionStatus;
import com.devmantech.reminders.model.Priority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Tag("integration")
@DisplayName("ReminderService Integration Tests")
class ReminderServiceIntegrationTest {

    @Autowired
    private ReminderService reminderService;

    @Value("${reminder.default-category-name}")
    private String defaultCategory;

    private ReminderDTO fullReminderDTO;
    private ReminderDTO partialReminderDTO;


    @BeforeEach
    void setUp() {
        fullReminderDTO = createFullReminderDTO();
        partialReminderDTO = createPartialReminderDTO();
    }

    @Test
    @DisplayName("Should return a list of all ReminderDTO")
    void shouldReturnAllReminderDTO() {
        List<ReminderDTO> remindersBefore = reminderService.getAllReminders();
        reminderService.addReminder(partialReminderDTO);
        List<ReminderDTO> remindersAfter = reminderService.getAllReminders();
        assertEquals(remindersBefore.size() + 1, remindersAfter.size());
    }

    @Test
    @DisplayName("Should add a complete ReminderDTO and return saved ReminderDTO")
    void shouldAddCompleteReminderDtoAndReturnIt() {
        ReminderDTO savedReminder = reminderService.addReminder(fullReminderDTO);
        assertAfterSavingFullReminderDTO(fullReminderDTO, savedReminder);
    }


    @Test
    @DisplayName("Should add a partial ReminderDTO and return saved ReminderDTO")
    void shouldAddPartialReminderDtoAndReturnIt() {
        ReminderDTO savedReminder = reminderService.addReminder(partialReminderDTO);
        assertAfterSavingPartialReminderDTO(partialReminderDTO, savedReminder);
    }

    @Test
    @DisplayName("Should update ReminderDTO and return saved ReminderDTO")
    void shouldUpdateReminderDtoAndReturnIt() {
        ReminderDTO savedReminder = reminderService.addReminder(partialReminderDTO);
        assertAfterSavingPartialReminderDTO(partialReminderDTO, savedReminder);

        savedReminder.setNotes("Test notes");
        ReminderDTO updateReminder = reminderService.updateReminder(savedReminder.getId(), savedReminder);
        assertEquals(savedReminder.getId(), updateReminder.getId());
        assertAfterSavingFullReminderDTO(savedReminder, updateReminder);
    }

    @Test
    @DisplayName("Should update partial ReminderDTO and return saved ReminderDTO")
    void shouldUpdatePartialReminderDtoAndReturnIt() {
        ReminderDTO savedReminder = reminderService.addReminder(fullReminderDTO);
        assertAfterSavingFullReminderDTO(fullReminderDTO, savedReminder);

        ReminderDTO partialReminder = new ReminderDTO();
        partialReminder.setTitle(savedReminder.getTitle());
        partialReminder.setLocation("Test location");

        ReminderDTO updateReminder = reminderService.updateReminderPartially(savedReminder.getId(), partialReminder);

        assertEquals(savedReminder.getId(), updateReminder.getId());
        assertAfterSavingFullReminderDTO(savedReminder, updateReminder);
    }

    @Test
    @DisplayName("Should delete a ReminderDTO by id")
    void shouldDeleteReminderDtoById() {
        List<ReminderDTO> remindersBefore = reminderService.getAllReminders();
        reminderService.deleteReminder(remindersBefore.get(0).getId());
        List<ReminderDTO> remindersAfter = reminderService.getAllReminders();
        assertEquals(remindersBefore.size() - 1, remindersAfter.size());
    }

    private void assertAfterSavingFullReminderDTO(ReminderDTO expectedReminder, ReminderDTO savedReminder) {
        assertNotNull(savedReminder);
        assertNotNull(savedReminder.getId());
        assertEquals(expectedReminder.getTitle(), savedReminder.getTitle());
        assertEquals(expectedReminder.getNotes(), savedReminder.getNotes());
        assertEquals(expectedReminder.getCategory(), savedReminder.getCategory());
        assertEquals(expectedReminder.getLocation(), savedReminder.getLocation());
        assertEquals(expectedReminder.getPriority(), savedReminder.getPriority());
        assertEquals(expectedReminder.getCompletionStatus(), savedReminder.getCompletionStatus());
        assertEquals(expectedReminder.getDueDateTime(), savedReminder.getDueDateTime());
    }

    private void assertAfterSavingPartialReminderDTO(ReminderDTO expectedReminder, ReminderDTO savedReminder) {
        assertNotNull(savedReminder);
        assertNotNull(savedReminder.getId());
        assertEquals(expectedReminder.getTitle(), savedReminder.getTitle());
        assertNull(savedReminder.getNotes());
        assertEquals(defaultCategory, savedReminder.getCategory());
        assertNull(savedReminder.getLocation());
        assertEquals(Priority.NONE, savedReminder.getPriority());
        assertEquals(CompletionStatus.NOT_COMPLETE, savedReminder.getCompletionStatus());
        assertNull(savedReminder.getDueDateTime());
    }

    private ReminderDTO createFullReminderDTO() {
        ReminderDTO reminderDTO = new ReminderDTO();
        reminderDTO.setTitle("Test Reminder");
        reminderDTO.setNotes("Test notes");
        reminderDTO.setCategory("Test category");
        reminderDTO.setLocation("Test location");
        reminderDTO.setPriority(Priority.HIGH);
        reminderDTO.setCompletionStatus(CompletionStatus.NOT_COMPLETE);
        reminderDTO.setDueDateTime(LocalDateTime.now().plusMinutes(1));
        return reminderDTO;
    }

    private ReminderDTO createPartialReminderDTO() {
        ReminderDTO reminderDTO = new ReminderDTO();
        reminderDTO.setTitle("Test Reminder");
        return reminderDTO;
    }


}
