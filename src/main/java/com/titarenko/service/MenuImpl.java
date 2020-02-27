package com.titarenko.service;

import com.titarenko.model.Operations;

import java.util.stream.Collectors;

public class MenuImpl implements Menu {
    public static final Writer CONSOLE_WRITER = new ConsoleWriterImpl();
    public static final Reader CONSOLE_READER = new ConsoleReaderImpl();
    boolean isContinue = true;

    public MenuImpl() {
        while (isContinue()) {
            show();
            perform();
            new ConsoleWriterImpl().writeToOutputStream("\n");
        }
    }

    EmployeeService employeeService = new EmployeeService();

    @Override
    public void show() {
        for (Operations operation : Operations.values()) {
            CONSOLE_WRITER.writeToOutputStream(operation.getLabel() + ". " + operation.getTitle());
        }
    }

    @Override
    public void perform() {
        switch (Operations.getByLabel(new ConsoleReaderImpl().readInt())) {
            case ADD:
                CONSOLE_WRITER.writeToOutputStream("Enter info about the new employee: ");
                employeeService.create(CONSOLE_READER.readEmployee());
                break;
            case FIND:
                CONSOLE_WRITER.writeToOutputStream("Enter the name of the employee you want to find: ");
                CONSOLE_WRITER.writeToOutputStream(employeeService.get(CONSOLE_READER.readLine()).toString());
                break;
            case UPDATE:
                CONSOLE_WRITER.writeToOutputStream("Enter the id of the employee you want to update: ");
                employeeService.update(CONSOLE_READER.readInt(), CONSOLE_READER.readEmployee());
                break;
            case DELETE:
                CONSOLE_WRITER.writeToOutputStream("Enter the name of the employee you want to delete: ");
                employeeService.delete(CONSOLE_READER.readLine());
                break;
            case GET_ALL:
                CONSOLE_WRITER.writeToOutputStream(employeeService.getAll()
                        .stream()
                        .map(Object::toString)
                        .collect(Collectors.joining("\n")));
                break;
            case SHOW:
                CONSOLE_WRITER.writeToOutputStream(employeeService.getAllGroupByPositionAndDate()
                        .stream()
                        .map(Object::toString)
                        .collect(Collectors.joining("\n")));
                break;
            case SHOW_SAME:
                CONSOLE_WRITER.writeToOutputStream(employeeService.getEmployeesWithSameSalary()
                        .stream()
                        .map(Object::toString)
                        .collect(Collectors.joining("\n")));
                break;
            case INCREASE:
                CONSOLE_WRITER.writeToOutputStream("Enter the id of the employee you want to update salary: ");
                int id = CONSOLE_READER.readInt();
                CONSOLE_WRITER.writeToOutputStream("How much must be increased");
                int plusSalary = CONSOLE_READER.readInt();
                employeeService.increaseSalary(id, plusSalary);
                break;
            case EXIT:
                isContinue = false;
        }
    }

    @Override
    public boolean isContinue() {
        return isContinue;
    }
}
