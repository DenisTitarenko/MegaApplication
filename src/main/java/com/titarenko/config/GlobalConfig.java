package com.titarenko.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class GlobalConfig {
    public static final String DATE_FORMAT_PATTERN = "dd-MM-yyyy";
}
