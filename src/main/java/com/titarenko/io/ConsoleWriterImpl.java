package com.titarenko.io;

import com.titarenko.di.annotation.Brick;
import org.springframework.stereotype.Component;

@Component
public class ConsoleWriterImpl implements Writer {

    private static final ConsoleWriterImpl INSTANCE = new ConsoleWriterImpl();

    private ConsoleWriterImpl() {
    }

    public static ConsoleWriterImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public void writeToOutputStream(String text) {
        System.out.println(text);
    }
}
