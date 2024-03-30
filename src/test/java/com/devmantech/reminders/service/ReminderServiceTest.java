package com.devmantech.reminders.service;

import com.devmantech.reminders.dto.ReminderDTO;
import com.devmantech.reminders.exception.ReminderNotFoundException;
import com.devmantech.reminders.mapper.ReminderMapper;
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
    private ReminderMapper reminderMapper;

    @InjectMocks
    private ReminderService reminderService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Should return all ReminderDTO")
    void shouldReturnAllReminderDTO() {
        when(reminderRepository.findAll()).thenReturn(Collections.singletonList(new Reminder()));
        when(reminderMapper.toDTO(any(Reminder.class))).thenReturn(new ReminderDTO());

        assertEquals(1, reminderService.getAllReminders().size());
    }

    @Test
    @DisplayName("Should return a ReminderDTO by id")
    void shouldReturnReminderDtoById() {
        Reminder reminder = new Reminder();
        reminder.setId(1L);
        when(reminderRepository.findById(1L)).thenReturn(Optional.of(reminder));
        when(reminderMapper.toDTO(any(Reminder.class))).thenReturn(new ReminderDTO());

        assertNotNull(reminderService.getReminderById(1L));
    }

    @Test
    @DisplayName("Should add a ReminderDTO")
    void shouldAddReminderDTO() {
        ReminderDTO reminderDTO = new ReminderDTO();
        reminderDTO.setId(1L);
        Reminder reminder = new Reminder();
        reminder.setId(1L);
        when(reminderMapper.toEntity(reminderDTO)).thenReturn(reminder);
        when(reminderRepository.save(any(Reminder.class))).thenReturn(reminder);
        when(reminderMapper.toDTO(any(Reminder.class))).thenReturn(reminderDTO);

        assertEquals(reminderDTO.getId(), reminderService.addReminder(reminderDTO).getId());
    }

    @Test
    @DisplayName("Should update a ReminderDTO")
    void shouldUpdateReminderDTO() {
        ReminderDTO reminderDTO = new ReminderDTO();
        reminderDTO.setId(1L);
        Reminder reminder = new Reminder();
        reminder.setId(1L);
        when(reminderRepository.findById(1L)).thenReturn(Optional.of(reminder));
        when(reminderMapper.toEntity(reminderDTO)).thenReturn(reminder);
        when(reminderRepository.save(any(Reminder.class))).thenReturn(reminder);
        when(reminderMapper.toDTO(any(Reminder.class))).thenReturn(reminderDTO);

        assertEquals(reminderDTO.getId(), reminderService.updateReminder(1L, reminderDTO).getId());
    }

    @Test
    @DisplayName("Should update a ReminderDTO partially")
    void shouldUpdateReminderDtoPartially() {
        Reminder reminder = new Reminder();
        reminder.setId(1L);
        when(reminderRepository.findById(1L)).thenReturn(Optional.of(reminder));
        when(reminderRepository.save(any(Reminder.class))).thenReturn(reminder);
        when(reminderMapper.toDTO(any(Reminder.class))).thenReturn(new ReminderDTO());

        assertNotNull(reminderService.updateReminderPartially(1L, new ReminderDTO()));
    }

    @Test
    @DisplayName("Should delete an existing ReminderDTO")
    void shouldDeleteExistingReminderDTO() {
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
