package com.titarenko;

import com.titarenko.dao.EmployeeDao;
import com.titarenko.model.Employee;
import com.titarenko.model.Gender;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class UnitTestParent {
    public LinkedList<Employee> database = new LinkedList<>();

    public Employee vasil = new Employee.Builder()
            .withId(1)
            .withName("Vasil")
            .withSex(Gender.M)
            .withPosition("position")
            .withSalary(150)
            .withDateOfHire(LocalDate.parse("2012-12-12"))
            .build();
    public Employee petr = new Employee.Builder()
            .withId(2)
            .withName("Petr")
            .withSex(Gender.M)
            .withPosition("position2")
            .withSalary(150)
            .withDateOfHire(LocalDate.parse("2011-11-11"))
            .build();
    public Employee stepa = new Employee.Builder()
            .withId(3)
            .withName("Stepa")
            .withSex(Gender.M)
            .withPosition("position")
            .withSalary(200)
            .withDateOfHire(LocalDate.parse("2013-10-10"))
            .build();

    public EmployeeDao employeeDao = new EmployeeDao() {
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
            return Arrays.asList(vasil, petr);
        }
    };
}
