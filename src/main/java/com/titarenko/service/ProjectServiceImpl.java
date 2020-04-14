package com.titarenko.service;

import com.titarenko.dao.ProjectRepository;
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
    private ProjectRepository repository;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public Integer create(Project project) {
        return repository.save(project).getId();
    }

    @Override
    public Project get(String name) {
        return repository.findByName(name);
    }

    @Override
    public Project update(Integer id, Project project) {
        Project proj = repository.findById(id).orElseThrow(NotFoundException::new);
        proj.setName(project.getName());
        proj.setEmployees(project.getEmployees());
        return repository.save(proj);
    }

    @Override
    public Project delete(String name) {
        Project deleted = repository.findByName(name);
        repository.delete(deleted);
        return deleted;
    }

    @Override
    public List<Project> getAll() {
        return repository.findAll();
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
