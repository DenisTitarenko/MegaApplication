package com.titarenko.dao;

import com.titarenko.Begin;
import com.titarenko.io.OwnFileReader;
import com.titarenko.io.Writer;

import java.io.FileNotFoundException;
import java.sql.*;

public class JdbcConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/megaAppDb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    private Connection connection;

    public JdbcConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Writer consoleWriter = Begin.getWriter();
            if (connection != null) {
                consoleWriter.writeToOutputStream("\nConnected to DB\n");
                OwnFileReader fileReader = new OwnFileReader(connection);
                fileReader.createTable();
                fileReader.tableInitializer();
            } else {
                consoleWriter.writeToOutputStream("Failed to connect to DB");
            }
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Statement createStatement() throws SQLException {
        return connection.createStatement();
    }

    public PreparedStatement prepareStatement(String query) throws SQLException {
        return connection.prepareStatement(query);
    }
}
