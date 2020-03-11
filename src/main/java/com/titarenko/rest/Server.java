package com.titarenko.rest;

import com.titarenko.io.AbstractReader;
import com.titarenko.io.Writer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

    private Socket socket;
    private static String response = "";

    public Server(Socket socket) {
        this.socket = socket;
    }

    public void up() {
        try {
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
        String httpResponse = """
                HTTP/1.1 200 OK
                Content-Length: %s
                Content-Type: text/plain; charset=utf-8
                Server: MegaApplication

                %s
                """.formatted(response.length(), response);
        try (PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true)) {
            printWriter.println(httpResponse);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static class SocketReader extends AbstractReader {
        @Override
        public String readLine() {
            return "0";
        }
    }


    public static class SocketWriter implements Writer {
        @Override
        public void writeToOutputStream(String text) {
            response += text + System.lineSeparator();
        }
    }
}
