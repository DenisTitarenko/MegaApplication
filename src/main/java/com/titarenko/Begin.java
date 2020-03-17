package com.titarenko;

import com.titarenko.dao.JdbcEmployeeDaoImpl;
import com.titarenko.io.*;
import com.titarenko.rest.Controller;
import com.titarenko.rest.Server;
import com.titarenko.service.EmployeeServiceImpl;
import com.titarenko.service.MenuImpl;

import java.net.Socket;

public class Begin {

    private static String response = "";

    public static void main(String[] args) {
        new MenuImpl(new EmployeeServiceImpl(new JdbcEmployeeDaoImpl()), getReader(), getWriter());
//        new Server(new Socket()).up();
//        new Controller(new EmployeeServiceImpl(new JdbcEmployeeDaoImpl()));
    }

    public static Reader getReader() {
        return ConsoleReaderImpl.getInstance();
//        return FileReaderImpl.getInstance();
//        return new AbstractReader() {
//            @Override
//            public String readLine() {
//                return "0";
//            }
//        };
    }

    public static Writer getWriter() {
        return ConsoleWriterImpl.getInstance();
//        return FileWriterImpl.getInstance();
//        return text -> response += text + "\n";
    }
}
