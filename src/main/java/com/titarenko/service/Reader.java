package com.titarenko.service;

import com.titarenko.model.Employee;

public interface Reader {
    Employee readEmployee();
    Integer readInt();
    String readLine();
}
