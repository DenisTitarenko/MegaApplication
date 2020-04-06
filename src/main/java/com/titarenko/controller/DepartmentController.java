package com.titarenko.controller;

import com.titarenko.dao.DepartmentDao;
import com.titarenko.model.Department;
import com.titarenko.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping ("/department")
public class DepartmentController {

    private DepartmentService service;
    private DepartmentDao departmentDao;

    @Autowired
    public DepartmentController(DepartmentService service, DepartmentDao departmentDao) {
        this.service = service;
        this.departmentDao = departmentDao;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("departments", service.getAll());
        return "DepartmentList";
    }

    @GetMapping("/create")
    public ModelAndView create(ModelAndView model) {
        Department department = new Department();
        model.addObject("department", department);
        model.setViewName("DepartmentForm");
        return model;
    }

    @GetMapping("/employees")
    public ModelAndView getEmployees(HttpServletRequest request) {
        Department department = departmentDao.getByName(request.getParameter("name"));
        ModelAndView model = new ModelAndView("EmployeeList");
        model.addObject("employees", department.getEmployees());
        return model;
    }

    @GetMapping("/update")
    public ModelAndView update(HttpServletRequest request) {
        Department department = departmentDao.getByName(request.getParameter("name"));
        ModelAndView model = new ModelAndView("DepartmentForm");
        model.addObject("department", department);
        return model;
    }

    @GetMapping("/delete")
    public ModelAndView delete(HttpServletRequest request) {
        service.delete(request.getParameter("name"));
        return new ModelAndView("redirect:/department/");
    }

    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute Department department) {
        System.out.println(department);
        if (department.getId() == 0) {
            service.create(department);
        } else {
            service.update(department.getId(), department);
        }
        return new ModelAndView("redirect:/department/");
    }

}
