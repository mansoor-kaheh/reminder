package com.devmantech.reminders.controller;

import com.devmantech.reminders.dto.ReminderModelAssembler;
import com.devmantech.reminders.dto.ReminderRequest;
import com.devmantech.reminders.dto.ReminderResponse;
import com.devmantech.reminders.exception.ReminderNotFoundException;
import com.devmantech.reminders.model.CompletionStatus;
import com.devmantech.reminders.model.Priority;
import com.devmantech.reminders.service.ReminderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ReminderController Tests")
class ReminderControllerTest {

    @Mock
    private ReminderService reminderService;
    @Mock
    private ReminderModelAssembler reminderModelAssembler;
    @InjectMocks
    private ReminderController reminderController;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Should return all Reminders")
    void shouldReturnAllReminders() {
        ReminderResponse reminderResponse = new ReminderResponse();
        when(reminderService.getAllReminders()).thenReturn(List.of(reminderResponse));

        ResponseEntity<CollectionModel<EntityModel<ReminderResponse>>> responseEntity = reminderController.getAllReminders();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        CollectionModel<EntityModel<ReminderResponse>> body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals(1, body.getContent().size());
    }

    @Test
    @DisplayName("Should return a Reminder by its Id")
    void shouldReturnReminderById() {
        ReminderResponse reminderResponse = new ReminderResponse();
        when(reminderService.getReminderById(1L)).thenReturn(reminderResponse);

        ResponseEntity<EntityModel<ReminderResponse>> responseEntity = reminderController.getReminderById(1L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Should create a Reminder and return it as result")
    void shouldCreateAndReturnReminder() {
        ReminderRequest reminderRequest = new ReminderRequest();
        ReminderResponse reminderResponse = new ReminderResponse();
        when(reminderService.addReminder(reminderRequest)).thenReturn(reminderResponse);
        when(reminderModelAssembler.toModel(any()))
                .thenReturn(
                        EntityModel.of(reminderResponse)
                                .add(Link.of("/reminders/1", IanaLinkRelations.SELF),
                                        Link.of("/reminders", "reminders")));
        ResponseEntity<EntityModel<ReminderResponse>> responseEntity = reminderController.createReminder(reminderRequest);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Should update an existing Reminder and return it as result")
    void shouldUpdateExistingReminder() {

        ReminderRequest reminderRequest = new ReminderRequest();

        ReminderResponse reminderResponse = new ReminderResponse();
        reminderResponse.setTitle("Test Reminder");
        reminderResponse.setNotes("Test notes");
        reminderResponse.setCategory("Test category");
        reminderResponse.setLocation("Test location");
        reminderResponse.setPriority(Priority.HIGH);
        reminderResponse.setCompletionStatus(CompletionStatus.NOT_COMPLETE);
        reminderResponse.setDueDateTime(LocalDateTime.now().plusMinutes(1));


        when(reminderModelAssembler.toModel(any()))
                .thenReturn(
                        EntityModel.of(reminderResponse)
                                .add(Link.of("/reminders/1", IanaLinkRelations.SELF),
                                        Link.of("/reminders", "reminders")));
        when(reminderService.updateReminder(1L, reminderRequest)).thenReturn(reminderResponse);

        ResponseEntity<EntityModel<ReminderResponse>> responseEntity = reminderController.updateReminder(1L, reminderRequest);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @Test
    @DisplayName("Should throw ReminderNotFoundException when updating a non-existing Reminder")
    void shouldThrowExceptionWhenUpdatingNonExistingReminder() {
        ReminderRequest reminderRequest = new ReminderRequest();
        when(reminderService.updateReminder(12345L, reminderRequest)).thenThrow(new ReminderNotFoundException(12345L));
        assertThrows(ReminderNotFoundException.class, () -> reminderController.updateReminder(12345L, reminderRequest));
    }

    @Test
    @DisplayName("Should update a partial Reminder and return the updated Reminder")
    void shouldUpdatePartialReminderAndReturnIt() {
        ReminderRequest reminderRequest = new ReminderRequest();
        ReminderResponse reminderResponse = new ReminderResponse();
        when(reminderService.updateReminderPartially(1L, reminderRequest)).thenReturn(reminderResponse);

        ResponseEntity<EntityModel<ReminderResponse>> responseEntity = reminderController.updateReminderPartially(1L, reminderRequest);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Should delete a Reminder")
    void shouldDeleteReminder() {
        assertDoesNotThrow(() -> reminderController.deleteReminder(1L));
        verify(reminderService, times(1)).deleteReminder(1L);
    }
}
