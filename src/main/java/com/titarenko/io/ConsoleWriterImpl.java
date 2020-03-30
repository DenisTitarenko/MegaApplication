package com.titarenko.io;

public class ConsoleWriterImpl implements Writer {

    private static final ConsoleWriterImpl INSTANCE = new ConsoleWriterImpl();

    public static ConsoleWriterImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public void writeToOutputStream(String text) {
        System.out.println(text);
    }
}
