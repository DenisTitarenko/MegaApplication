package com.titarenko.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/database1";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";
    Connection connection;

    public void setConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            if (connection != null) {
                System.out.println("\nConnected to DB");
            } else {
                System.out.println("Failed to connect to DB");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
