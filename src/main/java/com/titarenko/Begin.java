package com.titarenko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.titarenko.dao")
@SpringBootApplication
public class Begin extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(Begin.class, args);
    }
}
