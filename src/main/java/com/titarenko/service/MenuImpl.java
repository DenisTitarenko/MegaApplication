package com.titarenko.service;

import com.titarenko.model.Operations;

import java.util.Arrays;

public class MenuImpl implements Menu {
    boolean isContinue = true;
    EmployeeService service = new EmployeeServiceImpl();

    @Override
    public void show() {
        System.out.println(Arrays.toString(Operations.values()));
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
