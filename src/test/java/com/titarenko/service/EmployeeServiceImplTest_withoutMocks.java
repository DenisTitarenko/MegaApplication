package com.titarenko.service;

import com.titarenko.dao.EmployeeDao;
import com.titarenko.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeServiceImplTest_withoutMocks {

    private EmployeeServiceImpl employeeService;
    private Employee vasil = new Employee.Builder().withName("Vasil").withSalary(150).build();

    @BeforeEach
    public void setup() {
        employeeService = new EmployeeServiceImpl(new EmployeeDao() {
            @Override
            public Integer create(Employee employee) {
                return null;
            }

            @Override
            public Employee get(String name) {
                return null;
            }

            @Override
            public Employee get(int id) {
                return null;
            }

            @Override
            public Employee update(Integer id, Employee employee) {
                return null;
            }

            @Override
            public boolean delete(String name) {
                return false;
            }

            @Override
            public List<Employee> getAll() {
                return Collections.singletonList(vasil);
            }

            @Override
            public List<Employee> getEmployeesWithSameSalary() {
                return null;
            }
        });
    }

    @Test
    public void testGetAll() {
        List<Employee> all = employeeService.getAll();
        assertEquals(1, all.size());
        assertEquals(vasil, all.get(0));
    }
}
