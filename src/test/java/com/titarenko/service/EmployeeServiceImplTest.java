package com.titarenko.service;

import com.titarenko.dao.EmployeeDao;
import com.titarenko.model.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeDao employeeDao;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    public void testGetAll() {
        Employee bill = new Employee.Builder().withName("Bill").withSalary(400).build();
        when(employeeDao.getAll()).thenReturn(Collections.singletonList(bill));
        List<Employee> all = employeeService.getAll();
        assertEquals(1, all.size());
        assertEquals(bill, all.get(0));
    }
}
