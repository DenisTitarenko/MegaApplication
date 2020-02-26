package com.titarenko.service;

import com.titarenko.model.Employee;
import java.time.ZoneId;

public class EmployeeValidator implements Validator{

    @Override
    public boolean isValidEmployee(Employee employee) {
        int checkName = 0;
        for (char c : employee.getName().toCharArray()) {
            if (Character.isLetter(c))
                checkName++;
        }
        long timeInMillis = employee.getDateOfHire().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        int salary = employee.getSalary();

        return (checkName == employee.getName().length()
                && timeInMillis < System.currentTimeMillis()
                && salary >= 0);
    }
}
