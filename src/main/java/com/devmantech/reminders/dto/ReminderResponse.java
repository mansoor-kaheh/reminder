package com.devmantech.reminders.dto;

import com.devmantech.reminders.model.CompletionStatus;
import com.devmantech.reminders.model.Priority;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(name = "ReminderResponse", description = "Reminder object in response")
public class ReminderResponse {

    @Schema(name = "id", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long id;
    @NotBlank
    @Schema(name = "title", description = "Title of reminder", minLength = 1, maxLength = 255, example = "Buy Coffee", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;
    @Schema(name = "notes", description = "notes about reminder", minLength = 1, maxLength = 255, example = "Buy coffee from Starbucks", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String notes;
    @Schema(name = "category", description = "category of reminder", minLength = 1, maxLength = 255, example = "entertainment", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String category;
    @Schema(description = "location of reminder", minLength = 1, maxLength = 255, example = "Starbucks", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String location;
    @Schema(name = "priority", description = "priority of reminder", defaultValue = "NONE", example = "LOW", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Priority priority;
    @Schema(name = "completionStatus", description = "completion status of reminder", defaultValue = "NOT_COMPLETE", example = "NOT_COMPLETE", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private CompletionStatus completionStatus;
    @Future
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Schema(name = "dueDateTime", description = "due date and time of reminder", defaultValue = "null", example = "2024-02-14 03:30", pattern = "yyyy-MM-dd HH:ss", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private LocalDateTime dueDateTime;

}
