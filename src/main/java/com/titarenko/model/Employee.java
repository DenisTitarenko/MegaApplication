package com.titarenko.model;

import com.titarenko.service.JsonParser;

import java.time.LocalDate;
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

    public String getName() {
        return name;
    }

    public Gender getSex() {
        return sex;
    }

    public String getPosition() {
        return position;
    }

    public int getSalary() {
        return salary;
    }

    public LocalDate getDateOfHire() {
        return dateOfHire;
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
        return new JsonParser().toJson(this);
    }


    public static class Builder {

        private Employee newEmployee;

        public Builder() {
            this.newEmployee = new Employee();
        }

        public Builder setId(int id) {
            newEmployee.id = id;
            return this;
        }

        public Builder setName(String name) {
            newEmployee.name = name;
            return this;
        }

        public Builder setSex(Gender sex) {
            newEmployee.sex = sex;
            return this;
        }

        public Builder setPosition(String position) {
            newEmployee.position = position;
            return this;
        }

        public Builder setSalary(int salary) {
            newEmployee.salary = salary;
            return this;
        }

        public Builder setDateOfHire(LocalDate dateOfHire) {
            newEmployee.dateOfHire = dateOfHire;
            return this;
        }

        public Employee build() {
            return newEmployee;
        }
    }
}
