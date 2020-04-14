package com.titarenko.dao.handle;

import com.titarenko.model.Employee;

import java.util.List;

@Deprecated
public interface EmployeeDao {

    Integer create(Employee employee);

    Employee get(String name);

    Employee get(int id);

    Employee update(int id, Employee employee);

    boolean delete(String name);

    boolean delete(Integer id);

    List<Employee> getAll();

    List<Employee> getEmployeesWithSameSalary();

}
