package com.titarenko;

import com.titarenko.io.*;
import com.titarenko.rest.Server;
import com.titarenko.service.MenuImpl;

public class Begin {

    public static void main(String[] args) {
//        new MenuImpl();
        new Server().start();
    }

    public static Reader getReader() {
        return ConsoleReaderImpl.getInstance();
//        return FileReaderImpl.getInstance();
    }

    public static Writer getWriter() {
        return ConsoleWriterImpl.getInstance();
//        return FileWriterImpl.getInstance();
    }
}
