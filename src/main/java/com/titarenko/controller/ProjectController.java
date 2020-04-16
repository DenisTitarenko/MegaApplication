package com.titarenko.controller;

import com.titarenko.model.Project;
import com.titarenko.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/project")
public class ProjectController {

    private ProjectService service;

    @Autowired
    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("projects", service.getAll());
        return "ProjectList";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("project", new Project());
        return "ProjectForm";
    }

    @GetMapping("/employees")
    public String getEmployees(HttpServletRequest request, Model model) {
        model.addAttribute("employees", service.get(request.getParameter("name")).getEmployees());
        model.addAttribute("projectName", service.get(request.getParameter("name")).getName());
        return "ProjectEmployeeList";
    }

    @GetMapping("/update")
    public String update(HttpServletRequest request, Model model) {
        model.addAttribute("project", service.get(request.getParameter("name")));
        return "ProjectForm";
    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest request) {
        service.delete(request.getParameter("name"));
        return "redirect:/project/";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Project project) {
        if (project.getId() == 0) {
            service.create(project);
        } else {
            service.update(project.getId(), project);
        }
        return "redirect:/project/";
    }
}
