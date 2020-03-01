package com.titarenko.service;

import com.titarenko.Begin;
import com.titarenko.io.Reader;
import com.titarenko.io.Writer;
import com.titarenko.model.Operations;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MenuImpl implements Menu {
    private static final Writer CONSOLE_WRITER = Begin.getWriter();
    private static final Reader CONSOLE_READER = Begin.getReader();
    private EmployeeService employeeService = new EmployeeService();
    private boolean isContinue = true;

    public MenuImpl() {
        while (isContinue()) {
            CONSOLE_WRITER.writeToOutputStream(show());
            CONSOLE_WRITER.writeToOutputStream(perform());
            CONSOLE_WRITER.writeToOutputStream("\n");
        }
    }

    @Override
    public String show() {
        return Arrays.stream(Operations.values())
                .map(operation -> operation.getLabel() + ". " + operation.getTitle())
                .reduce((s1, s2) -> s1 + "\n" + s2)
                .orElse("");
    }

    @Override
    public String perform() {
        switch (Operations.getByLabel(CONSOLE_READER.readInt())) {
            case ADD:
                CONSOLE_WRITER.writeToOutputStream("Enter info about the new employee: ");
                return String.valueOf(employeeService.create(CONSOLE_READER.readEmployee()));
            case FIND:
                CONSOLE_WRITER.writeToOutputStream("Enter the name of the employee you want to find: ");
                return employeeService.get(CONSOLE_READER.readLine()).toString();
            case UPDATE:
                CONSOLE_WRITER.writeToOutputStream("Enter the id of the employee you want to update: ");
                return String.valueOf(employeeService.update(CONSOLE_READER.readInt(), CONSOLE_READER.readEmployee()));
            case DELETE:
                CONSOLE_WRITER.writeToOutputStream("Enter the name of the employee you want to delete: ");
                return String.valueOf(employeeService.delete(CONSOLE_READER.readLine()));
            case GET_ALL:
                return employeeService.getAll()
                        .stream()
                        .map(Object::toString)
                        .collect(Collectors.joining("\n"));
            case SHOW:
                return employeeService.getAllGroupByPositionAndDate()
                        .stream()
                        .map(Object::toString)
                        .collect(Collectors.joining("\n"));
            case SHOW_SAME:
                return employeeService.getEmployeesWithSameSalary()
                        .stream()
                        .map(Object::toString)
                        .collect(Collectors.joining("\n"));
            case INCREASE:
                CONSOLE_WRITER.writeToOutputStream("Enter the id of the employee you want to update salary: ");
                int id = CONSOLE_READER.readInt();
                CONSOLE_WRITER.writeToOutputStream("How much must be increased");
                int plusSalary = CONSOLE_READER.readInt();
                return String.valueOf(employeeService.increaseSalary(id, plusSalary));
            case EXIT:
                isContinue = false;
            default:
                return "exit";
        }
    }

    @Override
    public boolean isContinue() {
        return isContinue;
    }
}
