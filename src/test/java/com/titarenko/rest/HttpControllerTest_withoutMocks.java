package com.titarenko.rest;

import com.titarenko.UnitTestParent;
import com.titarenko.model.Employee;
import com.titarenko.service.EmployeeServiceImpl;
import com.titarenko.service.JsonParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Service
public class HttpControllerTest_withoutMocks extends UnitTestParent {

    private final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    public final JsonParser JSON_PARSER = new JsonParser();
    @Autowired
    private HttpController controller;

    private String incomingJson =
            "{\n" +
            "\"id\" : 10,\n" +
            "\"name\" : \"Tom Cruz\",\n" +
            "\"sex\" : \"M\",\n" +
            "\"position\" : \"actor\",\n" +
            "\"salary\" : 350,\n" +
            "\"dateOfHire\" : \"2010-10-10\"\n" +
            "}";

    @BeforeEach
    public void startHttpController() {
        Collections.addAll(database, vasil, petr, stepa);
    }

    @AfterEach
    public void closeHttpController() {
        controller.close();
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
        assertEquals("10", response.body());
        assertTrue(database.contains(JSON_PARSER.deserialize(incomingJson)));
    }

    @Test
    public void testHttpGet() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:1408/employee/get?name=Petr_Ovich"))
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
                .uri(URI.create("http://localhost:1408/employee/delete?name=Petr_Ovich"))
                .build();
        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals("true", response.body());
        assertEquals(2, database.size());
        assertFalse(database.contains(petr));
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

    @Test
    public void testHttpIncreaseSalary() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(incomingJson))
                .uri(URI.create("http://localhost:1408/employee/increase/1?salary=849"))
                .build();
        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals("true", response.body());
        assertEquals(150 + 849, vasil.getSalary());
    }

    @Test
    public void testHttpGetAllGroupByPositionAndDate() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:1408/employee/show_grouped"))
                .build();

        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        List<Employee> employeeList = JSON_PARSER.deserializeList(response.body());
        assertEquals(200, response.statusCode());
        assertEquals(Arrays.asList(stepa, vasil, petr), employeeList);
    }
}
