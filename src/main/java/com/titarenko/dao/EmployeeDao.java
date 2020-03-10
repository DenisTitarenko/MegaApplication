package com.titarenko.dao;

import com.titarenko.model.Employee;

import java.util.List;

public interface EmployeeDao {

    Integer create(Employee employee);

    Employee get(String name);

    Employee update(Integer id, Employee employee);

    boolean delete(String name);

    List<Employee> getAll();

    List<Employee> getAllGroupByPositionAndDate();

    List<Employee> getEmployeesWithSameSalary();

    boolean increaseSalary(int id, int plusSalary);

    List<Employee> getListEmployees(String query);

    List<Integer> getListOfId();

}
