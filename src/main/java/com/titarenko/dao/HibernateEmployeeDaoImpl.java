package com.titarenko.dao;

import com.titarenko.di.annotation.Brick;
import com.titarenko.model.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static com.titarenko.dao.HibernateSession.createSession;

@Brick
public class HibernateEmployeeDaoImpl implements EmployeeDao {

    public HibernateEmployeeDaoImpl() {
    }

    @Override
    public Integer create(Employee employee) {
        try (Session session = createSession()) {
            return (Integer) session.save(employee);
        }
    }

    @Override
    public Employee get(String name) {
        try (Session session = createSession()) {
            CriteriaQuery<Employee> criteria = session.getCriteriaBuilder().createQuery(Employee.class);
            Root<Employee> root = criteria.from(Employee.class);
            criteria.select(root).where(session.getCriteriaBuilder().equal(root.get("name"), name));
            return session.createQuery(criteria).uniqueResult();
        }
    }

    @Override
    public Employee get(int id) {
        try (Session session = createSession()) {
            return session.get(Employee.class, id);
        }
    }

    @Override
    public Employee update(int id, Employee employee) {
        try (Session session = HibernateSession.createSession()) {
            employee.setId(id);
            Transaction tx1 = session.beginTransaction();
            Object updated = session.merge(employee);
            tx1.commit();
            return (Employee) updated;
        }
    }

    @Override
    public boolean delete(String name) {
        try (Session session = createSession()) {
            Transaction tx1 = session.beginTransaction();
            session.delete(get(name));
            tx1.commit();
            return true;
        }
    }

    @Override
    public List<Employee> getAll() {
        try (Session session = createSession()) {
            return session.createQuery("from Employee", Employee.class).list();
        }
    }

    @Override
    public List<Employee> getEmployeesWithSameSalary() {
        try (Session session = createSession()) {
            return session.createNamedQuery("Employee_getEmployeesWithSameSalary", Employee.class).list();
        }
    }
}
