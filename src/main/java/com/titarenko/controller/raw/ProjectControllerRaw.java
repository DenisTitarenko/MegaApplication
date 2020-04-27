package com.titarenko.controller.raw;

import com.titarenko.model.Department;
import com.titarenko.model.Project;
import com.titarenko.service.DepartmentService;
import com.titarenko.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/raw/project")
public class ProjectControllerRaw {

    private ProjectService service;

    @Autowired
    public ProjectControllerRaw(ProjectService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public List<Project> getAllDepartments() {
        return service.getAll();
    }

    @PostMapping
    public Integer create(@RequestBody Project project) {
        return service.create(project);
    }

    @GetMapping
    public Project get(@RequestParam Integer id) {
        return service.get(id);
    }

    @PutMapping("/{id}")
    public Project update(@PathVariable Integer id, @RequestBody Project project) {
        return service.update(id, project);
    }

    @DeleteMapping
    public Project delete(@RequestParam Integer id) {
        return service.delete(id);
    }

}
