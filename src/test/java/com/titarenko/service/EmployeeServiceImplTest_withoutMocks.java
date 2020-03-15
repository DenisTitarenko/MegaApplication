package com.titarenko.service;

import com.titarenko.UnitTestParent;
import com.titarenko.model.Employee;
import com.titarenko.model.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceImplTest_withoutMocks extends UnitTestParent {

    private EmployeeServiceImpl employeeService;

    @BeforeEach
    public void setup() {
        Collections.addAll(database, vasil, petr, stepa);
        employeeService = new EmployeeServiceImpl(employeeDao);
    }

    @Test
    public void testCreate() {
        Employee newEmpl = new Employee.Builder()
                .withId(4)
                .withName("new")
                .withSex(Gender.F)
                .withPosition("new")
                .withSalary(2000)
                .withDateOfHire(LocalDate.parse("2010-10-10"))
                .build();
        assertEquals(newEmpl.getId(), employeeService.create(newEmpl));
    }

    @Test
    public void testGet() {
        Employee employee = employeeService.get("Vasil");
        assertEquals(employee, vasil);
    }

    @Test
    public void testUpdate() {
        Employee newEmpl = new Employee.Builder()
                .withName("new")
                .withSex(Gender.M)
                .withPosition("new")
                .withSalary(2000)
                .withDateOfHire(LocalDate.parse("2010-10-10"))
                .build();
        assertEquals(newEmpl, employeeService.update(1, newEmpl));
    }

    @Test
    public void testDelete() {
        assertTrue(employeeService.delete("Vasil"));
    }

    @Test
    public void testGetAll() {
        List<Employee> all = employeeService.getAll();
        assertEquals(database.size(), all.size());
        assertEquals(vasil, all.get(0));
        assertEquals(petr, all.get(1));
        assertEquals(stepa, all.get(2));
    }

    @Test
    public void testGetEmployeesWithSameSalary() {
        List<Employee> withSameSalary = Arrays.asList(vasil, petr);
        assertNotEquals(Collections.emptyList(), employeeService.getEmployeesWithSameSalary());
        assertEquals(withSameSalary, employeeService.getEmployeesWithSameSalary());
    }

    @Test
    public void testGetAllGroupByPositionAndDate() {
        assertEquals(Arrays.asList(stepa, vasil, petr), employeeService.getAllGroupByPositionAndDate());
    }

    @Test
    public void testIncreaseSalary() {
        int initSalary = database.get(0).getSalary();
        assertTrue(employeeService.increaseSalary(1, 849));
        assertEquals(initSalary + 849, database.get(0).getSalary());
    }
}
