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
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        ReminderResponse reminderResponse = new ReminderResponse();
        reminderResponse.setId(1L);
        reminderResponse.setTitle("Test Reminder");
        reminderResponse.setNotes("Test notes");
        reminderResponse.setCategory("Test category");
        reminderResponse.setLocation("Test location");
        reminderResponse.setPriority(Priority.LOW);
        reminderResponse.setCompletionStatus(CompletionStatus.COMPLETED);

        // Call the method under test
        EntityModel<ReminderResponse> model = assembler.toModel(reminderResponse);

        // Assert that the model contains the correct data
        ReminderResponse content = model.getContent();
        assertNotNull(content);
        assertEquals(1L, content.getId());
        assertEquals("Test Reminder", content.getTitle());
        assertEquals("Test notes", content.getNotes());
        assertEquals("Test category", content.getCategory());
        assertEquals("Test location", content.getLocation());
        assertEquals(Priority.LOW, content.getPriority());
        assertEquals(CompletionStatus.COMPLETED, content.getCompletionStatus());

        // Assert that the model contains the correct self link
        Link selfLink = model.getRequiredLink(IanaLinkRelations.SELF);
        assertEquals("/api/v1/reminders/1", selfLink.getHref());

        // Assert that the model contains the correct reminders link
        Link remindersLink = model.getRequiredLink("reminders");
        assertEquals("/api/v1/reminders", remindersLink.getHref());
    }
}
