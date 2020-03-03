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

    private Employee(Builder builder) {
        id = builder.id;
        name = builder.name;
        sex = builder.sex;
        position = builder.position;
        salary = builder.salary;
        dateOfHire = builder.dateOfHire;
    }

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

    public static final class Builder {
        private int id;
        private String name;
        private Gender sex;
        private String position;
        private int salary;
        private LocalDate dateOfHire;

        public Builder() {
        }

        public Builder withId(int val) {
            id = val;
            return this;
        }

        public Builder withName(String val) {
            name = val;
            return this;
        }

        public Builder withSex(Gender val) {
            sex = val;
            return this;
        }

        public Builder withPosition(String val) {
            position = val;
            return this;
        }

        public Builder withSalary(int val) {
            salary = val;
            return this;
        }

        public Builder withDateOfHire(LocalDate val) {
            dateOfHire = val;
            return this;
        }

        public Employee build() {
            return new Employee(this);
        }
    }
}
