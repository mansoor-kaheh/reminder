package com.devmantech.reminders.controller;

import com.devmantech.reminders.dto.ReminderDTO;
import com.devmantech.reminders.service.ReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reminders")
@RequiredArgsConstructor
public class ReminderController {

    private final ReminderService reminderService;

    @GetMapping
    public List<ReminderDTO> getAllReminders() {
        return reminderService.getAllReminders();
    }

    @GetMapping("/{id}")
    public ReminderDTO getReminderById(@PathVariable Long id) {
        return reminderService.getReminderById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReminderDTO createReminder(@RequestBody ReminderDTO reminderDTO) {
        return reminderService.addReminder(reminderDTO);
    }

    @PutMapping("/{id}")
    public ReminderDTO updateReminder(@PathVariable Long id, @RequestBody ReminderDTO reminderDTO) {
        return reminderService.updateReminder(id, reminderDTO);
    }

    @PatchMapping("/{id}")
    public ReminderDTO updateReminderById(@PathVariable Long id, @RequestBody ReminderDTO reminderDTO) {
        return reminderService.updateReminderPartially(id, reminderDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReminder(@PathVariable Long id) {
        reminderService.deleteReminder(id);
    }

}
