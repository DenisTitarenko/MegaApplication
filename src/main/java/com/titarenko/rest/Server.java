package com.titarenko.rest;

import com.titarenko.io.AbstractReader;
import com.titarenko.io.Writer;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(Server.class);
    private Socket socket;
    private static String response = "";

    public Server(Socket socket) {
        this.socket = socket;
    }

    public void up() {
        try (ServerSocket serverSocket = new ServerSocket(1408)) {
            while (true) {
                Socket socket = serverSocket.accept();
                LOGGER.info("Connected");
                Scanner request = new Scanner(socket.getInputStream()).useDelimiter("\\A");
                new Thread(new Server(socket)).start();
                if (request.hasNext() && "bye\n".equals(request.next())) {
                    break;
                }
            }
            LOGGER.info("close");
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
            response += text + "\n";
        }
    }
}
