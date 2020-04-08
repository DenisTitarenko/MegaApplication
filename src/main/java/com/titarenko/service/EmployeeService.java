package com.titarenko.service;

import com.titarenko.dto.EmployeeDto;
import com.titarenko.model.Employee;

import java.util.List;

public interface EmployeeService {

    Integer create(Employee employee);

    Employee get(String name);

    Employee update(Integer id, Employee employee);

    boolean delete(String name);

    boolean delete(Integer id);

    List<Employee> getAll();

    List<Employee> getAllGroupByPositionAndDate();

    List<Employee> getEmployeesWithSameSalary();

    boolean increaseSalary(int id, int plusSalary);

    Employee buildToEntity(EmployeeDto employeeDto);

    EmployeeDto buildToDto(Employee employee);
}
