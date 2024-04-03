package com.devmantech.reminders.service;

import com.devmantech.reminders.dto.ReminderRequest;
import com.devmantech.reminders.dto.ReminderResponse;
import com.devmantech.reminders.exception.ActionNotAllowedException;
import com.devmantech.reminders.exception.ReminderNotFoundException;
import com.devmantech.reminders.mapper.ReminderRequestMapper;
import com.devmantech.reminders.mapper.ReminderResponseMapper;
import com.devmantech.reminders.model.CompletionStatus;
import com.devmantech.reminders.model.Reminder;
import com.devmantech.reminders.repository.ReminderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final ReminderRequestMapper reminderRequestMapper;
    private final ReminderResponseMapper reminderResponseMapper;

    public List<ReminderResponse> getAllReminders() {
        return reminderRepository.findAll().stream().map(reminderResponseMapper::toDTO).collect(Collectors.toList());
    }

    public ReminderResponse getReminderById(Long id) {
        Reminder existingReminder = reminderRepository.findById(id)
                .orElseThrow(() -> new ReminderNotFoundException(id));
        return reminderResponseMapper.toDTO(existingReminder);
    }

    public ReminderResponse addReminder(ReminderRequest reminderRequest) {
        Reminder reminder = reminderRequestMapper.toEntity(reminderRequest);
        Reminder savedReminder = reminderRepository.save(reminder);
        return reminderResponseMapper.toDTO(savedReminder);
    }

    public ReminderResponse updateReminder(Long id, ReminderRequest reminderRequest) {
        Reminder reminder = reminderRequestMapper.toEntity(reminderRequest);
        Reminder existingReminder = reminderRepository.findById(id)
                .orElseThrow(() -> new ReminderNotFoundException(id));
        reminder.setId(existingReminder.getId());
        Reminder updatedReminder = reminderRepository.save(reminder);
        return reminderResponseMapper.toDTO(updatedReminder);
    }

    public ReminderResponse updateReminderPartially(Long id, ReminderRequest partialReminderRequest) {
        Reminder existingReminder = reminderRepository.findById(id)
                .orElseThrow(() -> new ReminderNotFoundException(id));

        if (partialReminderRequest.getTitle() != null) {
            existingReminder.setTitle(partialReminderRequest.getTitle());
        }
        if (partialReminderRequest.getNotes() != null) {
            existingReminder.setNotes(partialReminderRequest.getNotes());
        }
        if (partialReminderRequest.getCategory() != null) {
            existingReminder.setCategory(partialReminderRequest.getCategory());
        }
        if (partialReminderRequest.getLocation() != null) {
            existingReminder.setLocation(partialReminderRequest.getLocation());
        }
        if (partialReminderRequest.getPriority() != null) {
            existingReminder.setPriority(partialReminderRequest.getPriority());
        }

        Reminder updatedReminder = reminderRepository.save(existingReminder);
        return reminderResponseMapper.toDTO(updatedReminder);
    }

    public ReminderResponse completeReminder(Long id) {
        Reminder existingReminder = reminderRepository.findById(id)
                .orElseThrow(() -> new ReminderNotFoundException(id));
        if (existingReminder.getCompletionStatus() == CompletionStatus.COMPLETED) {
            throw new ActionNotAllowedException("Complete action is not allowed. Reminder is already complete");
        }
        existingReminder.setCompletionStatus(CompletionStatus.COMPLETED);
        Reminder completedReminder = reminderRepository.save(existingReminder);
        return reminderResponseMapper.toDTO(completedReminder);
    }

    public void deleteReminder(Long id) {
        Reminder existingReminder = reminderRepository.findById(id)
                .orElseThrow(() -> new ReminderNotFoundException(id));
        reminderRepository.delete(existingReminder);
    }
}
