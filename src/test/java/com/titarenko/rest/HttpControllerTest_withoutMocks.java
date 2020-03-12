package com.titarenko.rest;

import com.titarenko.dao.EmployeeDao;
import com.titarenko.model.Employee;
import com.titarenko.service.EmployeeServiceImpl;
import com.titarenko.service.JsonParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpControllerTest_withoutMocks {

    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    public static final JsonParser JSON_PARSER = new JsonParser();
    private static Employee vasil = new Employee.Builder().withName("Vasil").withSalary(150).build();

    @BeforeAll
    static void startHttpController() {
        new Controller(new EmployeeServiceImpl(new EmployeeDao() {
            @Override
            public Integer create(Employee employee) {
                return null;
            }

            @Override
            public Employee get(String name) {
                return null;
            }

            @Override
            public Employee get(int id) {
                return null;
            }

            @Override
            public Employee update(Integer id, Employee employee) {
                return null;
            }

            @Override
            public boolean delete(String name) {
                return false;
            }

            @Override
            public List<Employee> getAll() {
                return Collections.singletonList(vasil);
            }

            @Override
            public List<Employee> getEmployeesWithSameSalary() {
                return null;
            }
        }));
    }

    @Test
    public void testHttpGetAll() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:1408/employee/all"))
                .build();
        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        List<Employee> employeeList = JSON_PARSER.deserializeList(response.body());
        assertEquals(1, employeeList.size());
        assertEquals(vasil, employeeList.get(0));
    }
}
