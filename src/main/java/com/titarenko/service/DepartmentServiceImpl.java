package com.titarenko.service;

import com.titarenko.dao.DepartmentRepository;
import com.titarenko.model.Department;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository repository;

    @Override
    public Integer create(Department department) {
        return repository.save(department).getId();
    }

    @Override
    public Department get(String name) {
        return repository.findByName(name);
    }

    @Override
    public Department update(Integer id, Department department) {
        Department dep = repository.findById(id).orElseThrow();
        dep.setName(department.getName());
        dep.setEmployees(department.getEmployees());
        return repository.save(dep);
    }

    @Override
    public Department delete(String name) {
        Department deleted = repository.findByName(name);
        repository.delete(deleted);
        return deleted;
    }

    @Override
    public List<Department> getAll() {
        return repository.findAll();
    }
}
