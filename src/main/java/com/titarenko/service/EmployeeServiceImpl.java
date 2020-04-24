package com.titarenko.service;

import com.titarenko.dao.DepartmentRepository;
import com.titarenko.dao.EmployeeRepository;
import com.titarenko.dao.ProjectRepository;
import com.titarenko.dto.EmployeeDto;
import com.titarenko.exception.ResourceNotFoundException;
import com.titarenko.model.Employee;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOGGER = Logger.getLogger(EmployeeServiceImpl.class);
    private EmployeeRepository employeeRepository;
    private DepartmentRepository departmentRepository;
    private ProjectRepository projectRepository;
    private EmployeeValidator validator;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository,
                               ProjectRepository projectRepository, EmployeeValidator validator) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.projectRepository = projectRepository;
        this.validator = validator;
    }

    static ResourceNotFoundException logEmployeeNotFound(Integer id) {
        log.error("Employee with id = {} NOT_FOUND", id);
        return new ResourceNotFoundException("Employee with id = " + id + " not found");
    }

    @Override
    public Integer create(Employee employee) {
        int result;
        if (validator.isValidEmployee(employee)) {
            result = employeeRepository.save(employee).getId();
        } else {
            LOGGER.error("Employee wasn't added. Input data wasn't correct");
            return null;
        }
        LOGGER.info("Employee " + employee.getName() + " added");
        return result;
    }

    @Override
    public Employee get(Integer id) {
        return employeeRepository.findById(id).orElseThrow(() -> logEmployeeNotFound(id));
    }

    @Override
    public Employee update(Integer id, Employee employee) {
        if (validator.isValidEmployee(employee)) {
            Employee empl = employeeRepository.findById(id).orElseThrow(() -> logEmployeeNotFound(id));
            empl.setName(employee.getName());
            empl.setDepartment(employee.getDepartment());
            empl.setSex(employee.getSex());
            empl.setSalary(employee.getSalary());
            empl.setPosition(employee.getPosition());
            empl.setDateOfHire(employee.getDateOfHire());
            empl.setProjects(employee.getProjects());
            LOGGER.info("Employee's info updated");
            return employeeRepository.save(empl);
        } else {
            LOGGER.error("Input data wasn't correct");
            return null;
        }
    }

    @Override
    public Employee delete(Integer id) {
        Employee deleted = employeeRepository.findById(id).orElseThrow(() -> logEmployeeNotFound(id));
        employeeRepository.delete(deleted);
        LOGGER.info("Employee with id " + id + " deleted");
        return deleted;
    }

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> getAllGroupByPositionAndDate() {
        return employeeRepository.findByOrderByPositionAscDateOfHireDesc();
    }

    @Override
    public List<Employee> getEmployeesWithSameSalary() {
        return employeeRepository.getEmployeesWithSameSalary();
    }

    @Override
    public Employee buildToEntity(EmployeeDto employeeDto) {
        return Employee.builder()
                .name(employeeDto.getName())
                .sex(employeeDto.getSex())
                .department(departmentRepository.findByName(employeeDto.getDepartmentName()))
                .position(employeeDto.getPosition())
                .projects(employeeDto.getProjects()
                        .stream()
                        .map(name -> projectRepository.findByName(name))
                        .collect(Collectors.toSet()))
                .salary(employeeDto.getSalary())
                .dateOfHire(employeeDto.getDateOfHire())
                .build();
    }

    @Override
    public EmployeeDto buildToDto(Employee employee) {
        EmployeeDto.EmployeeDtoBuilder dto = EmployeeDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .sex(employee.getSex())
                .position(employee.getPosition())
                .salary(employee.getSalary())
                .dateOfHire(employee.getDateOfHire());
        if (employee.getDepartment() != null) {
            dto.departmentName(employee.getDepartment().getName());
        }
        return dto.build();
    }
}
