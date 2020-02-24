package com.titarenko.model;

import java.util.Calendar;
import java.util.Objects;

public class Employee {
    String name;
    Gender sex; // enum
    String position;
    double salary;
    Calendar dateOfHire;

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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Calendar getDateOfHire() {
        return dateOfHire;
    }

    public void setDateOfHire(Calendar dateOfHire) {
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
        return Double.compare(empl.salary, salary) == 0 &&
                name.equals(empl.name) &&
                sex == empl.sex &&
                position.equals(empl.position) &&
                Objects.equals(dateOfHire, empl.dateOfHire);
    }

    @Override
    public int hashCode() {
        final int PRIME = 17;
        int result = 1;
        result = PRIME * result + name.hashCode();
        result = PRIME * result + position.hashCode();
        result = PRIME * result + sex.hashCode();
        result = (int) (PRIME * result + salary);
        result = PRIME * result + dateOfHire.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return name + "\t\t" +
                sex.getCode() + "\t\t" +
                position + "\t\t" +
                salary + "$\t\t" +
                dateOfHire.get(Calendar.DATE) + "." +
                (dateOfHire.get(Calendar.MONTH) + 1) + "." +
                dateOfHire.get(Calendar.YEAR);
    }
}
