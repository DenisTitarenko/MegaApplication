package com.titarenko.service;

import com.titarenko.model.Department;

import java.util.List;

public interface DepartmentService {

    Integer create(Department department);

    Department get(Integer id);

    Department update(Integer id, Department department);

    Department delete(Integer id);

    List<Department> getAll();

}
