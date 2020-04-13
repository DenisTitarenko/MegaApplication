package com.titarenko.service;

import com.titarenko.dao.DepartmentDao;
import com.titarenko.model.Department;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDao dao;

    @Override
    public Integer create(Department department) {
        return dao.create(department);
    }

    @Override
    public Department get(String name) {
        return dao.getByName(name);
    }

    @Override
    public Department update(Integer id, Department department) {
        return dao.update(id, department);
    }

    @Override
    public boolean delete(String name) {
        return dao.delete(name);
    }

    @Override
    public List<Department> getAll() {
        return dao.getAll();
    }
}
