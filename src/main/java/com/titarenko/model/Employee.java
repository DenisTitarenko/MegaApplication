package com.titarenko.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.titarenko.service.JsonParser;

import java.time.LocalDate;
import java.util.Objects;

public class Employee implements Comparable<Employee> {
    private int id;
    private String name;
    private Gender sex; // enum
    private String position;
    private int salary;
    // next annotations uses for serialize/deserialize objects with LocalDate type
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateOfHire;

    public Employee() {
    }

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
    public int compareTo(Employee o) {
        return 0;
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
        return new JsonParser().serialize(this);
//        return String.format(
//                "%5d %25s %25s %25s %25d$ %25s",
//                getId(),
//                getName(),
//                getSex().getCode(),
//                getPosition(),
//                getSalary(),
//                getDateOfHire().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
//        );
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
