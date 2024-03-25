package com.devmantech.reminders.mapper;

import com.devmantech.reminders.dto.ReminderDTO;
import com.devmantech.reminders.model.Reminder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ReminderMapper {
    private final ModelMapper modelMapper;

    public ReminderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ReminderDTO toDTO(Reminder reminder) {
        return modelMapper.map(reminder, ReminderDTO.class);
    }

    public Reminder toEntity(ReminderDTO reminderDTO) {
        return modelMapper.map(reminderDTO, Reminder.class);
    }

}
