package com.devmantech.reminders.service;

import com.devmantech.reminders.dto.ReminderRequest;
import com.devmantech.reminders.dto.ReminderResponse;
import com.devmantech.reminders.exception.ActionNotAllowedException;
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

    private ReminderRequest fullReminderRequest;
    private ReminderRequest partialReminderRequest;


    @BeforeEach
    void setUp() {
        fullReminderRequest = createFullReminderDTO();
        partialReminderRequest = createPartialReminderDTO();
    }

    @Test
    @DisplayName("Should return a list of all ReminderResponse")
    void shouldReturnAllReminderResponse() {
        List<ReminderResponse> remindersBefore = reminderService.getAllReminders();
        reminderService.addReminder(partialReminderRequest);
        List<ReminderResponse> remindersAfter = reminderService.getAllReminders();
        assertEquals(remindersBefore.size() + 1, remindersAfter.size());
    }

    @Test
    @DisplayName("Should add a complete ReminderRequest and return saved ReminderResponse")
    void shouldAddCompleteReminderDtoAndReturnIt() {
        ReminderResponse savedReminder = reminderService.addReminder(fullReminderRequest);
        assertAfterSavingFullReminderDTO(fullReminderRequest, savedReminder);
    }


    @Test
    @DisplayName("Should add a partial ReminderRequest and return saved ReminderResponse")
    void shouldAddPartialReminderDtoAndReturnIt() {
        ReminderResponse savedReminder = reminderService.addReminder(partialReminderRequest);
        assertAfterSavingPartialReminderDTO(partialReminderRequest, savedReminder);
    }

    @Test
    @DisplayName("Should update ReminderRequest and return saved ReminderResponse")
    void shouldUpdateReminderDtoAndReturnIt() {
        ReminderResponse savedReminder = reminderService.addReminder(partialReminderRequest);
        assertAfterSavingPartialReminderDTO(partialReminderRequest, savedReminder);

        ReminderRequest reminderRequestForUpdate = getReminderRequestFromResponse(savedReminder);

        ReminderResponse updatedReminder = reminderService.updateReminder(savedReminder.getId(), reminderRequestForUpdate);
        assertEquals(savedReminder.getId(), updatedReminder.getId());
        assertAfterSavingFullReminderDTO(reminderRequestForUpdate, updatedReminder);
    }


    @Test
    @DisplayName("Should update partial ReminderRequest and return saved ReminderResponse")
    void shouldUpdatePartialReminderRequestAndReturnIt() {
        ReminderResponse savedReminder = reminderService.addReminder(fullReminderRequest);
        assertAfterSavingFullReminderDTO(fullReminderRequest, savedReminder);

        ReminderRequest partialReminder = new ReminderRequest();
        partialReminder.setTitle(savedReminder.getTitle());
        partialReminder.setLocation("Test location");

        ReminderResponse updateReminder = reminderService.updateReminderPartially(savedReminder.getId(), partialReminder);

        assertEquals(savedReminder.getId(), updateReminder.getId());
        assertAfterSavingFullReminderDTO(fullReminderRequest, updateReminder);
    }

    @Test
    @DisplayName("Should complete ReminderRequest and return ReminderResponse")
    void shouldCompleteReminderRequestAndReturnIt() {
        ReminderResponse savedReminder = reminderService.addReminder(fullReminderRequest);
        assertAfterSavingFullReminderDTO(fullReminderRequest, savedReminder);

        ReminderResponse completedReminder = reminderService.completeReminder(savedReminder.getId());

        assertEquals(savedReminder.getId(), completedReminder.getId());
        assertEquals(CompletionStatus.COMPLETED, completedReminder.getCompletionStatus());
    }

    @Test
    @DisplayName("Should throw exception when completing a Reminder that is already complete")
    void shouldNotCompleteACompletedReminderAndThrowException() {
        //create a new reminder
        ReminderResponse savedReminder = reminderService.addReminder(fullReminderRequest);
        assertAfterSavingFullReminderDTO(fullReminderRequest, savedReminder);
        //update reminder status to complete
        savedReminder.setCompletionStatus(CompletionStatus.COMPLETED);
        ReminderRequest reminderToComplete = getReminderRequestFromResponse(savedReminder);
        reminderService.updateReminder(savedReminder.getId(), reminderToComplete);
        //try to complete it again via complete method
        assertThrows(ActionNotAllowedException.class, () -> reminderService.completeReminder(savedReminder.getId()));
    }

    @Test
    @DisplayName("Should delete a Reminder by id")
    void shouldDeleteReminderById() {
        List<ReminderResponse> remindersBefore = reminderService.getAllReminders();
        reminderService.deleteReminder(remindersBefore.get(0).getId());
        List<ReminderResponse> remindersAfter = reminderService.getAllReminders();
        assertEquals(remindersBefore.size() - 1, remindersAfter.size());
    }

    private void assertAfterSavingFullReminderDTO(ReminderRequest expectedReminder, ReminderResponse savedReminder) {
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

    private void assertAfterSavingPartialReminderDTO(ReminderRequest expectedReminder, ReminderResponse savedReminder) {
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

    private ReminderRequest createFullReminderDTO() {
        ReminderRequest reminderRequest = new ReminderRequest();
        reminderRequest.setTitle("Test Reminder");
        reminderRequest.setNotes("Test notes");
        reminderRequest.setCategory("Test category");
        reminderRequest.setLocation("Test location");
        reminderRequest.setPriority(Priority.HIGH);
        reminderRequest.setCompletionStatus(CompletionStatus.NOT_COMPLETE);
        reminderRequest.setDueDateTime(LocalDateTime.now().plusMinutes(1));
        return reminderRequest;
    }

    private ReminderRequest createPartialReminderDTO() {
        ReminderRequest reminderRequest = new ReminderRequest();
        reminderRequest.setTitle("Test Reminder");
        return reminderRequest;
    }

    private static ReminderRequest getReminderRequestFromResponse(ReminderResponse savedReminder) {
        ReminderRequest reminderRequest = new ReminderRequest();
        reminderRequest.setTitle(savedReminder.getTitle());
        reminderRequest.setNotes("Test notes");
        reminderRequest.setCategory(savedReminder.getCategory());
        reminderRequest.setLocation(savedReminder.getLocation());
        reminderRequest.setPriority(savedReminder.getPriority());
        reminderRequest.setDueDateTime(savedReminder.getDueDateTime());
        reminderRequest.setCompletionStatus(savedReminder.getCompletionStatus());
        return reminderRequest;
    }


}
