package com.titarenko.dao.handle;

import com.titarenko.model.Project;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Deprecated
//@Repository
//@NoArgsConstructor
public class HibernateProjectDaoImpl implements ProjectDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Integer create(Project project) {
        try (Session session = sessionFactory.openSession()) {
            return (Integer) session.save(project);
        }
    }

    @Override
    public Project getByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaQuery<Project> criteria = session.getCriteriaBuilder().createQuery(Project.class);
            Root<Project> root = criteria.from(Project.class);
            criteria.select(root).where(session.getCriteriaBuilder().equal(root.get("name"), name));
            return session.createQuery(criteria).uniqueResult();
        }
    }

    @Override
    public Project update(Integer id, Project project) {
        try (Session session = sessionFactory.openSession()) {
            project.setId(id);
            Transaction tx1 = session.beginTransaction();
            Object updated = session.merge(project);
            tx1.commit();
            return (Project) updated;
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
    public List<Project> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Project", Project.class).list();
        }
    }
}
