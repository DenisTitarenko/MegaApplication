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

    private Session session;

    public HibernateEmployeeDaoImpl() {
        session = HibernateSession.getSessionFactory().openSession();
    }

    @Override
    public Integer create(Employee employee) {
        return (Integer) session.save(employee);
    }

    @Override
    public Employee get(String name) {
        CriteriaQuery<Employee> criteria = session.getCriteriaBuilder().createQuery(Employee.class);
        Root<Employee> root = criteria.from(Employee.class);
        criteria.select(root).where(session.getCriteriaBuilder().equal(root.get("name"), name));
        return session.createQuery(criteria).uniqueResult();
    }

    @Override
    public Employee get(int id) {
        return session.get(Employee.class, id);
    }

    @Override
    public Employee update(int id, Employee employee) {
        employee.setId(id);
        return (Employee) session.merge(employee);
    }

    @Override
    public boolean delete(String name) {
        Transaction tx1 = session.beginTransaction();
        session.delete(get(name));
        tx1.commit();
        return true;
    }

    @Override
    public List<Employee> getAll() {
        return session.createQuery("from Employee", Employee.class).list();
    }

    @Override
    public List<Employee> getEmployeesWithSameSalary() {
        return session.createNamedQuery("Employee_getEmployeesWithSameSalary", Employee.class).list();
    }
}
