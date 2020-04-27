package com.titarenko.service;

import com.titarenko.dao.DepartmentRepository;
import com.titarenko.exception.ResourceNotFoundException;
import com.titarenko.model.Department;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@NoArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository repository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository repository) {
        this.repository = repository;
    }

    static ResourceNotFoundException logDepartmentNotFound(Integer id) {
        log.error("Department with id = {} NOT_FOUND", id);
        return new ResourceNotFoundException("Department with id = " + id + " not found");
    }

    @Override
    public Integer create(Department department) {
        return repository.save(department).getId();
    }

    @Override
    public Department get(Integer id) {
        return repository.findById(id).orElseThrow(() -> logDepartmentNotFound(id));
    }

    @Override
    public Department update(Integer id, Department department) {
        Department dep = repository.findById(id).orElseThrow(() -> logDepartmentNotFound(id));
        dep.setName(department.getName());
        return repository.save(dep);
    }

    @Override
    public Department delete(Integer id) {
        Department deleted = repository.findById(id).orElseThrow(() -> logDepartmentNotFound(id));
        repository.delete(deleted);
        return deleted;
    }

    @Override
    public List<Department> getAll() {
        return repository.findAll();
    }
}
