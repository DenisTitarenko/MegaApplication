package com.titarenko.service;

import com.titarenko.dao.EmployeeDao;
import com.titarenko.dao.JdbcEmployeeDaoImpl;
import com.titarenko.model.Operations;

public class MenuImpl implements Menu {
    public static final Writer CONSOLE_WRITER = new ConsoleWriterImpl();
    public static final Reader CONSOLE_READER = new ConsoleReaderImpl();
    boolean isContinue = true;
    EmployeeDao employeeDao = new JdbcEmployeeDaoImpl();

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
                employeeDao.create(CONSOLE_READER.readEmployee());
                break;
            case FIND:
                CONSOLE_WRITER.writeToOutputStream("Enter the name of the employee you want to find: ");
                CONSOLE_WRITER.writeToOutputStream(employeeDao.get(CONSOLE_READER.readLine()).toString());
                break;
            case UPDATE:
                CONSOLE_WRITER.writeToOutputStream("Enter the id of the employee you want to update: ");
                employeeDao.update(CONSOLE_READER.readInt(), CONSOLE_READER.readEmployee());
                break;
            case DELETE:
                CONSOLE_WRITER.writeToOutputStream("Enter the name of the employee you want to delete: ");
                employeeDao.delete(CONSOLE_READER.readLine());
                break;
            case SHOW:
                break;
            case INCREASE:
                break;
            case SHOW_SAME:
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
