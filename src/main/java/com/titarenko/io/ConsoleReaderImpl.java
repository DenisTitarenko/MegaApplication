package com.titarenko.io;

import com.titarenko.di.annotation.Brick;

import java.util.Scanner;

@Brick
public class ConsoleReaderImpl extends AbstractReader {

    private static final ConsoleReaderImpl INSTANCE = new ConsoleReaderImpl();
    private Scanner scanner;

    public ConsoleReaderImpl() {
        this.scanner = new Scanner(System.in);
    }

    public static ConsoleReaderImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }
}
