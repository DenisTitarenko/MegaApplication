package com.titarenko;

import com.titarenko.dao.EmployeeDao;
import com.titarenko.model.Employee;
import com.titarenko.model.enumeration.Gender;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class UnitTestParent {
    public LinkedList<Employee> database = new LinkedList<>();

    public Employee vasil = Employee.builder()
            .id(1)
            .name("Vasil")
            .sex(Gender.M)
            .position("position")
            .salary(150)
            .dateOfHire(LocalDate.parse("2012-12-12"))
            .build();
    public Employee petr = Employee.builder()
            .id(2)
            .name("Petr Ovich")
            .sex(Gender.M)
            .position("position2")
            .salary(150)
            .dateOfHire(LocalDate.parse("2011-11-11"))
            .build();
    public Employee stepa = Employee.builder()
            .id(3)
            .name("Stepa")
            .sex(Gender.OTHER)
            .position("position")
            .salary(200)
            .dateOfHire(LocalDate.parse("2013-10-10"))
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
        public Employee update(int id, Employee employee) {
            employee.setId(id);
            int index = database.indexOf(get(id));
            database.set(index, employee);
            return database.get(index);
        }

        @Override
        public boolean delete(String name) {
            return database.remove(get(name));
        }

        @Override
        public boolean delete(Integer id) {
            return database.remove(get(id));
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
