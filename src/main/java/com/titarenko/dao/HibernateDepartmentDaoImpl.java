package com.titarenko.dao;

import com.titarenko.model.Department;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@NoArgsConstructor
public class HibernateDepartmentDaoImpl implements DepartmentDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Integer create(Department department) {
        try (Session session = sessionFactory.openSession()) {
            return (Integer) session.save(department);
        }
    }

    @Override
    public Department getByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaQuery<Department> criteria = session.getCriteriaBuilder().createQuery(Department.class);
            Root<Department> root = criteria.from(Department.class);
            criteria.select(root).where(session.getCriteriaBuilder().equal(root.get("name"), name));
            return session.createQuery(criteria).uniqueResult();
        }
    }

    @Override
    public Department update(Integer id, Department department) {
        try (Session session = sessionFactory.openSession()) {
            department.setId(id);
            Transaction tx1 = session.beginTransaction();
            Object updated = session.merge(department);
            tx1.commit();
            return (Department) updated;
        }
    }

    @Override
    public boolean delete(String name) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.delete(getByName(name));
            tx1.commit();
            return true;
        }
    }

    @Override
    public List<Department> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Department", Department.class).list();
        }
    }
}
