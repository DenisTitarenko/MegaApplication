package com.titarenko.service;

import com.titarenko.model.Project;

import java.util.List;

public interface ProjectService {

    Integer create(Project project);

    Project get(Integer id);

    Project update(Integer id, Project project);

    Project delete(Integer id);

    List<Project> getAll();
}
