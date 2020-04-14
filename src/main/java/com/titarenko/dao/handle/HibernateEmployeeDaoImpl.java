package com.titarenko.dao.handle;

import com.titarenko.model.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Deprecated
//@NoArgsConstructor
//@Repository
public class HibernateEmployeeDaoImpl implements EmployeeDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Integer create(Employee employee) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Integer save = (Integer) session.save(employee);
            session.getTransaction().commit();
            return save;
        }
    }

    @Override
    public Employee get(String name) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaQuery<Employee> criteria = session.getCriteriaBuilder().createQuery(Employee.class);
            Root<Employee> root = criteria.from(Employee.class);
            criteria.select(root).where(session.getCriteriaBuilder().equal(root.get("name"), name));
            return session.createQuery(criteria).uniqueResult();
        }
    }

    @Override
    public Employee get(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Employee.class, id);
        }
    }

    @Override
    public Employee update(int id, Employee employee) {
        try (Session session = sessionFactory.openSession()) {
            employee.setId(id);
            session.beginTransaction();
            Object updated = session.merge(employee);
            session.getTransaction().commit();
            return (Employee) updated;
        }
    }

    @Override
    public boolean delete(String name) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(get(name));
            session.getTransaction().commit();
            return true;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(get(id));
            session.getTransaction().commit();
            return true;
        }
    }

    @Override
    public List<Employee> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Employee", Employee.class).list();
        }
    }

    @Override
    public List<Employee> getEmployeesWithSameSalary() {
        try (Session session = sessionFactory.openSession()) {
            return session.createNamedQuery("Employee_getEmployeesWithSameSalary", Employee.class).list();
        }
    }
}