package com.titarenko.service;

import com.titarenko.model.Employee;
import com.titarenko.model.Gender;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ConsoleReaderImpl implements Reader {

    Scanner scanner = new Scanner(System.in);
    ConsoleWriterImpl consoleWriter = new ConsoleWriterImpl();

    @Override
    public Employee readEmployee() {
        Employee employee = new Employee();

        consoleWriter.writeToOutputStream("name: ");
        employee.setName(readLine());

        consoleWriter.writeToOutputStream("sex (M, F or OTHER): ");
        employee.setSex(readGender());

        consoleWriter.writeToOutputStream("position: ");
        employee.setPosition(readLine());

        consoleWriter.writeToOutputStream("salary : ");
        employee.setSalary(readInt());

        consoleWriter.writeToOutputStream("date (dd.mm.yyyy): ");
        employee.setDateOfHire(readDate());

        return employee;
    }


    @Override
    public Integer readInt() {
        int result = 0;
        try {
            result = Integer.parseInt(scanner.nextLine());
        } catch (InputMismatchException | NumberFormatException e) {
            consoleWriter.writeToOutputStream("This field should contain only integer. Try again: ");
            readInt();
        }
        return result;
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

    @Override
    public Gender readGender() {
        String gender = null;
        boolean bool = true;
        List<String> list = Arrays.stream(Gender.values())
                .map(Enum::toString)
                .collect(Collectors.toList());

        while (bool) {
            gender = readLine().toUpperCase();
            if (list.contains(gender)) {
                bool = false;
            } else {
                consoleWriter.writeToOutputStream("Sex field may contain next values M, F, OTHER. Try again:");
            }
        }
        return Gender.valueOf(gender);
    }

    @Override
    public LocalDate readDate() {
        LocalDate ld = null;
        boolean b = true;

        while (b) {
            String date = readLine();
            if (date.length() == 10 && date.charAt(2) == '.' && date.charAt(5) == '.') {
                b = false;
                try {
                    ld = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                } catch (DateTimeParseException e) {
                    b = true;
                    consoleWriter.writeToOutputStream("Such a date does not exist. Try again:");
                }
            } else {
                consoleWriter.writeToOutputStream("Date field must be in format \"dd.mm.yyyy\". Try again:");
            }
        }
        return ld;
    }
}
