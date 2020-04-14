package com.titarenko.controller;

import com.titarenko.model.Project;
import com.titarenko.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView create(ModelAndView model) {
        Project project = new Project();
        model.addObject("project", project);
        model.setViewName("ProjectForm");
        return model;
    }

    @GetMapping("/employees")
    public ModelAndView getEmployees(HttpServletRequest request) {
        Project project = service.get(request.getParameter("name"));
        ModelAndView model = new ModelAndView("ProjectEmployeeList");
        model.addObject("employees", project.getEmployees());
        model.addObject("projectName", project.getName());
        return model;
    }

    @GetMapping("/update")
    public ModelAndView update(HttpServletRequest request) {
        Project project = service.get(request.getParameter("name"));
        ModelAndView model = new ModelAndView("ProjectForm");
        model.addObject("project", project);
        return model;
    }

    @GetMapping("/delete")
    public ModelAndView delete(HttpServletRequest request) {
        service.delete(request.getParameter("name"));
        return new ModelAndView("redirect:/project/");
    }

    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute Project project) {
        if (project.getId() == 0) {
            service.create(project);
        } else {
            service.update(project.getId(), project);
        }
        return new ModelAndView("redirect:/project/");
    }
}
