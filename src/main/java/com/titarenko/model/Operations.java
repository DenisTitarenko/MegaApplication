package com.titarenko.model;

public enum Operations {
    ADD(1, "Add employee to DB;"),
    SHOW(2, "Show all employees group by department & date of start work;"),
    FIND(3, "Find employee by name;"),
    DELETE(4, "Delete employee by name;"),
    INCREASE(5, "Increase someone's salary;"),
    SHOW_SAME(6, "Show names of employees with same salary;"),
    EXIT(0, "Exit.");

    int label;
    String title;

    Operations(int label, String title) {
        this.label = label;
        this.title = title;
    }

    public static Operations getByLabel(int label) {
        for (Operations op : Operations.values()) {
            if (op.label == label) {
                return op;
            }
        }
        throw new UnsupportedOperationException("Wrong label for Operations");
    }
}
