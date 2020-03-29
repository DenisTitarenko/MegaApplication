package com.titarenko.config;

import com.titarenko.Begin;
import com.titarenko.io.OwnFileReader;
import com.titarenko.model.Employee;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@ComponentScan(basePackages = "com.titarenko")
public class SpringConfiguration {

    private static final Logger LOGGER = Logger.getLogger(SpringConfiguration.class);

    @Bean
    public SessionFactory sessionFactory() {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration().configure();
        configuration.addAnnotatedClass(Employee.class);
        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        return configuration.buildSessionFactory(serviceRegistryBuilder.build());
    }

    @Bean
    public Connection connection() {
        try {
            Connection connection =
                    DriverManager.getConnection("jdbc:postgresql://localhost:5432/megaAppDb", "postgres", "root");
            if (connection != null) {
                LOGGER.info("Connected to DB");
                OwnFileReader fileReader = new OwnFileReader(connection);
                fileReader.createTable();
                fileReader.tableInitializer();
                return connection;
            }
        } catch (SQLException | FileNotFoundException ignored) {}
        LOGGER.error("Failed to connect to DB");
        throw new RuntimeException("Failed to connect to DB");
    }
}

