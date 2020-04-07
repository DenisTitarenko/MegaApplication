package com.titarenko.service;

import com.titarenko.dao.ProjectDao;
import com.titarenko.dto.ProjectDto;
import com.titarenko.model.Employee;
import com.titarenko.model.Project;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao dao;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public Integer create(Project project) {
        return dao.create(project);
    }

    @Override
    public Project get(String name) {
        return dao.getByName(name);
    }

    @Override
    public Project update(Integer id, Project project) {
        return dao.update(id, project);
    }

    @Override
    public boolean delete(String name) {
        return dao.delete(name);
    }

    @Override
    public List<Project> getAll() {
        return dao.getAll();
    }

    @Override
    public Project buildToEntity(ProjectDto projectDto) {
        Set<Employee> set = new HashSet<>();
        for (Integer id : projectDto.getEmployeeId()) {
            set.add(employeeService.getAll()
                    .stream()
                    .filter(employee -> id.equals(employee.getId()))
                    .findFirst()
                    .orElseThrow(NotFoundException::new));
        }
        return Project.builder()
                .name(projectDto.getName())
                .employees(set)
                .build();
    }

    @Override
    public ProjectDto buildToDto(Project project) {
        ProjectDto.ProjectDtoBuilder dto = ProjectDto.builder()
                .id(project.getId())
                .name(project.getName());
        if (project.getEmployees() != null) {
            dto.employeeId(project.getEmployees()
                    .stream()
                    .map(Employee::getId)
                    .collect(Collectors.toSet()));
        }
        return dto.build();
    }
}
