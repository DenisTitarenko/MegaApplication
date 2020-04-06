package com.titarenko.service;

import com.titarenko.dao.DepartmentDao;
import com.titarenko.dao.EmployeeDao;
import com.titarenko.dto.EmployeeDto;
import com.titarenko.io.Writer;
import com.titarenko.model.Employee;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOGGER = Logger.getLogger(EmployeeServiceImpl.class);
    private Writer writer;
    private EmployeeDao employeeDao;
    @Autowired
    private DepartmentDao departmentDao;
    private EmployeeValidator validator = new EmployeeValidator();

    @Autowired
    public EmployeeServiceImpl(@Qualifier("hibernateEmployeeDaoImpl") EmployeeDao employeeDao,
                               Writer writer) {
        this.employeeDao = employeeDao;
        this.writer = writer;
    }

    @Override
    public Integer create(Employee employee) {
        int result;
        if (validator.isValidEmployee(employee)) {
            result = employeeDao.create(employee);
        } else {
            writer.writeToOutputStream("Seems like input data wasn't correct");
            LOGGER.error("Employee wasn't added. Input data wasn't correct");
            return null;
        }
        LOGGER.info("Employee " + employee.getName() + " added");
        return result;
    }

    @Override
    public Employee get(String name) {
        if (!validator.isValidName(name)) {
            writer.writeToOutputStream("Oops.. Seems like input name wasn't correct");
            return null;
        }
        return employeeDao.get(name);
    }

    @Override
    public Employee update(Integer id, Employee employee) {
        if (getListOfId().contains(id)) {
            if (validator.isValidEmployee(employee)) {
                LOGGER.info("Employee's info updated");
                return employeeDao.update(id, employee);
            } else {
                writer.writeToOutputStream("Oops.. Seems like input data wasn't correct");
                LOGGER.error("Input data wasn't correct");
                return null;
            }
        } else {
            writer.writeToOutputStream("Employee with such id doesn't exist");
            LOGGER.error("Employee with such id doesn't exist");
            return null;
        }
    }

    @Override
    public boolean delete(String name) {
        if (!validator.isValidName(name)) {
            writer.writeToOutputStream("Oops.. Seems like input name wasn't correct");
            LOGGER.error("Employee with such name doesn't exist");
            return false;
        }
        LOGGER.info("Employee " + name + " deleted");
        return employeeDao.delete(name);
    }

    @Override
    public List<Employee> getAll() {
        return employeeDao.getAll();
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
        return employeeDao.getEmployeesWithSameSalary();
    }

    @Override
    public boolean increaseSalary(int id, int plusSalary) {
        if (getListOfId().contains(id)) {
            Employee newEmpl = employeeDao.get(id);
            newEmpl.setSalary(newEmpl.getSalary() + plusSalary);
            employeeDao.update(id, newEmpl);
        } else {
            writer.writeToOutputStream("Employee with such id doesn't exist");
            LOGGER.error("Employee with such id doesn't exist");
            return false;
        }
        LOGGER.info("Salary of employee with ID=" + id + " was increased");
        return true;
    }

    @Override
    public Employee buildToEntity(EmployeeDto employeeDto) {
        return Employee.builder()
                .name(employeeDto.getName())
                .sex(employeeDto.getSex())
                .department(departmentDao.getByName(employeeDto.getDepartmentName()))
                .position(employeeDto.getPosition())
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
