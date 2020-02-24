package com.titarenko.service;

public class ConsoleWriterImpl implements Writer{

    @Override
    public void writeToOutputStream(String text) {
        System.out.println(text);
    }
}
