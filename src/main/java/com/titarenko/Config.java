package com.titarenko;

import com.titarenko.dao.HibernateEmployeeDaoImpl;
import com.titarenko.dao.JdbcConnection;
import com.titarenko.dao.JdbcEmployeeDaoImpl;
import com.titarenko.io.ConsoleReaderImpl;
import com.titarenko.io.ConsoleWriterImpl;
import com.titarenko.io.FileReaderImpl;
import com.titarenko.io.FileWriterImpl;
import com.titarenko.model.Employee;
import com.titarenko.rest.HttpController;
import com.titarenko.service.EmployeeServiceImpl;
import com.titarenko.service.MenuImpl;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.extension.Extensions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Config {

    @Bean
    public SessionFactory sessionFactory() {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration().configure();
        configuration.addAnnotatedClass(Employee.class);
        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        return configuration.buildSessionFactory(serviceRegistryBuilder.build());
    }

    @Bean
    public JdbcConnection jdbcConnection() {
        return new JdbcConnection();
    }

    @Bean
    public JdbcEmployeeDaoImpl jdbcEmployeeDaoImpl() {
        return new JdbcEmployeeDaoImpl(jdbcConnection());
    }

    @Bean
    public HibernateEmployeeDaoImpl hibernateEmployeeDaoImpl() {
        return new HibernateEmployeeDaoImpl();
    }

    @Bean
    public ConsoleReaderImpl consoleReaderImpl() {
        return ConsoleReaderImpl.getInstance();
    }

    @Bean
    public ConsoleWriterImpl consoleWriterImpl() {
        return ConsoleWriterImpl.getInstance();
    }

    @Bean
    public FileReaderImpl fileReaderImpl() {
        return FileReaderImpl.getInstance();
    }

    @Bean
    public FileWriterImpl fileWriterImpl() {
        return FileWriterImpl.getInstance();
    }

    @Bean
    public HttpController httpController() {
        return new HttpController(employeeServiceImpl());
    }

    @Bean
    public EmployeeServiceImpl employeeServiceImpl() {
        return new EmployeeServiceImpl();
    }

    @Bean
    public MenuImpl menuImpl() {
        return new MenuImpl(employeeServiceImpl(), consoleReaderImpl(), consoleWriterImpl());
    }
}
