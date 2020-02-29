package com.titarenko.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.titarenko.model.Employee;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CustomSerializer extends StdSerializer<Employee> {

    public CustomSerializer() {
        this(null);
    }

    public CustomSerializer(Class<Employee> t) {
        super(t);
    }

    @Override
    public void serialize(Employee employee, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeNumberField("id", employee.getId());
        jgen.writeStringField("name", employee.getName());
        jgen.writeStringField("sex", employee.getSex().getCode());
        jgen.writeStringField("position", employee.getPosition());
        jgen.writeNumberField("salary", employee.getSalary());
        jgen.writeStringField("dateOfHire", employee.getDateOfHire()
                .format(DateTimeFormatter.ofPattern("d MMM yyyy", new Locale("us", "US"))));
        jgen.writeEndObject();
    }
}
