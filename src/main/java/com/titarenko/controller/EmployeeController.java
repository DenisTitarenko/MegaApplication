package com.titarenko.controller;

import com.titarenko.dao.EmployeeDao;
import com.titarenko.model.Employee;
import com.titarenko.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getAllEmployees(Model model) {
        model.addAttribute("employees", service.getAll());
        return "Home";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create(ModelAndView model) {
        Employee employee = new Employee();
        model.addObject("employee", employee);
        model.setViewName("EmployeeForm");
        return model;
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public ModelAndView update(HttpServletRequest request) {
        Employee employee = employeeDao.get(Integer.parseInt(request.getParameter("id")));
        ModelAndView model = new ModelAndView("EmployeeForm");
        model.addObject("employee", employee);
        return model;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
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

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute Employee employee) {
        if (employee.getId() == 0) {
            service.create(employee);
        } else {
            service.update(employee.getId(), employee);
        }
        return new ModelAndView("redirect:/employee/");
    }


//    @GetMapping("/list")
//    public List<Employee> list() {
//        return service.getAll();
//    }
//
//    @PostMapping
//    public Integer create(@RequestBody Employee employee) {
//        return service.create(employee);
//    }
//
//    @GetMapping
//    public Employee get(@RequestParam String name) {
//        return service.get(name);
//    }
//
//    @PutMapping("/{id}")
//    public Employee update(@PathVariable Integer id, @RequestBody Employee employee) {
//        return service.update(id, employee);
//    }
//
//    @DeleteMapping
//    public boolean delete(@RequestParam String name) {
//        return service.delete(name);
//    }
//
//    @GetMapping("/samesalary")
//    public List<Employee> getWithSameSalary() {
//        return service.getEmployeesWithSameSalary();
//    }
//
//    @GetMapping("/grouped")
//    public List<Employee> getGroupByPosAndDate() {
//        return service.getAllGroupByPositionAndDate();
//    }
//
//    @PutMapping("/increase/{id}")
//    public boolean increaseSalary(@PathVariable Integer id, @RequestParam Integer plus) {
//        return service.increaseSalary(id, plus);
//    }
}
