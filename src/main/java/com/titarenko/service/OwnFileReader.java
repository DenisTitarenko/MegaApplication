package com.titarenko.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class OwnFileReader {

    Writer writer = new ConsoleWriterImpl();

    public static final String CREATE_TABLE_URL = "src/main/resources/create_table.sql";
    public static final String TABLE_INITIALIZER_URL = "src/main/resources/init_insert.sql";

    public void createTable(Connection connection) throws FileNotFoundException, SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        Scanner scanner = new Scanner(new FileReader(new File(CREATE_TABLE_URL)));
        while (scanner.hasNextLine()) {
            stringBuilder.append(scanner.nextLine());
        }
        Statement statement = connection.createStatement();
        statement.execute(stringBuilder.toString());


    }

    public void tableInitializer(Connection connection) throws SQLException, FileNotFoundException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM employees");
        if (!resultSet.isBeforeFirst()) {
            writer.writeToOutputStream("Do you want to fill the table automatically? [y/n]");
            String input = null;
            while (!("y".equals(input) || "n".equals(input))) {
                input = new Scanner(System.in).nextLine();
            }
            if ("y".equals(input)) {
                Scanner scanner = new Scanner(new FileReader(new File(TABLE_INITIALIZER_URL)));
                StringBuilder stringBuilder = new StringBuilder();
                while (scanner.hasNextLine()) {
                    stringBuilder.append(scanner.nextLine());
                }
                statement.execute(stringBuilder.toString());
            }
        }
    }
}







