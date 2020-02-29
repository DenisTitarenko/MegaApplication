package com.titarenko.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.titarenko.model.Employee;

import java.io.IOException;

public class JsonParser {

    private ObjectMapper mapper = new ObjectMapper();

    public JsonParser() {
        mapper.registerModule(new SimpleModule().addSerializer(Employee.class, new CustomSerializer()));
    }

    public String toJson(Object ob) {
        String jsonString = null;
        try {
            // Java objects to JSON string - pretty-print
            jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ob);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
