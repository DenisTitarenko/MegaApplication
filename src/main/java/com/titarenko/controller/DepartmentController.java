package com.titarenko.controller;

import com.titarenko.model.Department;
import com.titarenko.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping ("/department")
public class DepartmentController {

    private DepartmentService service;

    @Autowired
    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("departments", service.getAll());
        return "DepartmentList";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("department", new Department());
        return "DepartmentForm";
    }

    @GetMapping("/update")
    public String update(HttpServletRequest request, Model model) {
        model.addAttribute("department", service.get(Integer.valueOf(request.getParameter("id"))));
        return "DepartmentForm";
    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest request) {
        service.delete(Integer.valueOf(request.getParameter("id")));
        return "redirect:/department/";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Department department) {
        if (department.getId() == 0) {
            service.create(department);
        } else {
            service.update(department.getId(), department);
        }
        return "redirect:/department/";
    }
}
