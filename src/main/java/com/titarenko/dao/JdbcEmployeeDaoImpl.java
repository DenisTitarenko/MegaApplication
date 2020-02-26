package com.titarenko.dao;

import com.titarenko.model.Employee;
import com.titarenko.model.Gender;
import com.titarenko.service.ConsoleWriterImpl;
import com.titarenko.service.EmployeeValidator;
import com.titarenko.service.Validator;
import com.titarenko.service.Writer;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcEmployeeDaoImpl implements EmployeeDao {

    private JdbcConnection connection = new JdbcConnection();
    private Writer consoleWriter = new ConsoleWriterImpl();
    private Validator validator = new EmployeeValidator();


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

            if (validator.isValidEmployee(employee)) {
                preparedStatement.executeUpdate();
                consoleWriter.writeToOutputStream("Employee " + employee.getName() + " added");
            } else {
                consoleWriter.writeToOutputStream("Oops.. Seems like input data wasn't correct");
                return null;
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
            if (getListOfId().contains(id)) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, employee.getName());
                preparedStatement.setString(2, employee.getSex().getCode());
                preparedStatement.setString(3, employee.getPosition());
                preparedStatement.setInt(4, employee.getSalary());
                preparedStatement.setDate(5, Date.valueOf(employee.getDateOfHire()));
                preparedStatement.setInt(6, id);
                if (validator.isValidEmployee(employee)) {
                    preparedStatement.executeUpdate();
                    consoleWriter.writeToOutputStream("Row updated");
                } else {
                    consoleWriter.writeToOutputStream("Oops.. Seems like input data wasn't correct");
                    return null;
                }
            } else {
                consoleWriter.writeToOutputStream("Employee with such id doesn't exist");
                return null;
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
        Employee employee = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                employee = parseEmployeeInfoFromSQLtoJava(resultSet);
            } else {
                consoleWriter.writeToOutputStream("Employee with such name doesn't exist");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employee;

    }

    @Override
    public List<Employee> getAll() {
        String query = "SELECT * FROM employees";
        List<Employee> list = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Employee employee = parseEmployeeInfoFromSQLtoJava(resultSet);
                list.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    private List<Integer> getListOfId() throws SQLException {
        String query = "SELECT * FROM employees";

        List<Integer> list = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            list.add(resultSet.getInt("id"));
        }
        System.out.println(list.toString());
        return list;
    }


    private Employee parseEmployeeInfoFromSQLtoJava(ResultSet resultSet) throws SQLException {
        Employee employee = new Employee();

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

        return employee;
    }
}
