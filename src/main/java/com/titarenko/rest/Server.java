package com.titarenko.rest;

import com.titarenko.model.Operations;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(1408); Socket socket = serverSocket.accept()) {
            socket.getOutputStream().write(("HTTP 200 OK\r\n\r\n" + getOperations()).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getOperations() {
        return Arrays.stream(Operations.values())
                .map(operation -> operation.getLabel() + ". " + operation.getTitle())
                .reduce((s1, s2) -> s1 + "\n" + s2)
                .orElse("");
    }
}
