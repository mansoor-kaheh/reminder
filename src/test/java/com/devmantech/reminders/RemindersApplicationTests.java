package com.devmantech.reminders;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Tag("integration")
@DisplayName("ReminderApplication Test Context Loading")
class RemindersApplicationTests {

    @Test
    @DisplayName("Should load context")
    void contextLoads() {
    }

}
