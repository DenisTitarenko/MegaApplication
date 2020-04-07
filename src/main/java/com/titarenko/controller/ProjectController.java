package com.titarenko.controller;

import com.titarenko.dao.ProjectDao;
import com.titarenko.model.Department;
import com.titarenko.model.Employee;
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
    private ProjectDao projectDao;

    @Autowired
    public ProjectController(ProjectService service, ProjectDao projectDao) {
        this.service = service;
        this.projectDao = projectDao;
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

    @GetMapping("/{name}")
    public ModelAndView getEmployees(@PathVariable String name) {
        Project project = projectDao.getByName(name);
        ModelAndView model = new ModelAndView("ProjectEmployeeList");
        model.addObject("employees", project.getEmployees());
        return model;
    }

    @GetMapping("/{name}/create")
    public ModelAndView create(@PathVariable String name, ModelAndView model) {
        Project project = projectDao.getByName(name);
        model.addObject("project", project);
        model.setViewName("ProjectForm");
        return model;
    }

    @PostMapping("/{name}/save")
    public ModelAndView save(@PathVariable String name, @ModelAttribute Employee employee) {
//        service.get(name).getEmployees().add()
//        if (project.getId() == 0) {
//            service.create(project);
//        } else {
//            service.update(project.getId(), project);
//        }
        return new ModelAndView("redirect:/project/");
    }


    @GetMapping("/update")
    public ModelAndView update(HttpServletRequest request) {
        Project project = projectDao.getByName(request.getParameter("name"));
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
