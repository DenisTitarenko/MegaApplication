package com.titarenko.dao;

import com.titarenko.model.Employee;
import com.titarenko.service.HibernateSession;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;


public class HibernateEmployeeDaoImpl implements EmployeeDao {

    @Override
    public Integer create(Employee employee) {
        Session session = HibernateSession.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(employee);
        transaction.commit();
        session.close();
        return employee.getId();
    }

    @Override
    public Employee get(String name) {
        Criteria criteria = HibernateSession.getSessionFactory().openSession().createCriteria(Employee.class);
        criteria.add(Restrictions.eq("name", name));
        return (Employee) criteria.uniqueResult();
    }

    @Override
    public Employee get(int id) {
        return HibernateSession.getSessionFactory().openSession().get(Employee.class, id);
    }

    @Override
    public Employee update(int id, Employee employee) {
        Session session = HibernateSession.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Employee old = session.load(Employee.class, id);
        updateEmployeeInfo(old, employee);
        session.update(old);
        tx1.commit();
        session.close();
        return get(1);
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
        return HibernateSession.getSessionFactory().openSession().createQuery("from Employee", Employee.class).list();
    }

    @Override
    public List<Employee> getEmployeesWithSameSalary() {
        String query = "FROM Employee " +
                "WHERE salary IN " +
                "(SELECT salary FROM Employee " +
                "GROUP by salary " +
                "HAVING count(*) > 1) " +
                "ORDER BY salary DESC";
        return HibernateSession.getSessionFactory().openSession().createQuery(query, Employee.class).list();
    }

    private void updateEmployeeInfo(Employee old, Employee updated) {
        old.setName(updated.getName());
        old.setDateOfHire(updated.getDateOfHire());
        old.setPosition(updated.getPosition());
        old.setSalary(updated.getSalary());
        old.setSex(updated.getSex());
    }
}
