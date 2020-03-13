package com.titarenko.service;

import com.titarenko.dao.EmployeeDao;
import com.titarenko.model.Employee;
import com.titarenko.model.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceImplTest_withoutMocks {

    private EmployeeServiceImpl employeeService;
    private List<Employee> database;
    private Employee vasil = new Employee.Builder()
            .withId(1)
            .withName("Vasil")
            .withSex(Gender.M)
            .withPosition("position")
            .withSalary(150)
            .withDateOfHire(LocalDate.parse("2012-12-12"))
            .build();
    private Employee petr = new Employee.Builder()
            .withId(2)
            .withName("Petr")
            .withSex(Gender.M)
            .withPosition("position2")
            .withSalary(150)
            .withDateOfHire(LocalDate.parse("2011-11-11"))
            .build();
    private Employee stepa = new Employee.Builder()
            .withId(3)
            .withName("Stepa")
            .withSex(Gender.M)
            .withPosition("position3")
            .withSalary(200)
            .withDateOfHire(LocalDate.parse("2010-10-10"))
            .build();


    @BeforeEach
    public void setup() {
        database = new ArrayList<>();
        Collections.addAll(database, vasil, petr, stepa);
        employeeService = new EmployeeServiceImpl(new EmployeeDao() {
            @Override
            public Integer create(Employee employee) {
                database.add(employee);
                return database.get(database.size() - 1).getId();
            }

            @Override
            public Employee get(String name) {
                Employee returned = null;
                for (Employee empl : database) {
                    if (name.equals(empl.getName())) {
                        returned = empl;
                    }
                }
                return returned;
            }

            @Override
            public Employee get(int id) {
                Employee returned = null;
                for (Employee empl : database) {
                    if (id == empl.getId()) {
                        returned = empl;
                    }
                }
                return returned;
            }

            @Override
            public Employee update(Integer id, Employee employee) {
                employee.setId(id);
                int index = database.indexOf(get(id));
                database.set(index, employee);
                return database.get(index);
            }

            @Override
            public boolean delete(String name) {
                int initSize = database.size();
                database.remove(get(name));
                return initSize == database.size() + 1;
            }

            @Override
            public List<Employee> getAll() {
                return database;
            }

            @Override
            public List<Employee> getEmployeesWithSameSalary() {
                Map<Integer, List<Employee>> map = new HashMap<>();
                for (Employee empl : database) {
                    if (map.containsKey(empl.getSalary())) {
                        List<Employee> list = map.get(empl.getSalary());
                        list.add(empl);
                        map.put(empl.getSalary(), list);
                    } else {
                        List<Employee> list = new ArrayList<>();
                        list.add(empl);
                        map.put(empl.getSalary(), list);
                    }
                }
                List<Employee> returned = new ArrayList<>();
                for (int key : map.keySet()) {
                    if (map.get(key).size() > 1) {
                        returned.addAll(map.get(key));
                    }
                }
                return returned;
            }
        });
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
}
