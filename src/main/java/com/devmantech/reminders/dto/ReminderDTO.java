package com.devmantech.reminders.dto;

import com.devmantech.reminders.model.Priority;
import lombok.Data;

@Data
public class ReminderDTO {

    private Long id;
    private String title;
    private String notes;
    private String category;
    //    private LocalDateTime due;
    private String location;
    private Priority priority;

}
