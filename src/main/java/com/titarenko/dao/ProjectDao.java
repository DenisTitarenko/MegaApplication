package com.titarenko.dao;

import com.titarenko.model.Project;

import java.util.List;

public interface ProjectDao {

    Integer create(Project project);

    Project getByName(String name);

    Project update(Integer id, Project project);

    boolean delete(String name);

    List<Project> getAll();

}
