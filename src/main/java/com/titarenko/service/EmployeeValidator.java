package com.titarenko.service;

import com.titarenko.model.Employee;

import java.time.LocalDate;

public class EmployeeValidator implements Validator {

    private static final LocalDate REFERENCE_POINT = LocalDate.parse("1970-01-01");

    @Override
    public boolean isValidEmployee(Employee employee) {
        return employee != null
                && employee.getName() != null
                && employee.getSex() != null
                && employee.getPosition() != null
                && employee.getDateOfHire() != null
                && isValidName(employee.getName())
                && isValidDate(employee.getDateOfHire())
                && isValidSalary(employee.getSalary());
    }

    public boolean isValidName(String name) {
        return name.matches("([A-Za-z\\s-])+");
    }

    public boolean isValidSalary(int salary) {
        return salary >= 0;
    }

    public boolean isValidDate(LocalDate localDate) {
        return localDate.isBefore(LocalDate.now()) && localDate.isAfter(REFERENCE_POINT);
    }


}
