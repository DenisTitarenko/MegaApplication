package com.titarenko.dao;

import com.titarenko.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByName(String name);

    @Query("SELECT e FROM Employee e WHERE e.salary IN (SELECT e.salary FROM Employee e " +
            "GROUP by e.salary HAVING count(e) > 1) ORDER BY e.salary DESC")
    List<Employee> getEmployeesWithSameSalary();

    List<Employee> findByOrderByPositionAscDateOfHireDesc();
}
