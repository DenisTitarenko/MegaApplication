package com.titarenko.service;

import com.titarenko.io.Reader;
import com.titarenko.io.Writer;
import com.titarenko.model.Operations;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
@Profile("default")
public class MenuImpl implements Menu {

    private static final Logger LOGGER = Logger.getLogger(MenuImpl.class);
    private Writer WRITER;
    private Reader READER;
    private boolean isContinue = true;
    private EmployeeService service;

    @Autowired
    public MenuImpl(EmployeeService service, Reader reader, Writer writer) {
        this.service = service;
        READER = reader;
        WRITER = writer;
        init();
    }

    public void init() {
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
                return String.valueOf(service.create(READER.readEmployee()));
            case FIND:
                LOGGER.info("Search employee by name");
                WRITER.writeToOutputStream("Enter the name of the employee you want to find: ");
                return service.get(READER.readLine()).toString();
            case UPDATE:
                LOGGER.info("Attempt to update info about employee");
                WRITER.writeToOutputStream("Enter the id of the employee you want to update: ");
                return String.valueOf(service.update(READER.readInt(), READER.readEmployee()));
            case DELETE:
                LOGGER.info("Attempt to delete employee");
                WRITER.writeToOutputStream("Enter the name of the employee you want to delete: ");
                return String.valueOf(service.delete(READER.readLine()));
            case GET_ALL:
                LOGGER.info("View all employees");
                return service.getAll()
                        .stream()
                        .map(Object::toString)
                        .collect(Collectors.joining("\n"));
            case SHOW:
                LOGGER.info("View all employees group by position & name");
                return service.getAllGroupByPositionAndDate()
                        .stream()
                        .map(Object::toString)
                        .collect(Collectors.joining("\n"));
            case SHOW_SAME:
                LOGGER.info("View employees with the same salary");
                return service.getEmployeesWithSameSalary()
                        .stream()
                        .map(Object::toString)
                        .collect(Collectors.joining("\n"));
            case INCREASE:
                LOGGER.info("Attempt to increase employee's salary");
                WRITER.writeToOutputStream("Enter the id of the employee you want to update salary: ");
                int id = READER.readInt();
                WRITER.writeToOutputStream("How much must be increased");
                int plusSalary = READER.readInt();
                return String.valueOf(service.increaseSalary(id, plusSalary));
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
