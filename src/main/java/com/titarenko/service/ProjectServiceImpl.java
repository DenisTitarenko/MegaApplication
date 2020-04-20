package com.titarenko.service;

import com.titarenko.dao.ProjectRepository;
import com.titarenko.model.Project;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository repository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository repository) {
        this.repository = repository;
    }

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
        Project proj = repository.findById(id).orElseThrow();
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

}
