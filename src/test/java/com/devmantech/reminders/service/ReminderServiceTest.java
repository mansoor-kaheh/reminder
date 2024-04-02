package com.devmantech.reminders.service;

import com.devmantech.reminders.dto.ReminderRequest;
import com.devmantech.reminders.dto.ReminderResponse;
import com.devmantech.reminders.exception.ReminderNotFoundException;
import com.devmantech.reminders.mapper.ReminderRequestMapper;
import com.devmantech.reminders.mapper.ReminderResponseMapper;
import com.devmantech.reminders.model.Reminder;
import com.devmantech.reminders.repository.ReminderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ReminderService Tests")
class ReminderServiceTest {

    @Mock
    private ReminderRepository reminderRepository;

    @Mock
    private ReminderRequestMapper reminderRequestMapper;
    @Mock
    private ReminderResponseMapper reminderResponseMapper;

    @InjectMocks
    private ReminderService reminderService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Should return all ReminderResponse")
    void shouldReturnAllReminderResponses() {
        when(reminderRepository.findAll()).thenReturn(Collections.singletonList(new Reminder()));
        when(reminderResponseMapper.toDTO(any(Reminder.class))).thenReturn(new ReminderResponse());

        assertEquals(1, reminderService.getAllReminders().size());
    }

    @Test
    @DisplayName("Should return a ReminderResponse by id")
    void shouldReturnReminderResponseById() {
        Reminder reminder = new Reminder();
        reminder.setId(1L);
        when(reminderRepository.findById(1L)).thenReturn(Optional.of(reminder));
        when(reminderResponseMapper.toDTO(any(Reminder.class))).thenReturn(new ReminderResponse());

        assertNotNull(reminderService.getReminderById(1L));
    }

    @Test
    @DisplayName("Should add a ReminderRequest")
    void shouldAddReminderRequest() {
        ReminderRequest reminderRequest = new ReminderRequest();
        ReminderResponse reminderResponse = new ReminderResponse();
        reminderResponse.setId(1L);
        Reminder reminder = new Reminder();
        reminder.setId(1L);
        when(reminderRequestMapper.toEntity(reminderRequest)).thenReturn(reminder);
        when(reminderRepository.save(any(Reminder.class))).thenReturn(reminder);
        when(reminderResponseMapper.toDTO(any(Reminder.class))).thenReturn(reminderResponse);

        assertEquals(reminder.getId(), reminderService.addReminder(reminderRequest).getId());
    }

    @Test
    @DisplayName("Should update a ReminderRequest")
    void shouldUpdateReminderRequest() {
        ReminderRequest reminderRequest = new ReminderRequest();
        ReminderResponse reminderResponse = new ReminderResponse();
        reminderResponse.setId(1L);
        Reminder reminder = new Reminder();
        reminder.setId(1L);
        when(reminderRepository.findById(1L)).thenReturn(Optional.of(reminder));
        when(reminderRequestMapper.toEntity(reminderRequest)).thenReturn(reminder);
        when(reminderRepository.save(any(Reminder.class))).thenReturn(reminder);
        when(reminderResponseMapper.toDTO(any(Reminder.class))).thenReturn(reminderResponse);

        assertEquals(reminderResponse.getId(), reminderService.updateReminder(1L, reminderRequest).getId());
    }

    @Test
    @DisplayName("Should update a ReminderRequest partially")
    void shouldUpdateReminderRequestPartially() {
        Reminder reminder = new Reminder();
        reminder.setId(1L);
        when(reminderRepository.findById(1L)).thenReturn(Optional.of(reminder));
        when(reminderRepository.save(any(Reminder.class))).thenReturn(reminder);
        when(reminderResponseMapper.toDTO(any(Reminder.class))).thenReturn(new ReminderResponse());

        assertNotNull(reminderService.updateReminderPartially(1L, new ReminderRequest()));
    }

    @Test
    @DisplayName("Should delete an existing Reminder")
    void shouldDeleteExistingReminder() {
        Reminder reminder = new Reminder();
        reminder.setId(1L);
        when(reminderRepository.findById(1L)).thenReturn(Optional.of(reminder));

        assertDoesNotThrow(() -> reminderService.deleteReminder(1L));
    }

    @Test
    @DisplayName("Should throw ReminderNotFoundException when deleting a non-existing Reminder")
    void shouldThrowExceptionWhenDeletingNonExistingReminder() {
        when(reminderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ReminderNotFoundException.class, () -> reminderService.deleteReminder(1L));
    }
}
