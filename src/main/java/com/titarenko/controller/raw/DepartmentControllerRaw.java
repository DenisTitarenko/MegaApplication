package com.titarenko.controller.raw;

import com.titarenko.model.Department;
import com.titarenko.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/raw/department")
public class DepartmentControllerRaw {

    private DepartmentService service;

    @Autowired
    public DepartmentControllerRaw(DepartmentService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public List<Department> getAllDepartments() {
        return service.getAll();
    }

    @PostMapping
    public Integer create(@RequestBody Department department) {
        return service.create(department);
    }

    @GetMapping
    public Department get(@RequestParam String name) {
        return service.get(name);
    }

    @PutMapping("/{id}")
    public Department update(@PathVariable Integer id, @RequestBody Department department) {
        return service.update(id, department);
    }

    @DeleteMapping
    public boolean delete(@RequestParam String name) {
        return service.delete(name);
    }

}
