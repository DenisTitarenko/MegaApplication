package com.titarenko.service;

import com.titarenko.model.Employee;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeValidatorTest {

    private EmployeeValidator validator = new EmployeeValidator();

    @Test
    public void testNull() {
        Employee employee = null;
        assertFalse(validator.isValidEmployee(employee));
    }

    @Test
    public void testEmpty() {
        Employee employee = new Employee();
        assertFalse(validator.isValidEmployee(employee));
    }

    @Test
    public void testName() {
        assertTrue(validator.isValidName("Andrew"));
        assertTrue(validator.isValidName("Tom Hanks"));
        assertFalse(validator.isValidName("r2d2"));
        assertTrue(validator.isValidName("Sir de-Volan"));
        assertFalse(validator.isValidName("Itchy, Scratchy"));
        assertFalse(validator.isValidName("Itchy & Scratchy"));
    }

    @Test
    public void testDate() {
        assertFalse(validator.isValidDate(LocalDate.parse("2025-12-11")));
        assertFalse(validator.isValidDate(LocalDate.parse("1945-05-09")));
        assertTrue(validator.isValidDate(LocalDate.parse("2020-03-16")));
    }

    @Test
    public void testSalary() {
        assertFalse(validator.isValidSalary(-500));
        assertTrue(validator.isValidSalary(0));
        assertTrue(validator.isValidSalary(500));
    }
}
