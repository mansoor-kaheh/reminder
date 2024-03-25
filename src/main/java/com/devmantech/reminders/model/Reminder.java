package com.devmantech.reminders.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Entity
public class Reminder extends BaseEntity {

    private String title;
    private String notes;
    private String category;
    //    private LocalDateTime due;
    private String location;
    @Enumerated(value = EnumType.STRING)
    private Priority priority;

}
