package com.titarenko;

import com.titarenko.dao.HibernateEmployeeDaoImpl;
import com.titarenko.dao.JdbcEmployeeDaoImpl;
import com.titarenko.di.Summer;
import com.titarenko.io.*;
import com.titarenko.rest.Controller;
import com.titarenko.rest.Server;
import com.titarenko.service.EmployeeServiceImpl;
import com.titarenko.service.MenuImpl;

import java.net.Socket;

public class Begin {

    public static void main(String[] args) {
        Summer.go("src/main/java/com/titarenko");
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
