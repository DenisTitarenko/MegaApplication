package com.titarenko.rest;

import com.titarenko.Begin;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ServerSocketTest {

    private static final String EXPECTED_RESPONSE = "" +
            "1. Add employee to DB;\n" +
            "2. Find employee by name;\n" +
            "3. Update employee info;\n" +
            "4. Delete employee by name;\n" +
            "5. Get all employees;\n" +
            "6. Show all employees group by position & date of start work;\n" +
            "7. Show employees with same salary;\n" +
            "8. Increase someone's salary;\n" +
            "0. Exit.";

    @BeforeAll
    static void startApplication() throws InterruptedException {
        new Thread(() -> {
            try {
                Begin.main(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(500);
    }

    @Test
    public void testResponse() throws IOException {
        Socket socket = new Socket("localhost", 1408);
        Scanner scanner = new Scanner(socket.getInputStream()).useDelimiter("\\A");
        assertTrue(scanner.hasNext());
        String response = scanner.next();
        socket.close();
        assertNotNull(response);
        assertTrue(response.contains(EXPECTED_RESPONSE));
    }
}
