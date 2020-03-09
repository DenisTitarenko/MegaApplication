package com.titarenko.rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.titarenko.model.Employee;
import com.titarenko.service.EmployeeService;
import com.titarenko.service.JsonParser;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.*;

public class Controller {

    private EmployeeService service = new EmployeeService();
    private JsonParser parser = new JsonParser();

    public static void main(String[] args) {
        new Controller();
    }

    public Controller() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(1408), 0);
            server.createContext("/employee/add", this::add);
            server.createContext("/employee/get", this::get);
            server.createContext("/employee/update", this::update);
            server.createContext("/employee/delete", this::delete);
            server.createContext("/employee/all", this::getAll);
            server.createContext("/employee/show_grouped", this::getAllGroupByPositionAndDate);
            server.createContext("/employee/show_same", this::getEmployeesWithSameSalary);
            server.createContext("/employee/increase/", this::increaseSalary);
            server.setExecutor(null); // creates a default executor
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void add(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            Scanner scanner = new Scanner(new ByteArrayInputStream(exchange.getRequestBody().readAllBytes()));
            String request = scanner.useDelimiter("\\A").next();
            String resp = String.valueOf(service.create(parser.deserialize(request)));
            exchange.sendResponseHeaders(200, resp.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(resp.getBytes());
            output.flush();
        } else {
            exchange.sendResponseHeaders(405, -1); // 405 Method Not Allowed
        }
        exchange.close();
    }

    private void get(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            Map<String, List<String>> params = splitQuery(exchange.getRequestURI().getRawQuery());
            String name = params.get("name").get(0).replaceAll("_", " ");
            String resp = service.get(name).toString();
            exchange.sendResponseHeaders(200, resp.length());
            OutputStream output = exchange.getResponseBody();
            output.write(resp.getBytes());
            output.flush();
        } else {
            exchange.sendResponseHeaders(405, -1);
        }
        exchange.close();
    }

    private void update(HttpExchange exchange) throws IOException {
        if ("PUT".equals(exchange.getRequestMethod())) {
            Map<String, List<String>> params = splitQuery(exchange.getRequestURI().getRawQuery());
            int id = Integer.parseInt(params.get("id").get(0));

            Scanner scanner = new Scanner(new ByteArrayInputStream(exchange.getRequestBody().readAllBytes()));
            String request = scanner.useDelimiter("\\A").next();
            Employee resp = service.update(id, parser.deserialize(request));

            exchange.sendResponseHeaders(200, resp.toString().length());
            OutputStream output = exchange.getResponseBody();
            output.write(service.get(resp.getName()).toString().getBytes());
            output.flush();
        } else {
            exchange.sendResponseHeaders(405, -1);
        }
        exchange.close();
    }

    private void delete(HttpExchange exchange) throws IOException {
        if ("DELETE".equals(exchange.getRequestMethod())) {
            Map<String, List<String>> params = splitQuery(exchange.getRequestURI().getRawQuery());
            String name = params.get("name").get(0).replaceAll("_", " ");
            String resp = String.valueOf(service.delete(name));
            exchange.sendResponseHeaders(200, resp.length());
            OutputStream output = exchange.getResponseBody();
            output.write(resp.getBytes());
            output.flush();
        } else {
            exchange.sendResponseHeaders(405, -1);
        }
        exchange.close();
    }

    private void getAll(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            String resp = service.getAll().toString();
            exchange.sendResponseHeaders(200, resp.length());
            OutputStream output = exchange.getResponseBody();
            output.write(resp.getBytes());
            output.flush();
        } else {
            exchange.sendResponseHeaders(405, -1);
        }
        exchange.close();
    }

    private void getAllGroupByPositionAndDate(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            String resp = service.getAllGroupByPositionAndDate().toString();
            exchange.sendResponseHeaders(200, resp.length());
            OutputStream output = exchange.getResponseBody();
            output.write(resp.getBytes());
            output.flush();
        } else {
            exchange.sendResponseHeaders(405, -1);
        }
        exchange.close();
    }

    private void getEmployeesWithSameSalary(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            String resp = service.getEmployeesWithSameSalary().toString();
            exchange.sendResponseHeaders(200, resp.length());
            OutputStream output = exchange.getResponseBody();
            output.write(resp.getBytes());
            output.flush();
        } else {
            exchange.sendResponseHeaders(405, -1);
        }
        exchange.close();
    }

    private void increaseSalary(HttpExchange exchange) throws IOException {
        if ("PUT".equals(exchange.getRequestMethod())) {
            List<String> path = Arrays.asList(exchange.getRequestURI().getPath().split("/"));
            int id = Integer.parseInt(path.get(path.size() - 1));
            Map<String, List<String>> params = splitQuery(exchange.getRequestURI().getRawQuery());
            int salary = Integer.parseInt(params.get("salary").get(0));
            String resp = String.valueOf(service.increaseSalary(id, salary));
            exchange.sendResponseHeaders(200, resp.length());
            OutputStream output = exchange.getResponseBody();
            output.write(resp.getBytes());
            output.flush();
        } else {
            exchange.sendResponseHeaders(405, -1);
        }
        exchange.close();
    }

    private static Map<String, List<String>> splitQuery(String query) {
        if (query == null || "".equals(query)) {
            return Collections.emptyMap();
        }

        return Pattern.compile("&").splitAsStream(query)
                .map(s -> Arrays.copyOf(s.split("="), 2))
                .collect(groupingBy(s -> decode(s[0]), mapping(s -> decode(s[1]), toList())));

    }

    private static String decode(final String encoded) {
        return encoded == null ? null : URLDecoder.decode(encoded, StandardCharsets.UTF_8);
    }
}
