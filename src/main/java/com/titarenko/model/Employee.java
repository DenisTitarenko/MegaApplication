package com.titarenko.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.titarenko.service.JsonParser;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "employees")
@NamedQuery(
        name = "Employee_getEmployeesWithSameSalary",
        query = "FROM Employee WHERE salary\n" +
                "IN (SELECT salary FROM Employee GROUP by salary HAVING count(*) > 1)\n" +
                "ORDER BY salary DESC"
)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private Gender sex; // enum

    @Column(name = "position")
    private String position;

    @Column(name = "salary")
    private int salary;

    @Column(name = "dateOfHire")
    // next annotations uses for serialize/deserialize objects with LocalDate type
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateOfHire;

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
    }
}
