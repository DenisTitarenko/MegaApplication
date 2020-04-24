package com.titarenko.service;

import com.titarenko.dao.ProjectRepository;
import com.titarenko.exception.ResourceNotFoundException;
import com.titarenko.model.Project;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@NoArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository repository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository repository) {
        this.repository = repository;
    }

    static ResourceNotFoundException logProjectNotFound(Integer id) {
        log.error("Project with id = {} NOT_FOUND", id);
        return new ResourceNotFoundException("Project with id = " + id + " not found");
    }

    @Override
    public Integer create(Project project) {
        return repository.save(project).getId();
    }

    @Override
    public Project get(Integer id) {
        return repository.findById(id).orElseThrow(() -> logProjectNotFound(id));
    }

    @Override
    public Project update(Integer id, Project project) {
        Project proj = repository.findById(id).orElseThrow(() -> logProjectNotFound(id));
        proj.setName(project.getName());
        return repository.save(proj);
    }

    @Override
    public Project delete(Integer id) {
        Project deleted = repository.findById(id).orElseThrow(() -> logProjectNotFound(id));
        repository.delete(deleted);
        return deleted;
    }

    @Override
    public List<Project> getAll() {
        return repository.findAll();
    }
}
