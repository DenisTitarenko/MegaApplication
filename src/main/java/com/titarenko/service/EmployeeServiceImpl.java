package com.titarenko.service;

import com.titarenko.dao.DepartmentRepository;
import com.titarenko.dao.EmployeeRepository;
import com.titarenko.dao.ProjectRepository;
import com.titarenko.dto.EmployeeDto;
import com.titarenko.model.Employee;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOGGER = Logger.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private ProjectRepository projectRepository;

    private EmployeeValidator validator = new EmployeeValidator();

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
    public Employee get(String name) {
        if (!validator.isValidName(name)) {
            return null;
        }
        return employeeRepository.findByName(name);
    }

    @Override
    public Employee get(Integer id) {
        if (!getListOfId().contains(id)) {
            return null;
        }
        return employeeRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Employee update(Integer id, Employee employee) {
        if (getListOfId().contains(id)) {
            if (validator.isValidEmployee(employee)) {
                LOGGER.info("Employee's info updated");
                Employee empl = employeeRepository.findById(id).orElseThrow(NotFoundException::new);
                empl.setName(employee.getName());
                empl.setDepartment(employee.getDepartment());
                empl.setSex(employee.getSex());
                empl.setSalary(employee.getSalary());
                empl.setPosition(employee.getPosition());
                empl.setDateOfHire(employee.getDateOfHire());
                empl.setProjects(employee.getProjects());
                return employeeRepository.save(empl);
            } else {
                LOGGER.error("Input data wasn't correct");
                return null;
            }
        } else {
            LOGGER.error("Employee with such id doesn't exist");
            return null;
        }
    }

    @Override
    public Employee delete(String name) {
        if (!validator.isValidName(name)) {
            LOGGER.error("Employee with such name doesn't exist");
        }
        Employee deleted = employeeRepository.findByName(name);
        employeeRepository.delete(deleted);
        LOGGER.info("Employee " + name + " deleted");
        return deleted;
    }

    @Override
    public Employee delete(Integer id) {
        if (!getListOfId().contains(id)) {
            LOGGER.error("Employee with such id doesn't exist");
        }
        Employee deleted = employeeRepository.findById(id).orElseThrow(NotFoundException::new);
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
        Comparator<Employee> comparePositionAndDate = Comparator.comparing(Employee::getPosition)
                .thenComparing(Employee::getDateOfHire, Collections.reverseOrder());
        List<Employee> list = getAll();
        list.sort(comparePositionAndDate);
        return list;
    }

    @Override
    public List<Employee> getEmployeesWithSameSalary() {
        return null;
    }

    @Override
    public Integer increaseSalary(int id, int plusSalary) {
        Employee empl = employeeRepository.findById(id).orElseThrow(NotFoundException::new);
        if (getListOfId().contains(id)) {
            empl.setSalary(empl.getSalary() + plusSalary);
            employeeRepository.save(empl);
        } else {
            LOGGER.error("Employee with such id doesn't exist");
        }
        LOGGER.info("Salary of employee with ID=" + id + " was increased");
        return empl.getSalary();
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

    private List<Integer> getListOfId() {
        return getAll().stream()
                .map(Employee::getId)
                .collect(Collectors.toList());
    }
}
