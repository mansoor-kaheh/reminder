package com.devmantech.reminders.controller;

import com.devmantech.reminders.dto.ReminderModelAssembler;
import com.devmantech.reminders.dto.ReminderRequest;
import com.devmantech.reminders.dto.ReminderResponse;
import com.devmantech.reminders.exception.ErrorMessage;
import com.devmantech.reminders.service.ReminderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/reminders")
@RequiredArgsConstructor
@Tag(name = "Reminder APIs", description = "API services for CRUD operations on Reminders")
public class ReminderController {

    private final ReminderService reminderService;
    private final ReminderModelAssembler reminderModelAssembler;

    @Operation(summary = "Get all Reminders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reminders are returned",
                    content = {@Content(mediaType = "application/hal+json")})
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ReminderResponse>>> getAllReminders() {
        List<EntityModel<ReminderResponse>> reminders = reminderService.getAllReminders().stream()
                .map(reminderModelAssembler::toModel).toList();
        return ResponseEntity.ok(CollectionModel.of(reminders,
                linkTo(methodOn(ReminderController.class).getAllReminders()).withSelfRel()));
    }

    @Operation(summary = "Get a Reminder by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reminder is returned",
                    content = {@Content(mediaType = "application/hal+json")}),
            @ApiResponse(responseCode = "404", description = "Reminder not found",
                    content = {@Content(mediaType = "application/hal+json", schema = @Schema(implementation = ErrorMessage.class))})
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ReminderResponse>> getReminderById(
            @Parameter(description = "id of Reminder to be searched") @PathVariable Long id) {
        return ResponseEntity.ok(reminderModelAssembler.toModel(reminderService.getReminderById(id)));
    }

    @Operation(summary = "Create a new Reminder")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reminder is created",
                    content = {@Content(mediaType = "application/hal+json", schema = @Schema(implementation = ReminderResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/hal+json", schema = @Schema(implementation = ErrorMessage.class))})
    })
    @PostMapping
    public ResponseEntity<EntityModel<ReminderResponse>> createReminder(@RequestBody @Valid ReminderRequest reminderRequest) {
        EntityModel<ReminderResponse> model = reminderModelAssembler.toModel(reminderService.addReminder(reminderRequest));
        return ResponseEntity.created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(model);
    }

    @Operation(summary = "Update a Reminder by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reminder is fully updated and returned",
                    content = {@Content(mediaType = "application/hal+json", schema = @Schema(implementation = ReminderResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/hal+json", schema = @Schema(implementation = ErrorMessage.class))}),
            @ApiResponse(responseCode = "404", description = "Reminder not found",
                    content = {@Content(mediaType = "application/hal+json", schema = @Schema(implementation = ErrorMessage.class))})
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ReminderResponse>> updateReminder(
            @Parameter(description = "id of Reminder to be updated") @PathVariable Long id,
            @Parameter @RequestBody @Valid ReminderRequest reminderRequest) {
        EntityModel<ReminderResponse> model = reminderModelAssembler.toModel(reminderService.updateReminder(id, reminderRequest));
        return ResponseEntity.ok(model);
    }

    @Operation(summary = "Update a Reminder partially by id. Only not-null fields are updated.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reminder is partially updated and returned",
                    content = {@Content(mediaType = "application/hal+json", schema = @Schema(implementation = ReminderResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/hal+json", schema = @Schema(implementation = ErrorMessage.class))}),
            @ApiResponse(responseCode = "404", description = "Reminder not found",
                    content = {@Content(mediaType = "application/hal+json", schema = @Schema(implementation = ErrorMessage.class))})
    })
    @PatchMapping("/{id}")
    public ResponseEntity<EntityModel<ReminderResponse>> updateReminderPartially(
            @Parameter(description = "id of Reminder to be updated") @PathVariable Long id,
            @Parameter @RequestBody @Valid ReminderRequest reminderRequest) {
        return ResponseEntity.ok(reminderModelAssembler.toModel(reminderService.updateReminderPartially(id, reminderRequest)));
    }

    @Operation(summary = "Delete a Reminder by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reminder is deleted"),
            @ApiResponse(responseCode = "404", description = "Reminder not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))})
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReminder(
            @Parameter(description = "id of Reminder to be deleted") @PathVariable Long id) {
        reminderService.deleteReminder(id);
        return ResponseEntity.noContent().build();
    }

}
