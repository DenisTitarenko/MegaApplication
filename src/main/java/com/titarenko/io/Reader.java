package com.titarenko.io;

import com.titarenko.model.Employee;
import com.titarenko.model.Gender;

import java.time.LocalDate;

public interface Reader {
    Employee readEmployee();
    Integer readInt();
    String readLine();
    Gender readGender();
    LocalDate readDate();
}
