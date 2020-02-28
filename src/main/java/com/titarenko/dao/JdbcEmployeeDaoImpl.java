package com.titarenko.dao;

import com.titarenko.model.Employee;
import com.titarenko.model.Gender;
import com.titarenko.service.ConsoleWriterImpl;
import com.titarenko.service.Writer;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JdbcEmployeeDaoImpl implements EmployeeDao {

    private JdbcConnection connection = new JdbcConnection();
    private Writer writer = new ConsoleWriterImpl();

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
    public List<Employee> getAllGroupByPositionAndDate() {
        String query = "SELECT * FROM employees ORDER BY position, dateOfHire DESC";
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

    @Override
    public boolean increaseSalary(int id, int plusSalary) {
        String query = "UPDATE employees SET salary = salary + ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, plusSalary);
            preparedStatement.setInt(2, id);
            writer.writeToOutputStream(preparedStatement.executeUpdate() + " row(s) updated");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    private List<Employee> getListEmployees(String query) {
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

    public List<Integer> getListOfId() {
        String query = "SELECT * FROM employees";
        List<Integer> list = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                list.add(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
