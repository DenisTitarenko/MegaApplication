package com.titarenko.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.titarenko.model.enumeration.Gender;
import com.titarenko.service.JsonParser;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
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
    private Integer id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private Gender sex;

    @JsonIgnore
    @ManyToOne
    private Department department;

    @Column(name = "position")
    private String position;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "employee_project",
            joinColumns = {@JoinColumn(name = "employee_id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id")})
    private Set<Project> projects;

    @Column(name = "salary")
    private int salary;

    @Column(name = "dateOfHire")
    // next annotations uses for serialize/deserialize objects with LocalDate type
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateOfHire;

    @Override
    public String toString() {
        return new JsonParser().serialize(this);
    }
}
