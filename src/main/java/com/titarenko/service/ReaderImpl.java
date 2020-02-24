package com.titarenko.service;

import com.titarenko.model.Employee;
import com.titarenko.model.Gender;

import java.util.*;

public class ReaderImpl implements Reader {

    Scanner scanner = new Scanner(System.in);
    ConsoleWriterImpl consoleWriter = new ConsoleWriterImpl();

    @Override
    public Employee readEmployee() {
        Employee employee = new Employee();

        consoleWriter.writeToOutputStream("name: ");
        employee.setName(scanner.nextLine());

        consoleWriter.writeToOutputStream("sex (M, F or OTHER): ");
        employee.setSex(Gender.valueOf(scanner.nextLine()));

        consoleWriter.writeToOutputStream("position: ");
        employee.setPosition(scanner.nextLine());

        consoleWriter.writeToOutputStream("salary (in $): ");
        employee.setSalary(Double.parseDouble(scanner.nextLine()));

        consoleWriter.writeToOutputStream("date (dd.mm.yyyy): ");
        String[] date = scanner.nextLine().split("\\.");
        int d = Integer.parseInt(date[0]);
        int m = Integer.parseInt(date[1]) - 1;
        int y = Integer.parseInt(date[2]);
        employee.setDateOfHire(new GregorianCalendar(y, m, d));

        return employee;
    }

    @Override
    public Integer readInt() {
        return scanner.nextInt();
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }
}
