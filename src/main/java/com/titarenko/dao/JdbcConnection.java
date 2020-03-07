package com.titarenko.dao;

import com.titarenko.io.OwnFileReader;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.sql.*;

public class JdbcConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/megaAppDb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";
    private static final Logger LOGGER = Logger.getLogger(JdbcConnection.class);

    private Connection connection;

    public JdbcConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            if (connection != null) {
                LOGGER.info("Connected to DB");
                OwnFileReader fileReader = new OwnFileReader(connection);
                fileReader.createTable();
                fileReader.tableInitializer();

            } else {
                LOGGER.error("Failed to connect to DB");
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
