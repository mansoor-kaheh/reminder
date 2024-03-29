package com.devmantech.reminders.mapper;

import com.devmantech.reminders.dto.ReminderDTO;
import com.devmantech.reminders.model.CompletionStatus;
import com.devmantech.reminders.model.Priority;
import com.devmantech.reminders.model.Reminder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ReminderMapper {
    private final ModelMapper modelMapper;

    @Value("${reminder.default-category-name}")
    private String defaultCategory;

    @Autowired
    public ReminderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ReminderDTO toDTO(Reminder reminder) {
        return modelMapper.map(reminder, ReminderDTO.class);
    }

    public Reminder toEntity(ReminderDTO reminderDTO) {
        Reminder reminder = modelMapper.map(reminderDTO, Reminder.class);
        if (reminder.getCategory() == null || reminder.getCategory().trim().isEmpty())
            reminder.setCategory(defaultCategory);
        if (reminder.getPriority() == null)
            reminder.setPriority(Priority.NONE);
        if (reminder.getCompletionStatus() == null)
            reminder.setCompletionStatus(CompletionStatus.NOT_COMPLETE);
        return reminder;
    }

}
