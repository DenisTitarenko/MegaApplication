package com.titarenko.dao;

import com.titarenko.model.Employee;
import com.titarenko.model.Gender;
import com.titarenko.service.ConsoleWriterImpl;
import com.titarenko.service.Writer;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class JdbcEmployeeDaoImpl implements EmployeeDao {

    JdbcConnection connection = new JdbcConnection();
    Writer consoleWriter = new ConsoleWriterImpl();

    @Override
    public Integer create(Employee employee) {
        String query = "INSERT INTO employees VALUES(DEFAULT, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getSex().getCode());
            preparedStatement.setString(3, employee.getPosition());
            preparedStatement.setInt(4, employee.getSalary());
            preparedStatement.setDate(5, Date.valueOf(employee.getDateOfHire()));
            if (preparedStatement.executeUpdate() != 0) {
                consoleWriter.writeToOutputStream("Employee " + employee.getName() + " added");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return get(employee.getName()).getId();
    }

    @Override
    public Employee update(Integer id, Employee employee) {
        String query = "UPDATE employees SET " +
                "name = ?, " +
                "sex = ?, " +
                "position = ?, " +
                "salary = ?, " +
                "dateOfHire = ? " +
                "WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getSex().getCode());
            preparedStatement.setString(3, employee.getPosition());
            preparedStatement.setInt(4, employee.getSalary());
            preparedStatement.setDate(5, Date.valueOf(employee.getDateOfHire()));
            preparedStatement.setInt(6, id);

            if (preparedStatement.executeUpdate() == 0) {
                consoleWriter.writeToOutputStream("Employee with such id doesn't exist");
            } else {
                consoleWriter.writeToOutputStream("Row updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return get(employee.getName());
    }


    @Override
    public boolean delete(String name) {
        String query = "DELETE FROM employees WHERE name = ?";
        PreparedStatement preparedStatement;
        int row = 0;

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            row += preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return row != 0;
    }

    @Override
    public Employee get(String name) {
        String query = "SELECT * FROM employees WHERE name = ?";
        PreparedStatement preparedStatement;
        Employee employee = new Employee();

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                int id = resultSet.getInt("id");
                String emplName = resultSet.getString("name");
                Gender sex = Gender.getGender(resultSet.getString("sex"));
                String position = resultSet.getString("position");
                int salary = resultSet.getInt("salary");
                LocalDate dateOfHire = resultSet.getDate("dateOfHire").toLocalDate();

                employee.setId(id);
                employee.setName(emplName);
                employee.setSex(sex);
                employee.setPosition(position);
                employee.setSalary(salary);
                employee.setDateOfHire(dateOfHire);
            } else {
                consoleWriter.writeToOutputStream("Employee with such name doesn't exist");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employee;

    }
}
