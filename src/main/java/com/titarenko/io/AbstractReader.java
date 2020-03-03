package com.titarenko.io;

import com.titarenko.Begin;
import com.titarenko.model.Employee;
import com.titarenko.model.Gender;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractReader implements Reader {

    Writer consoleWriter = Begin.getWriter();

    @Override
    public Employee readEmployee() {
        Employee.Builder builder = new Employee.Builder();

        consoleWriter.writeToOutputStream("name: ");
        builder.setName(readLine());

        consoleWriter.writeToOutputStream("sex (M, F or OTHER): ");
        builder.setSex(readGender());

        consoleWriter.writeToOutputStream("position: ");
        builder.setPosition(readLine());

        consoleWriter.writeToOutputStream("salary: ");
        builder.setSalary(readInt());

        consoleWriter.writeToOutputStream("date (dd.mm.yyyy): ");
        builder.setDateOfHire(readDate());

        return builder.build();
    }

    @Override
    public Integer readInt() {
        int result = 0;
        boolean b = true;
        while (b) {
            try {
                result = Integer.parseInt(readLine());
                b = false;
            } catch (InputMismatchException | NumberFormatException e) {
                consoleWriter.writeToOutputStream("This field should contain only integer. Try again: ");
            }
        }
        return result;
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
