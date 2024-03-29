package com.devmantech.reminders.config;

import com.devmantech.reminders.dto.ReminderDTO;
import com.devmantech.reminders.mapper.ReminderMapper;
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
    CommandLineRunner initDatabase(ReminderRepository repository, ReminderMapper mapper) {

        return args -> {
            ReminderDTO reminderDTO1 = ReminderDTO.builder().title("Pay phone bill")
                    .notes("Pay phone bill for March")
                    .priority(Priority.MEDIUM)
                    .dueDateTime(LocalDateTime.of(2024, 5, 6, 10, 20, 30))
                    .location("Ottawa")
                    .category("bills")
                    .completionStatus(CompletionStatus.NOT_COMPLETE)
                    .build();


            ReminderDTO reminderDTO2 = ReminderDTO.builder().title("Pay rent")
                    .notes("Pay rent for April")
                    .dueDateTime(LocalDateTime.of(2024, 5, 15, 20, 0, 0))
                    .location("Ottawa")
                    .build();

            repository.save(mapper.toEntity(reminderDTO1));
            repository.save(mapper.toEntity(reminderDTO2));
        };
    }

}
