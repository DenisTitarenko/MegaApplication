package com.titarenko;

import com.titarenko.io.ConsoleReaderImpl;
import com.titarenko.io.ConsoleWriterImpl;
import com.titarenko.io.Reader;
import com.titarenko.io.Writer;
import com.titarenko.service.MenuImpl;

public class Begin {

    public static void main(String[] args) {
        new MenuImpl();
    }

    public static Reader getReader() {
        return ConsoleReaderImpl.getInstance();
    }

    public static Writer getWriter() {
        return ConsoleWriterImpl.getInstance();
    }

}
