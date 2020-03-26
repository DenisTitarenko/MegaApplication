package com.titarenko.dao;

import com.titarenko.di.annotation.Brick;
import com.titarenko.model.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Brick
public class HibernateEmployeeDaoImpl implements EmployeeDao {

    public HibernateEmployeeDaoImpl() {
    }

    @Override
    public Integer create(Employee employee) {
        Session session = HibernateSession.getSessionFactory().openSession();
        session.save(employee);
        session.close();
        return employee.getId();
    }

    @Override
    public Employee get(String name) {
        Session session = HibernateSession.getSessionFactory().openSession();
        CriteriaQuery<Employee> criteria = session.getCriteriaBuilder().createQuery(Employee.class);
        Root<Employee> root = criteria.from(Employee.class);
        criteria.select(root).where(session.getCriteriaBuilder().equal(root.get("name"), name));
        Employee returned = session.createQuery(criteria).uniqueResult();
        session.close();
        return returned;
    }

    @Override
    public Employee get(int id) {
        Session session = HibernateSession.getSessionFactory().openSession();
        Employee returned = session.get(Employee.class, id);
        session.close();
        return returned;
    }

    @Override
    public Employee update(int id, Employee employee) {
        Session session = HibernateSession.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        employee.setId(id);
        session.merge(employee);
        tx1.commit();
        session.close();
        return get(id);
    }

    @Override
    public boolean delete(String name) {
        Session session = HibernateSession.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(get(name));
        tx1.commit();
        session.close();
        return true;
    }

    @Override
    public List<Employee> getAll() {
        Session session = HibernateSession.getSessionFactory().openSession();
        List<Employee> returned = session.createQuery("from Employee", Employee.class).list();
        session.close();
        return returned;
    }

    @Override
    public List<Employee> getEmployeesWithSameSalary() {
        Session session = HibernateSession.getSessionFactory().openSession();
        List<Employee> returned = session.createNamedQuery("Employee_getEmployeesWithSameSalary", Employee.class).list();
        session.close();
        return returned;
    }
}
