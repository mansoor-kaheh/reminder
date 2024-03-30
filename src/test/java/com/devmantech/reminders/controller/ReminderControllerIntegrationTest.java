package com.devmantech.reminders.controller;

import com.devmantech.reminders.dto.ReminderDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.modelmapper.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Tag("integration")
@DisplayName("ReminderController Integration Tests")
class ReminderControllerIntegrationTest {

    public static final String API_PATH = "/api/v1/reminders";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should create a Reminder via POST API")
    void shouldCreateReminder() throws Exception {
        ReminderDTO reminderDTO = new ReminderDTO();
        reminderDTO.setTitle("Test Reminder");
        reminderDTO.setDueDateTime(LocalDateTime.now().plusHours(1));
        String json = objectMapper.writeValueAsString(reminderDTO);

        mockMvc.perform(MockMvcRequestBuilders.post(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DisplayName("Should receive BAD_REQUEST and not create a Reminder because of past due date")
    void shouldNotCreateReminderWithPastDueDateTime() throws Exception {
        ReminderDTO reminderDTO = new ReminderDTO();
        reminderDTO.setTitle("Test Reminder");
        reminderDTO.setDueDateTime(LocalDateTime.now().minusMinutes(1));
        String json = objectMapper.writeValueAsString(reminderDTO);

        mockMvc.perform(MockMvcRequestBuilders.post(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    @DisplayName("Should receive BAD_REQUEST and not create a Reminder because of empty title")
    void shouldNotCreateReminderWithEmptyTitle() throws Exception {
        ReminderDTO reminderDTO = new ReminderDTO();
        reminderDTO.setNotes("Test note");
        String json = objectMapper.writeValueAsString(reminderDTO);

        mockMvc.perform(MockMvcRequestBuilders.post(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Should receive NOT_FOUND when GET a Reminder that not exist")
    void shouldReceiveNotFoundWhenGetReminderThatNotExist() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(API_PATH + "/1234567")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }



}
