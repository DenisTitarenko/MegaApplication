package com.titarenko.dao;

import com.titarenko.model.Employee;

import java.util.List;

public interface EmployeeDao {

    /**
     * return new id
     */
    Integer create(Employee employee);

    Employee update(Integer id, Employee employee);

    boolean delete(String name);

    Employee get(String name);

    List<Employee> getAll();
}
