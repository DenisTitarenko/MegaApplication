package com.titarenko.service;

import com.titarenko.model.Employee;
import com.titarenko.model.Gender;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        employee.setName(scanner.nextLine());

        consoleWriter.writeToOutputStream("sex (M, F or OTHER): ");
        employee.setSex(readGender());

        consoleWriter.writeToOutputStream("position: ");
        employee.setPosition(scanner.nextLine());

        consoleWriter.writeToOutputStream("salary (in $): ");
        employee.setSalary(Double.parseDouble(scanner.nextLine()));

        consoleWriter.writeToOutputStream("date (dd.mm.yyyy): ");
        employee.setDateOfHire(readDate());

        return employee;
    }

    private Gender readGender() {
        String gender = null;
        boolean bool = true;
        List<String> list = Arrays.stream(Gender.values())
                .map(Enum::toString)
                .collect(Collectors.toList());
        while (bool) {
            gender = scanner.nextLine().toUpperCase();
            if (list.contains(gender)) {
                bool = false;
            } else {
                consoleWriter.writeToOutputStream("Sex field may contain next values M, F, OTHER. Try again:");
            }
        }
        return Gender.valueOf(gender);
    }

    private LocalDate readDate() {
        String date = null;
        boolean b = true;

        while (b) {
            date = scanner.nextLine();
            List<Character> list = date.chars()
                    .mapToObj(c -> (char) c)
                    .collect(Collectors.toList());
            if (list.size() == 10 && list.get(2) == '.' && list.get(5) == '.') {
                b = false;
            } else {
                consoleWriter.writeToOutputStream("Date field must be in format \"dd.mm.yyyy\". Try again:");
            }
        }
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Override
    public Integer readInt() {
        int result = 0;
        try {
            result = scanner.nextInt();
        } catch (InputMismatchException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }
}
