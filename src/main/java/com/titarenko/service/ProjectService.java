package com.titarenko.service;

import com.titarenko.model.Project;

import java.util.List;

public interface ProjectService {

    Integer create(Project project);

    Project get(String name);

    Project update(Integer id, Project project);

    Project delete(String name);

    List<Project> getAll();
}
