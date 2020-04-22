package com.titarenko.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.titarenko.config.GlobalConfig;
import com.titarenko.model.enumeration.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDto {

    private int id;

    private String name;

    private Gender sex;

    private String departmentName;

    private String position;

    private Set<String> projects;

    private int salary;

    @JsonFormat(pattern = GlobalConfig.DATE_FORMAT_PATTERN)
    private LocalDate dateOfHire;

}
