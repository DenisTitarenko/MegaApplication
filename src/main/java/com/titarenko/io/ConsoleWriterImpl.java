package com.titarenko.io;

import com.titarenko.di.annotation.Brick;

@Brick
public class ConsoleWriterImpl implements Writer {

    private static final ConsoleWriterImpl INSTANCE = new ConsoleWriterImpl();

    public ConsoleWriterImpl() {
    }

    public static ConsoleWriterImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public void writeToOutputStream(String text) {
        System.out.println(text);
    }
}
