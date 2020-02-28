package com.titarenko.service;

import com.titarenko.model.Employee;
import java.time.LocalDate;

public class EmployeeValidator implements Validator {

    public boolean isValidName(String name) {
        return name.matches("([A-Za-z\\s])+");
    }

    @Override
    public boolean isValidEmployee(Employee employee) {
        return (isValidName(employee.getName())
                && employee.getDateOfHire().isBefore(LocalDate.now())
                && employee.getSalary() >= 0);
    }
}
