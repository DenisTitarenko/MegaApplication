package com.titarenko.service;

import com.titarenko.dto.EmployeeDto;
import com.titarenko.dto.ProjectDto;
import com.titarenko.model.Employee;
import com.titarenko.model.Project;

import java.util.List;

public interface ProjectService {

    Integer create(Project project);

    Project get(String name);

    Project update(Integer id, Project project);

    Project delete(String name);

    List<Project> getAll();

    Project buildToEntity(ProjectDto projectDto);

    ProjectDto buildToDto(Project project);
}
