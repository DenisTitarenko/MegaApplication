package com.titarenko.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class OwnFileReader {

    private static final String CREATE_TABLE_URL = "src/main/resources/create_table.sql";
    private static final String TABLE_INITIALIZER_URL = "src/main/resources/init_insert.sql";
    private Connection connection;

    public OwnFileReader(Connection connection) {
        this.connection = connection;
    }

    public void createTable() throws FileNotFoundException, SQLException {
        connection.createStatement().execute(readSql(CREATE_TABLE_URL));
    }

    public void tableInitializer() throws SQLException, FileNotFoundException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM employees");
        if (!resultSet.isBeforeFirst()) {
            statement.execute(readSql(TABLE_INITIALIZER_URL));
        }
    }

    private String readSql(String filePath) throws FileNotFoundException {
        StringBuilder stringBuilder = new StringBuilder();
        Scanner scanner = new Scanner(new FileReader(new File(filePath)));
        while (scanner.hasNextLine()) {
            stringBuilder.append(scanner.nextLine());
        }
        return stringBuilder.toString();
    }
}
