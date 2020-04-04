package com.titarenko.controller.raw;

import com.titarenko.model.Employee;
import com.titarenko.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/raw/employee")
public class EmployeeControllerRaw {

    private EmployeeService service;

    @Autowired
    public EmployeeControllerRaw(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public List<Employee> list() {
        return service.getAll();
    }

    @PostMapping
    public Integer create(@RequestBody Employee employee) {
        return service.create(employee);
    }

    @GetMapping
    public Employee get(@RequestParam String name) {
        return service.get(name);
    }

    @PutMapping("/{id}")
    public Employee update(@PathVariable Integer id, @RequestBody Employee employee) {
        return service.update(id, employee);
    }

    @DeleteMapping
    public boolean delete(@RequestParam String name) {
        return service.delete(name);
    }

    @GetMapping("/samesalary")
    public List<Employee> getWithSameSalary() {
        return service.getEmployeesWithSameSalary();
    }

    @GetMapping("/grouped")
    public List<Employee> getGroupByPosAndDate() {
        return service.getAllGroupByPositionAndDate();
    }

    @PutMapping("/increase/{id}")
    public boolean increaseSalary(@PathVariable Integer id, @RequestParam Integer plus) {
        return service.increaseSalary(id, plus);
    }
}
