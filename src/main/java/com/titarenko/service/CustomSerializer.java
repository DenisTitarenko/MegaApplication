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
    public void serialize(Employee employee, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeStartObject();
        generator.writeNumberField("id", employee.getId());
        generator.writeStringField("name", employee.getName());
        generator.writeStringField("sex", employee.getSex().toString());
        generator.writeStringField("position", employee.getPosition());
        generator.writeNumberField("salary", employee.getSalary());
        generator.writeStringField("dateOfHire", employee.getDateOfHire()
                .format(DateTimeFormatter.ofPattern("d MMM yyyy", new Locale("us", "US"))));
        generator.writeEndObject();
    }
}
