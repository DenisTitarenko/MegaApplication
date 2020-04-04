package com.titarenko.controller;

import com.titarenko.dao.EmployeeDao;
import com.titarenko.model.Employee;
import com.titarenko.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private EmployeeService service;
    private EmployeeDao dao;

    @Autowired
    public EmployeeController(EmployeeService service,
                              @Qualifier("hibernateEmployeeDaoImpl") EmployeeDao dao) {
        this.service = service;
        this.dao = dao;
    }

    @GetMapping
    public String getAllEmployees(Model model) {
        model.addAttribute("employees", service.getAll());
        return "Home";
    }

    @GetMapping("/create")
    public ModelAndView create(ModelAndView model) {
        Employee employee = new Employee();
        model.addObject("employee", employee);
        model.setViewName("EmployeeForm");
        return model;
    }

    @GetMapping("/update")
    public ModelAndView update(HttpServletRequest request) {
        Employee employee = dao.get(Integer.parseInt(request.getParameter("id")));
        ModelAndView model = new ModelAndView("EmployeeForm");
        model.addObject("employee", employee);
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
        return "Home";
    }

    @RequestMapping("/grouped")
    public String getGroupByPosAndDate(Model model) {
        model.addAttribute("employees", service.getAllGroupByPositionAndDate());
        return "Home";
    }

    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute Employee employee) {
        if (employee.getId() == 0) {
            service.create(employee);
        } else {
            service.update(employee.getId(), employee);
        }
        return new ModelAndView("redirect:/employee/");
    }
}
