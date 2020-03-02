package com.titarenko;

import com.titarenko.io.*;
import com.titarenko.service.ConsoleMenuImpl;
import com.titarenko.service.FileMenuImpl;

public class Begin {

    public static void main(String[] args) {
        new FileMenuImpl();
    }

    public static Reader getReader() {
        return ConsoleReaderImpl.getInstance();
    }

    public static Writer getWriter() {
        return ConsoleWriterImpl.getInstance();
    }

}
