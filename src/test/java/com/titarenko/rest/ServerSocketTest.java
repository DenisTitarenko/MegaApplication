package com.titarenko.rest;

import com.titarenko.service.MenuImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServerSocketTest {

    private static final String HTTP_200_OK = "HTTP/1.1 200 OK";
    private static final String EXPECTED_RESPONSE = """
            1. Add employee to DB;
            2. Find employee by name;
            3. Update employee info;
            4. Delete employee by name;
            5. Get all employees;
            6. Show all employees group by position & date of start work;
            7. Show employees with same salary;
            8. Increase someone's salary;
            0. Exit.
            """;

    @BeforeAll
    static void startApplication() throws InterruptedException {
        new Thread(() -> {
            new MenuImpl(null, new ClientServerSocket.SocketReader(), new ClientServerSocket.SocketWriter());
            new ClientServerSocket(null).up();
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
        assertTrue(response.contains(HTTP_200_OK));
        assertTrue(response.contains(EXPECTED_RESPONSE));
    }

    @AfterAll
    static void closeApplication() throws IOException {
        try (Socket socket = new Socket("localhost", 1408);
             PrintWriter pw = new PrintWriter(socket.getOutputStream(), true)) {
            pw.println("bye");
        }
    }
}
