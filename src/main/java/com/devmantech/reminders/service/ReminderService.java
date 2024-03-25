package com.devmantech.reminders.service;

import com.devmantech.reminders.dto.ReminderDTO;
import com.devmantech.reminders.mapper.ReminderMapper;
import com.devmantech.reminders.model.Reminder;
import com.devmantech.reminders.repository.ReminderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final ReminderMapper reminderMapper;

    public List<ReminderDTO> getAllReminders() {
        return reminderRepository.findAll().stream().map(reminderMapper::toDTO).collect(Collectors.toList());
    }

    public ReminderDTO getReminderById(Long id) {
        Reminder existingReminder = reminderRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Reminder not found with id " + id));
        return reminderMapper.toDTO(existingReminder);
    }

    public ReminderDTO addReminder(ReminderDTO reminderDTO) {
        Reminder reminder = reminderMapper.toEntity(reminderDTO);
        Reminder savedReminder = reminderRepository.save(reminder);
        return reminderMapper.toDTO(savedReminder);
    }

    public ReminderDTO updateReminder(Long id, ReminderDTO reminderDTO) {
        Reminder reminder = reminderMapper.toEntity(reminderDTO);
        Reminder existingReminder = reminderRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Reminder not found with id " + id));
        reminder.setId(existingReminder.getId());
        Reminder updatedReminder = reminderRepository.save(reminder);
        return reminderMapper.toDTO(updatedReminder);
    }

    public ReminderDTO updateReminderPartially(Long id, ReminderDTO partialReminderDTO) {
        Reminder existingReminder = reminderRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Reminder not found with id " + id));

        if (partialReminderDTO.getTitle() != null) {
            existingReminder.setTitle(partialReminderDTO.getTitle());
        }
        if (partialReminderDTO.getNotes() != null) {
            existingReminder.setNotes(partialReminderDTO.getNotes());
        }
        if (partialReminderDTO.getCategory() != null) {
            existingReminder.setCategory(partialReminderDTO.getCategory());
        }
        if (partialReminderDTO.getLocation() != null) {
            existingReminder.setLocation(partialReminderDTO.getLocation());
        }
        if (partialReminderDTO.getPriority() != null) {
            existingReminder.setPriority(partialReminderDTO.getPriority());
        }

        Reminder updatedReminder = reminderRepository.save(existingReminder);
        return reminderMapper.toDTO(updatedReminder);
    }


    public void deleteReminder(Long id) {
        Reminder existingReminder = reminderRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Reminder not found with id " + id));
        reminderRepository.delete(existingReminder);
    }
}
