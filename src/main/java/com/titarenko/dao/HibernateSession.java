package com.titarenko.dao;

import com.titarenko.model.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSession {

    private static SessionFactory sessionFactory;

    static {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Employee.class);
        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(serviceRegistryBuilder.build());
    }

    public static Session createSession() {
        return sessionFactory.openSession();
    }
}
