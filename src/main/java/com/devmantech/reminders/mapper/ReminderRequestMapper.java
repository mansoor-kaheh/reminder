package com.devmantech.reminders.mapper;

import com.devmantech.reminders.dto.ReminderRequest;
import com.devmantech.reminders.model.CompletionStatus;
import com.devmantech.reminders.model.Priority;
import com.devmantech.reminders.model.Reminder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ReminderRequestMapper {
    private final ModelMapper modelMapper;

    @Value("${reminder.default-category-name}")
    private String defaultCategory;

    @Autowired
    public ReminderRequestMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ReminderRequest toDTO(Reminder reminder) {
        return modelMapper.map(reminder, ReminderRequest.class);
    }

    public Reminder toEntity(ReminderRequest reminderRequest) {
        Reminder reminder = modelMapper.map(reminderRequest, Reminder.class);
        if (reminder.getCategory() == null || reminder.getCategory().trim().isEmpty())
            reminder.setCategory(defaultCategory);
        if (reminder.getPriority() == null)
            reminder.setPriority(Priority.NONE);
        if (reminder.getCompletionStatus() == null)
            reminder.setCompletionStatus(CompletionStatus.NOT_COMPLETE);
        return reminder;
    }

}
