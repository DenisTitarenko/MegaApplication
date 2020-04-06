package com.titarenko.controller;

import com.titarenko.dao.EmployeeDao;
import com.titarenko.dto.EmployeeDto;
import com.titarenko.model.Employee;
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

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private EmployeeService service;
    private EmployeeDao employeeDao;

    @Autowired
    public EmployeeController(EmployeeService service,
                              @Qualifier("hibernateEmployeeDaoImpl") EmployeeDao employeeDao) {
        this.service = service;
        this.employeeDao = employeeDao;
    }

    @GetMapping
    public String getAllEmployees(Model model) {
        model.addAttribute("employees", service.getAll());
        return "EmployeeList";
    }

    @GetMapping("/create")
    public ModelAndView create(ModelAndView model) {
        EmployeeDto employeeDto = new EmployeeDto();
        model.addObject("employee", employeeDto);
        model.setViewName("EmployeeForm");
        return model;
    }

    @GetMapping("/update")
    public ModelAndView update(HttpServletRequest request) {
        EmployeeDto employeeDto = service.buildToDto(employeeDao.get(Integer.parseInt(request.getParameter("id"))));
        ModelAndView model = new ModelAndView("EmployeeForm");
        model.addObject("employee", employeeDto);
        return model;
    }

    @GetMapping("/delete")
    public ModelAndView delete(HttpServletRequest request) {
        service.delete(request.getParameter("name"));
        return new ModelAndView("redirect:/employee/");
    }

    @RequestMapping("/samesalary")
    public String getWithSameSalary(Model model) {
        model.addAttribute("employees", service.getEmployeesWithSameSalary());
        return "EmployeeList";
    }

    @RequestMapping("/grouped")
    public String getGroupByPosAndDate(Model model) {
        model.addAttribute("employees", service.getAllGroupByPositionAndDate());
        return "EmployeeList";
    }

    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute EmployeeDto employeeDto) {
        Employee employee = service.buildToEntity(employeeDto);
        if (employeeDto.getId() == 0) {
            service.create(employee);
        } else {
            service.update(employeeDto.getId(), employee);
        }
        return new ModelAndView("redirect:/employee/");
    }
}
