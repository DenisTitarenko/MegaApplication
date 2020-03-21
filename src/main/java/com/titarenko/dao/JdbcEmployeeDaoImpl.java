package com.titarenko.dao;

import com.titarenko.Begin;
import com.titarenko.io.Writer;
import com.titarenko.model.Employee;
import com.titarenko.model.Gender;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcEmployeeDaoImpl implements EmployeeDao {

    private JdbcConnection connection = new JdbcConnection();
    private Writer writer = Begin.getWriter();

    @Override
    public Integer create(Employee employee) {
        String query = "INSERT INTO employees(name, sex, position, salary, dateOfHire) VALUES(?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getSex().getCode());
            preparedStatement.setString(3, employee.getPosition());
            preparedStatement.setInt(4, employee.getSalary());
            preparedStatement.setDate(5, Date.valueOf(employee.getDateOfHire()));
            preparedStatement.executeUpdate();
            writer.writeToOutputStream("Employee " + employee.getName() + " added");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return get(employee.getName()).getId();
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
                writer.writeToOutputStream("Employee with such name doesn't exist");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public Employee get(int id) {
        String query = "SELECT * FROM employees WHERE id = ?";
        Employee employee = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                employee = parseEmployeeInfoFromSQLtoJava(resultSet);
            } else {
                writer.writeToOutputStream("Employee with such id doesn't exist");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public Employee update(int id, Employee employee) {
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
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
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
            writer.writeToOutputStream(row + " row(s) deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return row != 0;
    }

    @Override
    public List<Employee> getAll() {
        String query = "SELECT * FROM employees";
        return getListEmployees(query);
    }

    @Override
    public List<Employee> getEmployeesWithSameSalary() {
        String query =
                "SELECT * FROM employees " +
                        "WHERE salary IN " +
                        "(SELECT salary FROM employees " +
                        "GROUP by salary " +
                        "HAVING count(*) > 1) " +
                        "ORDER BY salary DESC";
        return getListEmployees(query);
    }

    public List<Employee> getListEmployees(String query) {
        List<Employee> list = new ArrayList<>();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            while (resultSet.next()) {
                Employee employee = parseEmployeeInfoFromSQLtoJava(resultSet);
                list.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Employee parseEmployeeInfoFromSQLtoJava(ResultSet resultSet) throws SQLException {
        return Employee.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .sex(Gender.getGender(resultSet.getString("sex")))
                .position(resultSet.getString("position"))
                .salary(resultSet.getInt("salary"))
                .dateOfHire(resultSet.getDate("dateOfHire").toLocalDate())
                .build();
    }
}
