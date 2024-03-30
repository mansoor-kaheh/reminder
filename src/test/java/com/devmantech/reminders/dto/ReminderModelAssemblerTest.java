package com.devmantech.reminders.dto;

import com.devmantech.reminders.model.CompletionStatus;
import com.devmantech.reminders.model.Priority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("ReminderModelAssembler Tests")
class ReminderModelAssemblerTest {

    private ReminderModelAssembler assembler;

    @BeforeEach
    void setUp() {
        assembler = new ReminderModelAssembler();
    }

    @Test
    @DisplayName("Should return a model that contains a self link")
    void shouldModelContainLinks() {
        // Mock data
        ReminderDTO reminderDTO = new ReminderDTO();
        reminderDTO.setId(1L);
        reminderDTO.setTitle("Test Reminder");
        reminderDTO.setNotes("Test notes");
        reminderDTO.setCategory("Test category");
        reminderDTO.setLocation("Test location");
        reminderDTO.setPriority(Priority.LOW);
        reminderDTO.setCompletionStatus(CompletionStatus.COMPLETED);

        // Call the method under test
        EntityModel<ReminderDTO> model = assembler.toModel(reminderDTO);

        // Assert that the model contains the correct data
        assertEquals(1L, model.getContent().getId());
        assertEquals("Test Reminder", model.getContent().getTitle());
        assertEquals("Test notes", model.getContent().getNotes());
        assertEquals("Test category", model.getContent().getCategory());
        assertEquals("Test location", model.getContent().getLocation());
        assertEquals(Priority.LOW, model.getContent().getPriority());
        assertEquals(CompletionStatus.COMPLETED, model.getContent().getCompletionStatus());

        // Assert that the model contains the correct self link
        Link selfLink = model.getRequiredLink(IanaLinkRelations.SELF);
        assertEquals("/api/v1/reminders/1", selfLink.getHref());

        // Assert that the model contains the correct reminders link
        Link remindersLink = model.getRequiredLink("reminders");
        assertEquals("/api/v1/reminders", remindersLink.getHref());
    }
}
