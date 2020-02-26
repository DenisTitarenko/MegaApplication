package com.titarenko.model;

public enum Operations {
    ADD(1, "Add employee to DB;"),
    FIND(2, "Find employee by name;"),
    UPDATE(3, "Update employee info;"),
    DELETE(4, "Delete employee by name;"),
    SHOW(5, "Show all employees group by department & date of start work;"),
    INCREASE(6, "Increase someone's salary;"),
    SHOW_SAME(7, "Show names of employees with same salary;"),
    EXIT(0, "Exit.");

    private int label;
    private String title;

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

    public int getLabel() {
        return label;
    }

    public String getTitle() {
        return title;
    }
}
