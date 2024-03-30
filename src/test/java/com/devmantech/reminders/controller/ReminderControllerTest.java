package com.devmantech.reminders.controller;

import com.devmantech.reminders.dto.ReminderDTO;
import com.devmantech.reminders.dto.ReminderModelAssembler;
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
        ReminderDTO reminderDTO = new ReminderDTO();
        when(reminderService.getAllReminders()).thenReturn(List.of(reminderDTO));

        ResponseEntity<CollectionModel<EntityModel<ReminderDTO>>> responseEntity = reminderController.getAllReminders();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().getContent().size());
    }

    @Test
    @DisplayName("Should return a Reminder by its Id")
    void shouldReturnReminderById() {
        ReminderDTO reminderDTO = new ReminderDTO();
        when(reminderService.getReminderById(1L)).thenReturn(reminderDTO);

        ResponseEntity<EntityModel<ReminderDTO>> responseEntity = reminderController.getReminderById(1L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Should create a Reminder and return it as result")
    void shouldCreateAndReturnReminder() {
        ReminderDTO reminderDTO = new ReminderDTO();
        when(reminderService.addReminder(reminderDTO)).thenReturn(reminderDTO);
        when(reminderModelAssembler.toModel(any()))
                .thenReturn(
                        EntityModel.of(reminderDTO)
                                .add(Link.of("/reminders/1", IanaLinkRelations.SELF),
                                        Link.of("/reminders", "reminders")));
        ResponseEntity<EntityModel<ReminderDTO>> responseEntity = reminderController.createReminder(reminderDTO);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Should update an existing Reminder and return it as result")
    void shouldUpdateExistingReminder() {

        ReminderDTO reminderDTO = new ReminderDTO();
        reminderDTO.setTitle("Test Reminder");
        reminderDTO.setNotes("Test notes");
        reminderDTO.setCategory("Test category");
        reminderDTO.setLocation("Test location");
        reminderDTO.setPriority(Priority.HIGH);
        reminderDTO.setCompletionStatus(CompletionStatus.NOT_COMPLETE);
        reminderDTO.setDueDateTime(LocalDateTime.now().plusMinutes(1));

        when(reminderModelAssembler.toModel(any()))
                .thenReturn(
                        EntityModel.of(reminderDTO)
                                .add(Link.of("/reminders/1", IanaLinkRelations.SELF),
                                        Link.of("/reminders", "reminders")));
        when(reminderService.updateReminder(1L, reminderDTO)).thenReturn(reminderDTO);

        ResponseEntity<EntityModel<ReminderDTO>> responseEntity = reminderController.updateReminder(1L, reminderDTO);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

    }

    @Test
    @DisplayName("Should throw ReminderNotFoundException when updating a non-existing Reminder")
    void shouldThrowExceptionWhenUpdatingNonExistingReminder() {
        ReminderDTO reminderDTO = new ReminderDTO();
        reminderDTO.setId(12345L);
        when(reminderService.updateReminder(12345L, reminderDTO)).thenThrow(new ReminderNotFoundException(12345L));
        assertThrows(ReminderNotFoundException.class, () -> reminderController.updateReminder(12345L, reminderDTO));
    }

    @Test
    @DisplayName("Should update a partial Reminder and return the updated Reminder")
    void shouldUpdatePartialReminderAndReturnIt() {
        ReminderDTO reminderDTO = new ReminderDTO();
        when(reminderService.updateReminderPartially(1L, reminderDTO)).thenReturn(reminderDTO);

        ResponseEntity<EntityModel<ReminderDTO>> responseEntity = reminderController.updateReminderPartially(1L, reminderDTO);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Should delete a Reminder")
    void shouldDeleteReminder() {
        assertDoesNotThrow(() -> reminderController.deleteReminder(1L));
        verify(reminderService, times(1)).deleteReminder(1L);
    }
}
