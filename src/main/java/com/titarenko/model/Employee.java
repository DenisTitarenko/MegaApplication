package com.titarenko.model;

import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Employee {
    private int id;
    private String name;
    private Gender sex; // enum
    private String position;
    private int salary;
    private LocalDate dateOfHire;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getSex() {
        return sex;
    }

    public void setSex(Gender sex) {
        this.sex = sex;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public LocalDate getDateOfHire() {
        return dateOfHire;
    }

    public void setDateOfHire(LocalDate dateOfHire) {
        this.dateOfHire = dateOfHire;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Employee empl = (Employee) obj;
        return empl.salary == salary &&
                Objects.equals(name, empl.name) &&
                Objects.equals(sex, empl.sex) &&
                Objects.equals(position, empl.position) &&
                Objects.equals(dateOfHire, empl.dateOfHire);
    }

    @Override
    public int hashCode() {
        return Objects.hash(salary, name, sex, position, dateOfHire);
    }

    @Override
    public String toString() {
        String format = "%5d %25s %25s %25s %25d$ %25s";
        return String.format(
                format,
                getId(),
                getName(),
                getSex().getCode(),
                getPosition(),
                getSalary(),
                getDateOfHire().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        );
    }
}
