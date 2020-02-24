package com.titarenko.service;

import com.titarenko.model.Operations;

public class MenuImpl implements Menu {
    public static final ConsoleWriterImpl CONSOLE_WRITER = new ConsoleWriterImpl();
    boolean isContinue = true;
    EmployeeService service = new EmployeeServiceImpl();

    @Override
    public void show() {
        for (Operations operation : Operations.values()) {
            CONSOLE_WRITER.writeToOutputStream(operation.getLabel() + ". " + operation.getTitle());
        }
    }

    @Override
    public void perform(int label) {
        switch (Operations.getByLabel(label)) {
            case ADD:
                service.create(ReadFromConsole.readEmployee());
                break;
            case SHOW:
                break;
            case FIND:
                break;
            case DELETE:
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
