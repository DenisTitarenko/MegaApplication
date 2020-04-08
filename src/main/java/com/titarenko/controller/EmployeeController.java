package com.titarenko.controller;

import com.titarenko.dao.EmployeeDao;
import com.titarenko.dto.EmployeeDto;
import com.titarenko.model.Department;
import com.titarenko.model.Employee;
import com.titarenko.service.DepartmentService;
import com.titarenko.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private EmployeeService employeeService;
    private EmployeeDao employeeDao;
    private DepartmentService departmentService;

    @Autowired
    public EmployeeController(EmployeeService employeeService,
                              DepartmentService departmentService,
                              @Qualifier("hibernateEmployeeDaoImpl") EmployeeDao employeeDao) {
        this.employeeService = employeeService;
        this.employeeDao = employeeDao;
        this.departmentService = departmentService;
    }

    @GetMapping
    public String getAllEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAll());
        return "EmployeeList";
    }

    @GetMapping("/create")
    public ModelAndView create(ModelAndView model) {
        EmployeeDto employeeDto = new EmployeeDto();
        model.addObject("employee", employeeDto);
        List<String> list = departmentService.getAll().stream().map(Department::getName).collect(Collectors.toList());
        model.addObject("departments", list);
        model.setViewName("EmployeeForm");
        return model;
    }

    @GetMapping("/update")
    public ModelAndView update(HttpServletRequest request) {
        EmployeeDto employeeDto = employeeService.buildToDto(employeeDao.get(Integer.parseInt(request.getParameter("id"))));
        ModelAndView model = new ModelAndView("EmployeeForm");
        List<String> list = departmentService.getAll().stream().map(Department::getName).collect(Collectors.toList());
        model.addObject("departments", list);
        model.addObject("employee", employeeDto);
        return model;
    }

    @GetMapping("/delete")
    public ModelAndView delete(HttpServletRequest request) {
        employeeService.delete(Integer.valueOf(request.getParameter("id")));
        return new ModelAndView("redirect:/employee/");
    }

    @RequestMapping("/samesalary")
    public String getWithSameSalary(Model model) {
        model.addAttribute("employees", employeeService.getEmployeesWithSameSalary());
        return "EmployeeList";
    }

    @RequestMapping("/grouped")
    public String getGroupByPosAndDate(Model model) {
        model.addAttribute("employees", employeeService.getAllGroupByPositionAndDate());
        return "EmployeeList";
    }

    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute EmployeeDto employeeDto) {
        Employee employee = employeeService.buildToEntity(employeeDto);
        if (employeeDto.getId() == 0) {
            employeeService.create(employee);
        } else {
            employeeService.update(employeeDto.getId(), employee);
        }
        return new ModelAndView("redirect:/employee/");
    }
}
