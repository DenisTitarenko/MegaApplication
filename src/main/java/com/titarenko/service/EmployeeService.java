package com.titarenko.service;

import com.titarenko.Begin;
import com.titarenko.dao.JdbcEmployeeDaoImpl;
import com.titarenko.io.Writer;
import com.titarenko.model.Employee;
import org.apache.log4j.Logger;

import java.util.List;

public class EmployeeService {

    private static final Logger LOGGER = Logger.getLogger(EmployeeService.class);

    private JdbcEmployeeDaoImpl employeeDao = new JdbcEmployeeDaoImpl();
    private EmployeeValidator validator = new EmployeeValidator();
    private Writer writer = Begin.getWriter();

    public Integer create(Employee employee) {
        int result;
        if (validator.isValidEmployee(employee)) {
            result = employeeDao.create(employee);
        } else {
            writer.writeToOutputStream("Seems like input data wasn't correct");
            LOGGER.error("Employee wasn't added. Input data wasn't correct");
            return null;
        }
        LOGGER.info("Employee " + employee.getName() + " added");
        return result;
    }

    public Employee get(String name) {
        if (!validator.isValidName(name)) {
            writer.writeToOutputStream("Oops.. Seems like input name wasn't correct");
            return null;
        }
        return employeeDao.get(name);
    }

    public Employee update(Integer id, Employee employee) {
        if (employeeDao.getListOfId().contains(id)) {
            if (validator.isValidEmployee(employee)) {
                employeeDao.update(id, employee);
            } else {
                writer.writeToOutputStream("Oops.. Seems like input data wasn't correct");
                LOGGER.error("Input data wasn't correct");
                return null;
            }
        } else {
            writer.writeToOutputStream("Employee with such id doesn't exist");
            LOGGER.error("Employee with such id doesn't exist");
            return null;
        }
        LOGGER.info("Employee's info updated");
        return employee;
    }

    public boolean delete(String name) {
        if (!validator.isValidName(name)) {
            writer.writeToOutputStream("Oops.. Seems like input name wasn't correct");
            LOGGER.error("Employee with such name doesn't exist");
            return false;
        }
        LOGGER.info("Employee " + name + " deleted");
        return employeeDao.delete(name);
    }

    public List<Employee> getAll() {
        return employeeDao.getAll();
    }

    public List<Employee> getAllGroupByPositionAndDate() {
        return employeeDao.getAllGroupByPositionAndDate();
    }

    public List<Employee> getEmployeesWithSameSalary() {
        return employeeDao.getEmployeesWithSameSalary();
    }

    public boolean increaseSalary(int id, int plusSalary) {
        if (employeeDao.getListOfId().contains(id)) {
            employeeDao.increaseSalary(id, plusSalary);
        } else {
            writer.writeToOutputStream("Employee with such id doesn't exist");
            LOGGER.error("Employee with such id doesn't exist");
            return false;
        }
        LOGGER.info("Salary of employee with ID=" + id +" was increased");
        return true;
    }
}
