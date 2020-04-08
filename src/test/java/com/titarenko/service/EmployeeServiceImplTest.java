package com.titarenko.service;

import com.titarenko.UnitTestParent;
import com.titarenko.dao.EmployeeDao;
import com.titarenko.model.Employee;
import com.titarenko.model.enumeration.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest extends UnitTestParent {
    private Employee jerry = Employee.builder()
            .id(15)
            .name("Jerry")
            .sex(Gender.M)
            .position("singer")
            .salary(800)
            .dateOfHire(LocalDate.parse("2011-11-11"))
            .build();

    @Mock
    private EmployeeDao employeeDao;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    public void setup() {
        Collections.addAll(database, vasil, petr, stepa);
    }

    @Test
    public void testCreate() {
        when(employeeDao.create(jerry)).thenReturn(createEmployee(jerry));
        assertEquals(15, employeeService.create(jerry));
    }

    @Test
    public void testGet() {
        String name = petr.getName();
        when(employeeDao.get(name)).thenReturn(petr);
        Employee employee = employeeService.get(name);
        assertEquals(employee, petr);
    }

    @Test
    public void testUpdate() {
        Employee updated = database.get(2);
        when(employeeDao.getAll()).thenReturn(List.of(vasil, petr, stepa));
        when(employeeDao.update(updated.getId(), jerry)).thenReturn(updateEmployee(updated.getId(), jerry));
        employeeService.update(updated.getId(), jerry);
        assertEquals(jerry, database.get(2));
    }

    @Test
    public void testDelete() {
        String name = petr.getName();
        when(employeeDao.delete(name)).thenReturn(database.removeIf(e -> name.equals(e.getName())));
        assertTrue(employeeService.delete(name));
        assertEquals(2, database.size());
    }

    @Test
    public void testGetAll() {
        when(employeeDao.getAll()).thenReturn(database);
        List<Employee> all = employeeService.getAll();
        assertEquals(3, all.size());
        assertEquals(vasil, all.get(0));
    }

    @Test
    public void testGetEmployeeWithSameSalary() {
        when(employeeDao.getEmployeesWithSameSalary()).thenReturn(List.of(vasil, petr));
        List<Employee> list = employeeService.getEmployeesWithSameSalary();
        assertEquals(2, list.size());
        assertEquals(list.get(0).getSalary(), list.get(1).getSalary());
        assertEquals(vasil, list.get(0));
    }

    @Test
    public void testGetAllGroupByPositionAndDate() {
        when(employeeService.getAllGroupByPositionAndDate()).thenReturn(Arrays.asList(stepa, vasil, petr));
        List<Employee> list = employeeService.getAllGroupByPositionAndDate();
        assertEquals(3, list.size());
        assertTrue(list.get(0).getDateOfHire().isAfter(list.get(1).getDateOfHire()));
    }

    @Test
    public void testIncreaseSalary() {
        Employee increasedEmployee = database.get(0);
        when(employeeDao.getAll()).thenReturn(database);
        when(employeeDao.get(increasedEmployee.getId())).thenReturn(increasedEmployee);
        assertTrue(employeeService.increaseSalary(1, 850));
        assertEquals(1000, database.get(0).getSalary());
    }

    private int createEmployee(Employee employee) {
        database.add(employee);
        return employee.getId();
    }

    private Employee updateEmployee(Integer id, Employee e) {
        Employee find = database
                .stream()
                .filter(test -> ((test.getId() == id)))
                .findFirst()
                .orElse(null);
        database.set(database.indexOf(find), e);
        return e;
    }
}
