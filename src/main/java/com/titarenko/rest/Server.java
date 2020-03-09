package com.titarenko.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

    private Socket socket;
    private String response;

    public Server(Socket socket) {
        this.socket = socket;
    }

    public void up(String response) {
        try {
            this.response = response;
            ServerSocket serverSocket = new ServerSocket(1408);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Connected");
                new Thread(new Server(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try (PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true)) {
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println(response);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
