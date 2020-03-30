package com.titarenko.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.titarenko.model.Employee;
import com.titarenko.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "firstOwnServlet", urlPatterns = "/employee")
public class Servlet extends HttpServlet {

    @Autowired
    private EmployeeServiceImpl service;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Employee employee = new ObjectMapper().readValue(req.getInputStream(),Employee.class);
        resp.getWriter().print(service.create(employee));

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().print(service.getAll().toString());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Employee employee = new ObjectMapper().readValue(req.getInputStream(),Employee.class);
        resp.getWriter().print(service.update(id, employee));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().print(service.delete(req.getParameter("name").replaceAll("_", " ")));
    }
}
