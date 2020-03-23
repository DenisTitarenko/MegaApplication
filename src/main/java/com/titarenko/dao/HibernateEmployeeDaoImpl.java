package com.titarenko.dao;

import com.titarenko.model.Employee;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;


public class HibernateEmployeeDaoImpl implements EmployeeDao {

    private Session session;

    public HibernateEmployeeDaoImpl() {
        session = HibernateSession.getSessionFactory().openSession();
    }

    @Override
    public Integer create(Employee employee) {
        Transaction transaction = session.beginTransaction();
        session.save(employee);
        transaction.commit();
        session.close();
        return employee.getId();
    }

    @Override
    public Employee get(String name) {
        Criteria criteria = session.createCriteria(Employee.class);
        criteria.add(Restrictions.eq("name", name));
        return (Employee) criteria.uniqueResult();
    }

    @Override
    public Employee get(int id) {
        return session.get(Employee.class, id);
    }

    @Override
    public Employee update(int id, Employee employee) {
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
        Transaction tx1 = session.beginTransaction();
        session.delete(get(name));
        tx1.commit();
        session.close();
        return true;
    }

    @Override
    public List<Employee> getAll() {
        return session.createQuery("from Employee", Employee.class).list();
    }

    @Override
    public List<Employee> getEmployeesWithSameSalary() {
        String query = "FROM Employee " +
                "WHERE salary IN " +
                "(SELECT salary FROM Employee " +
                "GROUP by salary " +
                "HAVING count(*) > 1) " +
                "ORDER BY salary DESC";
        return session.createQuery(query, Employee.class).list();
    }

    private void updateEmployeeInfo(Employee old, Employee updated) {
        old.setName(updated.getName());
        old.setDateOfHire(updated.getDateOfHire());
        old.setPosition(updated.getPosition());
        old.setSalary(updated.getSalary());
        old.setSex(updated.getSex());
    }
}
