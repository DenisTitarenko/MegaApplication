package com.titarenko.io;

import java.util.Scanner;

public class ConsoleReaderImpl extends AbstractReader {

    private static final ConsoleReaderImpl INSTANCE = new ConsoleReaderImpl();
    private Scanner scanner;

    private ConsoleReaderImpl() {
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
