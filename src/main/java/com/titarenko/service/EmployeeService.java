package com.titarenko.service;

import com.titarenko.dto.EmployeeDto;
import com.titarenko.model.Employee;

import java.util.List;

public interface EmployeeService {

    Integer create(Employee employee);

    Employee get(Integer id);

    Employee update(Integer id, Employee employee);

    Employee delete(Integer id);

    List<Employee> getAll();

    List<Employee> getAllGroupByPositionAndDate();

    List<Employee> getEmployeesWithSameSalary();

    Employee buildToEntity(EmployeeDto employeeDto);

    EmployeeDto buildToDto(Employee employee);
}
