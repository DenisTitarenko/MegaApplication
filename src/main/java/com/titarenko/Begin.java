package com.titarenko;

import com.titarenko.di.Summer;
import com.titarenko.io.ConsoleReaderImpl;
import com.titarenko.io.ConsoleWriterImpl;
import com.titarenko.io.Reader;
import com.titarenko.io.Writer;

public class Begin {

    public static void main(String[] args) {
        Summer.go("src/main/java/com/titarenko");
    }

    public static Reader getReader() {
        return ConsoleReaderImpl.getInstance();         // for work with console
//        return FileReaderImpl.getInstance();          // for work with files
//        return new AbstractReader() {                 // for work with web
//            @Override
//            public String readLine() {
//                return "0";
//            }
//        };
    }

    public static Writer getWriter() {
        return ConsoleWriterImpl.getInstance();         // for work with console
//        return FileWriterImpl.getInstance();          // for work with files
//        return text -> response += text + "\n";       // for work with web
    }
}
