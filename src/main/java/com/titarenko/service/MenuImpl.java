package com.titarenko.service;

import com.titarenko.Begin;
import com.titarenko.io.Reader;
import com.titarenko.io.Writer;
import com.titarenko.model.Operations;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MenuImpl implements Menu {

    private static final Logger LOGGER = Logger.getLogger(MenuImpl.class);
    private static final Writer WRITER = Begin.getWriter();
    private static final Reader READER = Begin.getReader();
    private EmployeeService employeeService = new EmployeeService();
    private boolean isContinue = true;

    public MenuImpl() {
        while (isContinue()) {
            WRITER.writeToOutputStream(show());
            WRITER.writeToOutputStream(perform());
            WRITER.writeToOutputStream("\n");
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
        switch (Operations.getByLabel(READER.readInt())) {

            case ADD:
                LOGGER.info("Attempt to add employee");
                WRITER.writeToOutputStream("Enter info about the new employee: ");
                return String.valueOf(employeeService.create(READER.readEmployee()));
            case FIND:
                LOGGER.info("Search employee by name");
                WRITER.writeToOutputStream("Enter the name of the employee you want to find: ");
                return employeeService.get(READER.readLine()).toString();
            case UPDATE:
                LOGGER.info("Attempt to update info about employee");
                WRITER.writeToOutputStream("Enter the id of the employee you want to update: ");
                return String.valueOf(employeeService.update(READER.readInt(), READER.readEmployee()));
            case DELETE:
                LOGGER.info("Attempt to delete employee");
                WRITER.writeToOutputStream("Enter the name of the employee you want to delete: ");
                return String.valueOf(employeeService.delete(READER.readLine()));
            case GET_ALL:
                LOGGER.info("View all employees");
                return employeeService.getAll()
                        .stream()
                        .map(Object::toString)
                        .collect(Collectors.joining("\n"));
            case SHOW:
                LOGGER.info("View all employees group by position & name");
                return employeeService.getAllGroupByPositionAndDate()
                        .stream()
                        .map(Object::toString)
                        .collect(Collectors.joining("\n"));
            case SHOW_SAME:
                LOGGER.info("View employees with the same salary");
                return employeeService.getEmployeesWithSameSalary()
                        .stream()
                        .map(Object::toString)
                        .collect(Collectors.joining("\n"));
            case INCREASE:
                LOGGER.info("Attempt to increase employee's salary");
                WRITER.writeToOutputStream("Enter the id of the employee you want to update salary: ");
                int id = READER.readInt();
                WRITER.writeToOutputStream("How much must be increased");
                int plusSalary = READER.readInt();
                return String.valueOf(employeeService.increaseSalary(id, plusSalary));
            case EXIT:
                LOGGER.info("Exit from the program");
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
