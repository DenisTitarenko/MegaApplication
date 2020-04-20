package com.titarenko.dao.handle;

import com.titarenko.model.Department;

import java.util.List;
@Deprecated
public interface DepartmentDao {

    Integer create(Department department);

    Department getByName(String name);

    Department update(Integer id, Department department);

    boolean delete(String name);

    List<Department> getAll();

}
