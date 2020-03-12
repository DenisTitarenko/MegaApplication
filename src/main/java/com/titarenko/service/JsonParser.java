package com.titarenko.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.titarenko.model.Employee;

import java.io.IOException;
import java.util.List;

public class JsonParser {

    private ObjectMapper mapper = new ObjectMapper();

    public String serialize(Object ob) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ob);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Employee deserialize(String string) {
        try {
            return mapper.readValue(string, Employee.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Employee> deserializeList(String string) {
        try {
            return mapper.readValue(string, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
