package com.titarenko.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.titarenko.model.Employee;

import java.io.IOException;

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
}
