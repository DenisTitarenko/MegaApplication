package com.titarenko.controller;

import com.titarenko.dto.EmployeeDto;
import com.titarenko.model.Department;
import com.titarenko.model.Project;
import com.titarenko.service.DepartmentService;
import com.titarenko.service.EmployeeService;
import com.titarenko.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value={"/employee", "/"})
public class EmployeeController {

    private EmployeeService employeeService;
    private DepartmentService departmentService;
    private ProjectService projectService;

    @Autowired
    public EmployeeController(EmployeeService employeeService,
                              DepartmentService departmentService,
                              ProjectService projectService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.projectService = projectService;
    }

    @GetMapping
    public String getAllEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAll());
        return "EmployeeList";
    }

    @GetMapping("/projects")
    public String getEmployees(HttpServletRequest request, Model model) {
        model.addAttribute("projects", employeeService.get(request.getParameter("name")).getProjects());
        model.addAttribute("employeeName", employeeService.get(request.getParameter("name")).getName());
        return "EmployeeProjectList";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("employee", new EmployeeDto());
        model.addAttribute("departments", departmentService.getAll().stream().map(Department::getName).collect(Collectors.toList()));
        model.addAttribute("projects", projectService.getAll().stream().map(Project::getName).collect(Collectors.toList()));
        return "EmployeeForm";
    }

    @GetMapping("/update")
    public String update(HttpServletRequest request, Model model) {
        model.addAttribute("employee", employeeService.buildToDto(employeeService.get(Integer.parseInt(request.getParameter("id")))));
        model.addAttribute("departments", departmentService.getAll().stream().map(Department::getName).collect(Collectors.toList()));
        model.addAttribute("projects", projectService.getAll().stream().map(Project::getName).collect(Collectors.toList()));
        return "EmployeeForm";
    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest request) {
        employeeService.delete(Integer.valueOf(request.getParameter("id")));
        return "redirect:/employee/";
    }

    @GetMapping("/samesalary")
    public String getWithSameSalary(Model model) {
        model.addAttribute("employees", employeeService.getEmployeesWithSameSalary());
        return "EmployeeList";
    }

    @GetMapping("/grouped")
    public String getGroupByPosAndDate(Model model) {
        model.addAttribute("employees", employeeService.getAllGroupByPositionAndDate());
        return "EmployeeList";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute EmployeeDto employeeDto) {
        if (employeeDto.getId() == 0) {
            employeeService.create(employeeService.buildToEntity(employeeDto));
        } else {
            employeeService.update(employeeDto.getId(), employeeService.buildToEntity(employeeDto));
        }
        return "redirect:/";
    }
}
