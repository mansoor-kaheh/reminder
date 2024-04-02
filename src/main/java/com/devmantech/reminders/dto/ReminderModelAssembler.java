package com.devmantech.reminders.dto;

import com.devmantech.reminders.controller.ReminderController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ReminderModelAssembler implements RepresentationModelAssembler<ReminderResponse, EntityModel<ReminderResponse>> {
    @Override
    public EntityModel<ReminderResponse> toModel(ReminderResponse reminderResponse) {
        return EntityModel.of(reminderResponse,
                linkTo(methodOn(ReminderController.class).getReminderById(reminderResponse.getId())).withSelfRel(),
                linkTo(methodOn(ReminderController.class).getAllReminders()).withRel("reminders"));
    }
}
