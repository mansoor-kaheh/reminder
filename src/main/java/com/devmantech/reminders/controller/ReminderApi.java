package com.devmantech.reminders.controller;

import com.devmantech.reminders.dto.ReminderRequest;
import com.devmantech.reminders.dto.ReminderResponse;
import com.devmantech.reminders.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface ReminderApi {
    @Operation(summary = "Get all Reminders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reminders are returned",
                    content = {@Content(mediaType = "application/hal+json")})
    })
    ResponseEntity<CollectionModel<EntityModel<ReminderResponse>>> getAllReminders();

    @Operation(summary = "Get a Reminder by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reminder is returned",
                    content = {@Content(mediaType = "application/hal+json")}),
            @ApiResponse(responseCode = "404", description = "Reminder not found",
                    content = {@Content(mediaType = "application/hal+json", schema = @Schema(implementation = ErrorMessage.class))})
    })
    ResponseEntity<EntityModel<ReminderResponse>> getReminderById(
            @Parameter(description = "id of Reminder to be searched") @PathVariable Long id);

    @Operation(summary = "Create a new Reminder")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reminder is created",
                    content = {@Content(mediaType = "application/hal+json", schema = @Schema(implementation = ReminderResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/hal+json", schema = @Schema(implementation = ErrorMessage.class))})
    })
    ResponseEntity<EntityModel<ReminderResponse>> createReminder(@RequestBody @Valid ReminderRequest reminderRequest);

    @Operation(summary = "Update a Reminder by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reminder is fully updated and returned",
                    content = {@Content(mediaType = "application/hal+json", schema = @Schema(implementation = ReminderResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/hal+json", schema = @Schema(implementation = ErrorMessage.class))}),
            @ApiResponse(responseCode = "404", description = "Reminder not found",
                    content = {@Content(mediaType = "application/hal+json", schema = @Schema(implementation = ErrorMessage.class))})
    })
    ResponseEntity<EntityModel<ReminderResponse>> updateReminder(
            @Parameter(description = "id of Reminder to be updated") @PathVariable Long id,
            @Parameter @RequestBody @Valid ReminderRequest reminderRequest);

    @Operation(summary = "Update a Reminder partially by id. Only not-null fields are updated.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reminder is partially updated and returned",
                    content = {@Content(mediaType = "application/hal+json", schema = @Schema(implementation = ReminderResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/hal+json", schema = @Schema(implementation = ErrorMessage.class))}),
            @ApiResponse(responseCode = "404", description = "Reminder not found",
                    content = {@Content(mediaType = "application/hal+json", schema = @Schema(implementation = ErrorMessage.class))})
    })
    ResponseEntity<EntityModel<ReminderResponse>> updateReminderPartially(
            @Parameter(description = "id of Reminder to be updated") @PathVariable Long id,
            @Parameter @RequestBody @Valid ReminderRequest reminderRequest);

    @Operation(summary = "Complete the state of a Reminder")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reminder is completed",
                    content = {@Content(mediaType = "application/hal+json", schema = @Schema(implementation = ReminderResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Reminder not found",
                    content = {@Content(mediaType = "application/hal+json", schema = @Schema(implementation = ErrorMessage.class))}),
            @ApiResponse(responseCode = "405", description = "Method not allowed. Reminder is already completed",
                    content = {@Content(mediaType = "application/hal+json", schema = @Schema(implementation = ErrorMessage.class))})
    })
    ResponseEntity<EntityModel<ReminderResponse>> completeReminder(@PathVariable Long id);

    @Operation(summary = "Delete a Reminder by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reminder is deleted"),
            @ApiResponse(responseCode = "404", description = "Reminder not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))})
    })
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteReminder(
            @Parameter(description = "id of Reminder to be deleted") @PathVariable Long id);
}
