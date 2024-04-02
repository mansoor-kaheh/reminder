package com.devmantech.reminders.mapper;

import com.devmantech.reminders.dto.ReminderResponse;
import com.devmantech.reminders.model.Reminder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReminderResponseMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public ReminderResponseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ReminderResponse toDTO(Reminder reminder) {
        return modelMapper.map(reminder, ReminderResponse.class);
    }

    public Reminder toEntity(ReminderResponse reminderResponse) {
        return modelMapper.map(reminderResponse, Reminder.class);
    }

}
