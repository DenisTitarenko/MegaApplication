package com.titarenko.service;

import com.titarenko.dao.JdbcConnection;
import com.titarenko.model.Employee;

public class EmployeeServiceImpl implements EmployeeService {
    JdbcConnection connection = new JdbcConnection();

    @Override
    public Integer create(Employee employee) {
//        validations
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
    public Employee get(String name) {
        return null;
    }
}
