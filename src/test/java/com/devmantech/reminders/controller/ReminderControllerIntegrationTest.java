package com.devmantech.reminders.controller;

import com.devmantech.reminders.dto.ReminderRequest;
import com.devmantech.reminders.model.Priority;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
@Tag("integration")
@DisplayName("ReminderController Integration Tests")
class ReminderControllerIntegrationTest {

    public static final String API_PATH = "/api/v1/reminders";
    public static final String TEST_REMINDER = "Test Reminder";
    public static final String NEW_TEST_REMINDER = "New Test Reminder";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should create a Reminder via POST API")
    void shouldCreateReminder() throws Exception {
        ReminderRequest reminderRequest = new ReminderRequest();
        reminderRequest.setTitle(TEST_REMINDER);
        reminderRequest.setDueDateTime(LocalDateTime.now().plusHours(1));
        String reminderJson = objectMapper.writeValueAsString(reminderRequest);

        mockMvc.perform(MockMvcRequestBuilders.post(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reminderJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DisplayName("Should receive BAD_REQUEST and not create a Reminder because of past due date")
    void shouldReceiveBadRequestWhenCreatingReminderWithPastDueDateTime() throws Exception {
        ReminderRequest reminderRequest = new ReminderRequest();
        reminderRequest.setTitle(TEST_REMINDER);
        reminderRequest.setDueDateTime(LocalDateTime.now().minusMinutes(1));
        String reminderJson = objectMapper.writeValueAsString(reminderRequest);

        mockMvc.perform(MockMvcRequestBuilders.post(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reminderJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Should receive BAD_REQUEST and not create a Reminder because of empty title")
    void shouldReceiveBadRequestWhenCreatingReminderWithEmptyTitle() throws Exception {
        ReminderRequest reminderRequest = new ReminderRequest();
        reminderRequest.setNotes("Test note");
        String reminderJson = objectMapper.writeValueAsString(reminderRequest);

        mockMvc.perform(MockMvcRequestBuilders.post(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reminderJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Should receive BAD_REQUEST and not create a Reminder because of parsing error")
    void shouldReceiveBadRequestWhenCreatingReminderWithParsingError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Test title\",\"priority\": \"test priority\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders.post(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Test title\",\"dueDateTime\": \"2024-12-01T12:20:33\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Should receive NOT_FOUND when GET a Reminder that not exist")
    void shouldReceiveNotFoundWhenGetNonExistingReminder() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(API_PATH + "/13572468"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Should update existing reminder via PUT API")
    void shouldUpdateExistingReminder() throws Exception {
        ReminderRequest reminderRequest = new ReminderRequest();
        reminderRequest.setTitle(TEST_REMINDER);
        reminderRequest.setDueDateTime(LocalDateTime.now().plusHours(1));
        String reminderJson = objectMapper.writeValueAsString(reminderRequest);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reminderJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String location = mvcResult.getResponse().getHeader("Location");
        assertNotNull(location);

        String savedReminder = mvcResult.getResponse().getContentAsString();
        String newReminderToUpdate = savedReminder.replace(TEST_REMINDER, NEW_TEST_REMINDER);
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(new URI(location))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newReminderToUpdate))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        savedReminder = mvcResult.getResponse().getContentAsString();
        assertTrue(savedReminder.indexOf(NEW_TEST_REMINDER) > 0);
    }

    @Test
    @DisplayName("Should receive NOT_FOUND status when updating non-existing Reminder via PUT API")
    void shouldReceiveNotFoundWhenUpdatingNonExistingReminder() throws Exception {
        ReminderRequest reminderRequest = new ReminderRequest();
        reminderRequest.setTitle(TEST_REMINDER);
        reminderRequest.setDueDateTime(LocalDateTime.now().plusHours(1));
        String reminderJson = objectMapper.writeValueAsString(reminderRequest);

        mockMvc.perform(MockMvcRequestBuilders.put(API_PATH + "/13572468")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reminderJson))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Should partially update  existing reminder via PATCH API")
    void shouldPartiallyUpdateExistingReminder() throws Exception {
        //create a Reminder via POST
        ReminderRequest reminderRequest = new ReminderRequest();
        reminderRequest.setTitle(TEST_REMINDER);
        reminderRequest.setDueDateTime(LocalDateTime.now().plusHours(1));
        String reminderJson = objectMapper.writeValueAsString(reminderRequest);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reminderJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        //get location from response
        String location = mvcResult.getResponse().getHeader("Location");
        assertNotNull(location);

        //get id from response
        String savedReminder = mvcResult.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(savedReminder);
        Long id = jsonNode.get("id").asLong();

        //construct a new reminder partially
        ReminderRequest partialUpdateReminder = new ReminderRequest();
        partialUpdateReminder.setTitle(TEST_REMINDER);
        partialUpdateReminder.setNotes(NEW_TEST_REMINDER);
        partialUpdateReminder.setPriority(Priority.HIGH);
        partialUpdateReminder.setCategory("bills");
        String newReminderToUpdate = objectMapper.writeValueAsString(partialUpdateReminder);
        //call PATCH API
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch(new URI(location))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newReminderToUpdate))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        savedReminder = mvcResult.getResponse().getContentAsString();
        assertTrue(savedReminder.indexOf(NEW_TEST_REMINDER) > 0);
    }

    @Test
    @DisplayName("Should receive NOT_FOUND status when updating non-existing Reminder via PATCH API")
    void shouldReceiveNotFoundWhenPartiallyUpdatingNonExistingReminder() throws Exception {
        ReminderRequest reminderRequest = new ReminderRequest();
        reminderRequest.setTitle(TEST_REMINDER);
        reminderRequest.setDueDateTime(LocalDateTime.now().plusHours(1));
        String reminderJson = objectMapper.writeValueAsString(reminderRequest);

        mockMvc.perform(MockMvcRequestBuilders.patch(API_PATH + "/13572468")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reminderJson))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Should delete existing Reminder via DELETE API")
    void shouldDeleteExistingReminder() throws Exception {
        ReminderRequest reminderRequest = new ReminderRequest();
        reminderRequest.setTitle(TEST_REMINDER);
        reminderRequest.setDueDateTime(LocalDateTime.now().plusHours(1));
        String json = objectMapper.writeValueAsString(reminderRequest);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String location = mvcResult.getResponse().getHeader("Location");
        assertNotNull(location);
        mockMvc.perform(MockMvcRequestBuilders.delete(new URI(location)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("Should receive NOT_FOUND status when deleting non-existing Reminder via DELETE API")
    void shouldReceiveNotFoundWhenDeletingNonExistingReminder() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(API_PATH + "/13572468"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


}
