package com.titarenko.config;

import com.titarenko.io.*;
import com.titarenko.model.Department;
import com.titarenko.model.Employee;
import com.titarenko.model.Project;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//@Configuration
//@ComponentScan("com.titarenko")
@Deprecated
public class SpringConfiguration  {

    private static final Logger LOGGER = Logger.getLogger(SpringConfiguration.class);

    @Bean
    public SessionFactory sessionFactory() {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration().configure();
        configuration.addAnnotatedClass(Employee.class);
        configuration.addAnnotatedClass(Department.class);
        configuration.addAnnotatedClass(Project.class);
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
//                fileReader.createTable();
//                fileReader.tableInitializer();
                return connection;
            }
        } catch (SQLException ignored) {
        }
        LOGGER.error("Failed to connect to DB");
        throw new RuntimeException("Failed to connect to DB");
    }

    @Bean
    public Reader reader() {
        return ConsoleReaderImpl.getInstance();         // for work with console
//        return FileReaderImpl.getInstance();          // for work with files
    }

    @Bean
    public Writer writer() {
        return ConsoleWriterImpl.getInstance();         // for work with console
//        return FileWriterImpl.getInstance();          // for work with files
    }
}
