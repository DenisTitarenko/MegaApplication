package com.titarenko.service;

import com.titarenko.model.Department;

import java.util.List;

public interface DepartmentService {

    Integer create(Department department);

    Department get(String name);

    Department update(Integer id, Department department);

    boolean delete(String name);

    List<Department> getAll();

}
