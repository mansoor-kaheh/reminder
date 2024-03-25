package com.devmantech.reminders.config;

import com.devmantech.reminders.mapper.ReminderMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ReminderMapper reminderMapper() {
        return new ReminderMapper(modelMapper());
    }
}
