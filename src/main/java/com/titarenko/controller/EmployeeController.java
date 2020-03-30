package com.titarenko.controller;

import com.titarenko.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/all")
    public String getAll() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("employee");
//        return modelAndView;
        return employeeService.getAll().toString();
    }
}
