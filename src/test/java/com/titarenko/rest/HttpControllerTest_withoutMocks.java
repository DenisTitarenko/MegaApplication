package com.titarenko.rest;

import com.titarenko.dao.EmployeeDao;
import com.titarenko.model.Employee;
import com.titarenko.model.Gender;
import com.titarenko.service.EmployeeServiceImpl;
import com.titarenko.service.JsonParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class HttpControllerTest_withoutMocks {

    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    public static final JsonParser JSON_PARSER = new JsonParser();
    private static List<Employee> database;
    private static Employee vasil = new Employee.Builder()
            .withId(1)
            .withName("Vasil")
            .withSex(Gender.M)
            .withPosition("position")
            .withSalary(150)
            .withDateOfHire(LocalDate.parse("2012-12-12"))
            .build();
    private static Employee petr = new Employee.Builder()
            .withId(2)
            .withName("Petr")
            .withSex(Gender.M)
            .withPosition("position2")
            .withSalary(150)
            .withDateOfHire(LocalDate.parse("2011-11-11"))
            .build();
    private static Employee stepa = new Employee.Builder()
            .withId(3)
            .withName("Stepa")
            .withSex(Gender.M)
            .withPosition("position3")
            .withSalary(200)
            .withDateOfHire(LocalDate.parse("2010-10-10"))
            .build();

    private String incomingJson = """
                {
                  "id" : 10,
                  "name" : "Tom Cruz",
                  "sex" : "M",
                  "position" : "actor",
                  "salary" : 350,
                  "dateOfHire" : "2010-10-10"
                }
                """;

    @BeforeAll
    static void startHttpController() {
        database = new ArrayList<>();
        Collections.addAll(database, vasil, petr, stepa);
        new Controller(new EmployeeServiceImpl(new EmployeeDao() {
            @Override
            public Integer create(Employee employee) {
                database.add(employee);
                return database.get(database.size() - 1).getId();
            }

            @Override
            public Employee get(String name) {
                Employee returned = null;
                for (Employee empl : database) {
                    if (name.equals(empl.getName())) {
                        returned = empl;
                    }
                }
                return returned;
            }

            @Override
            public Employee get(int id) {
                Employee returned = null;
                for (Employee empl : database) {
                    if (id == empl.getId()) {
                        returned = empl;
                    }
                }
                return returned;
            }

            @Override
            public Employee update(Integer id, Employee employee) {
                employee.setId(id);
                int index = database.indexOf(get(id));
                database.set(index, employee);
                return database.get(index);
            }

            @Override
            public boolean delete(String name) {
                int initSize = database.size();
                database.remove(get(name));
                return initSize == database.size() + 1;
            }

            @Override
            public List<Employee> getAll() {
                return database;
            }

            @Override
            public List<Employee> getEmployeesWithSameSalary() {
                Map<Integer, List<Employee>> map = new HashMap<>();
                for (Employee empl : database) {
                    if (map.containsKey(empl.getSalary())) {
                        List<Employee> list = map.get(empl.getSalary());
                        list.add(empl);
                        map.put(empl.getSalary(), list);
                    } else {
                        List<Employee> list = new ArrayList<>();
                        list.add(empl);
                        map.put(empl.getSalary(), list);
                    }
                }
                List<Employee> returned = new ArrayList<>();
                for (int key : map.keySet()) {
                    if (map.get(key).size() > 1) {
                        returned.addAll(map.get(key));
                    }
                }
                return returned;
            }
        }));
    }

    @Test
    public void testHttpCreate() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(incomingJson))
                .uri(URI.create("http://localhost:1408/employee/add"))
                .build();

        int initSize = database.size();
        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals(initSize + 1, database.size());
        assertEquals(response.body(), String.valueOf(database.get(database.size() - 1).getId()));
    }

    @Test
    public void testHttpGet() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:1408/employee/get?name=Petr"))
                .build();
        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        Employee employee = JSON_PARSER.deserialize(response.body());
        assertEquals(petr, employee);
    }

    @Test
    public void testHttpUpdate() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(incomingJson))
                .uri(URI.create("http://localhost:1408/employee/update?id=3"))
                .build();

        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        Employee empl = JSON_PARSER.deserialize(response.body());
        assertEquals(200, response.statusCode());
        assertEquals(empl.getName(), database.get(database.indexOf(empl)).getName());
        assertTrue(database.contains(empl));
    }

    @Test
    public void testHttpDelete() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create("http://localhost:1408/employee/delete?name=Tom_Cruz"))
                .build();
        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals("true", response.body());
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
        assertEquals(database.size(), employeeList.size());
        assertEquals(vasil, employeeList.get(0));
        assertEquals(petr, employeeList.get(1));
        assertEquals(stepa, employeeList.get(2));
    }

    @Test
    public void testHttpGetEmployeesWithSameSalary() throws IOException, InterruptedException {
        List<Employee> withSameSalary = Arrays.asList(vasil, petr);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:1408/employee/show_same"))
                .build();
        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        List<Employee> employeeList = JSON_PARSER.deserializeList(response.body());
        assertEquals(withSameSalary, employeeList);
    }
}
