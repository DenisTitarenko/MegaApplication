package com.titarenko.dao;

import com.titarenko.config.SpringConfiguration;
import com.titarenko.dao.handle.EmployeeDao;
import com.titarenko.model.Employee;
import com.titarenko.model.enumeration.Gender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class HibernateVsJdbcTest {
    private static final String VAN_DAMME = "Jean Claude Van Damme";
    @Autowired
    @Qualifier(value = "hibernateEmployeeDaoImpl")
    private EmployeeDao hibernateDao;
    @Autowired
    @Qualifier(value = "jdbcEmployeeDaoImpl")
    private EmployeeDao jdbcDao;
    private Employee vanDamme = Employee.builder()
            .name(VAN_DAMME)
            .sex(Gender.M)
            .position("karate")
            .salary(2100)
            .dateOfHire(LocalDate.parse("1960-10-18"))
            .build();

    @Test
    public void testCreate() {
        int sizeBefore = jdbcDao.getAll().size();
        Integer id = hibernateDao.create(vanDamme);
        assertEquals(sizeBefore + 1, jdbcDao.getAll().size());
        assertEquals(vanDamme, jdbcDao.get(id));
        assertEquals(vanDamme, jdbcDao.get(VAN_DAMME));
        assertTrue(jdbcDao.delete(VAN_DAMME));

        sizeBefore = hibernateDao.getAll().size();
        id = jdbcDao.create(vanDamme);
        assertEquals(sizeBefore + 1, hibernateDao.getAll().size());
        assertEquals(vanDamme, hibernateDao.get(id));
        assertEquals(vanDamme, hibernateDao.get(VAN_DAMME));
        assertTrue(hibernateDao.delete(VAN_DAMME));
        assertEquals(sizeBefore, hibernateDao.getAll().size());
    }

    @Test
    public void testUpdate() {
        final String JIM_CARREY = "Jim Carrey";
        final Employee JIM = Employee.builder()
                .name(JIM_CARREY)
                .sex(Gender.M)
                .position("comedian")
                .salary(600)
                .dateOfHire(LocalDate.parse("1962-01-17"))
                .build();
        Integer id = hibernateDao.create(vanDamme);
        jdbcDao.update(id, JIM);
        assertEquals(JIM, hibernateDao.get(id));
        assertEquals(JIM, hibernateDao.get(JIM_CARREY));
        assertNull(hibernateDao.get(VAN_DAMME));
        assertTrue(hibernateDao.delete(JIM_CARREY));

        id = jdbcDao.create(vanDamme);
        hibernateDao.update(id, JIM);
        assertEquals(JIM, jdbcDao.get(id));
        assertEquals(JIM, jdbcDao.get(JIM_CARREY));
        assertNull(jdbcDao.get(VAN_DAMME));
        assertTrue(jdbcDao.delete(JIM_CARREY));
    }

    @Test
    public void testDelete() {
        int sizeBefore = hibernateDao.getAll().size();
        hibernateDao.create(vanDamme);
        assertTrue(jdbcDao.delete(VAN_DAMME));
        assertNull(hibernateDao.get(VAN_DAMME));
        assertEquals(sizeBefore, hibernateDao.getAll().size());

        jdbcDao.create(vanDamme);
        assertTrue(hibernateDao.delete(VAN_DAMME));
        assertNull(jdbcDao.get(VAN_DAMME));
        assertEquals(sizeBefore, jdbcDao.getAll().size());
    }

    @Test
    public void testGetAll() {
        assertEquals(hibernateDao.getAll(), jdbcDao.getAll());
    }

    @Test
    public void testGetEmployeesWithSameSalary() {
        assertEquals(hibernateDao.getEmployeesWithSameSalary(), jdbcDao.getEmployeesWithSameSalary());
    }
}