package com.titarenko.dao;

import com.titarenko.service.ConsoleWriterImpl;
import com.titarenko.service.OwnFileReader;
import com.titarenko.service.Writer;

import java.io.FileNotFoundException;
import java.sql.*;

public class JdbcConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/megaAppDb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    private Connection connection;
    private Writer consoleWriter = new ConsoleWriterImpl();
    private OwnFileReader fileReader = new OwnFileReader();

    public JdbcConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            if (connection != null) {
                consoleWriter.writeToOutputStream("\nConnected to DB\n");
            } else {
                consoleWriter.writeToOutputStream("Failed to connect to DB");
            }
            fileReader.createTable(connection);
            fileReader.tableInitializer(connection);
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
