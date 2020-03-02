package com.titarenko.service;

import com.titarenko.io.FileReaderImpl;
import com.titarenko.io.FileWriterImpl;
import com.titarenko.model.Operations;

import java.util.Arrays;
import java.util.stream.Collectors;

public class FileMenuImpl implements Menu {

    private static final FileWriterImpl FILE_WRITER = new FileWriterImpl();
    private static final FileReaderImpl FILE_READER = new FileReaderImpl();
    private EmployeeService employeeService = new EmployeeService();
    private boolean isContinue = true;

    public FileMenuImpl() {
        FILE_WRITER.clearFile();
        while (isContinue()) {
            FILE_WRITER.writeToOutputStream(show());
            FILE_WRITER.writeToOutputStream(perform());
            FILE_WRITER.writeToOutputStream("\n");
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
        switch (Operations.getByLabel(Integer.parseInt(FILE_READER.readLine()))) {
            case ADD:
                FILE_WRITER.writeToOutputStream("Enter info about the new employee: ");
                return String.valueOf(employeeService.create(FILE_READER.readEmployee()));
            case FIND:
                FILE_WRITER.writeToOutputStream("Enter the name of the employee you want to find: ");
                return employeeService.get(FILE_READER.readLine()).toString();
            case UPDATE:
                FILE_WRITER.writeToOutputStream("Enter the id of the employee you want to update: ");
                return String.valueOf(employeeService.update(FILE_READER.readInt(), FILE_READER.readEmployee()));
            case DELETE:
                FILE_WRITER.writeToOutputStream("Enter the name of the employee you want to delete: ");
                return String.valueOf(employeeService.delete(FILE_READER.readLine()));
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
                FILE_WRITER.writeToOutputStream("Enter the id of the employee you want to update salary: ");
                int id = FILE_READER.readInt();
                FILE_WRITER.writeToOutputStream("How much must be increased");
                int plusSalary = FILE_READER.readInt();
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
