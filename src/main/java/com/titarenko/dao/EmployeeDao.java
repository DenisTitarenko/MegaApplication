package com.titarenko.dao;

import com.titarenko.model.Employee;

import java.util.List;

public interface EmployeeDao {

    Integer create(Employee employee);

    Employee get(String name);

    Employee get(int id);

    Employee update(int id, Employee employee);

    boolean delete(String name);

    List<Employee> getAll();

    List<Employee> getEmployeesWithSameSalary();

}
