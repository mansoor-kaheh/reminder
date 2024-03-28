package com.devmantech.reminders.controller;

import com.devmantech.reminders.dto.ReminderDTO;
import com.devmantech.reminders.dto.ReminderModelAssembler;
import com.devmantech.reminders.service.ReminderService;
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
public class ReminderController {

    private final ReminderService reminderService;
    private final ReminderModelAssembler reminderModelAssembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ReminderDTO>>> getAllReminders() {
        List<EntityModel<ReminderDTO>> reminders = reminderService.getAllReminders().stream()
                .map(reminderModelAssembler::toModel).toList();
        return ResponseEntity.ok(CollectionModel.of(reminders,
                linkTo(methodOn(ReminderController.class).getAllReminders()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ReminderDTO>> getReminderById(@PathVariable Long id) {
        return ResponseEntity.ok(reminderModelAssembler.toModel(reminderService.getReminderById(id)));
    }

    @PostMapping
    public ResponseEntity<EntityModel<ReminderDTO>> createReminder(@RequestBody ReminderDTO reminderDTO) {
        EntityModel<ReminderDTO> model = reminderModelAssembler.toModel(reminderService.addReminder(reminderDTO));
        return ResponseEntity.created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ReminderDTO>> updateReminder(@PathVariable Long id, @RequestBody ReminderDTO reminderDTO) {
        EntityModel<ReminderDTO> model = reminderModelAssembler.toModel(reminderService.updateReminder(id, reminderDTO));
        return ResponseEntity.created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(model);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EntityModel<ReminderDTO>> updateReminderById(@PathVariable Long id, @RequestBody ReminderDTO reminderDTO) {
        return ResponseEntity.ok(reminderModelAssembler.toModel(reminderService.updateReminderPartially(id, reminderDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReminder(@PathVariable Long id) {
        reminderService.deleteReminder(id);
        return ResponseEntity.noContent().build();
    }

}
