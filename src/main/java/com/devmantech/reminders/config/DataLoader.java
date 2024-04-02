package com.devmantech.reminders.config;

import com.devmantech.reminders.dto.ReminderRequest;
import com.devmantech.reminders.mapper.ReminderRequestMapper;
import com.devmantech.reminders.model.CompletionStatus;
import com.devmantech.reminders.model.Priority;
import com.devmantech.reminders.repository.ReminderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(ReminderRepository repository, ReminderRequestMapper mapper) {

        return args -> {
            ReminderRequest reminderRequest1 = ReminderRequest.builder().title("Pay phone bill")
                    .notes("Pay phone bill for March")
                    .priority(Priority.MEDIUM)
                    .dueDateTime(LocalDateTime.of(2024, 5, 6, 10, 20, 30))
                    .location("Ottawa")
                    .category("bills")
                    .completionStatus(CompletionStatus.NOT_COMPLETE)
                    .build();


            ReminderRequest reminderRequest2 = ReminderRequest.builder().title("Pay rent")
                    .notes("Pay rent for April")
                    .dueDateTime(LocalDateTime.of(2024, 5, 15, 20, 0, 0))
                    .location("Ottawa")
                    .build();

            repository.save(mapper.toEntity(reminderRequest1));
            repository.save(mapper.toEntity(reminderRequest2));
        };
    }

}
