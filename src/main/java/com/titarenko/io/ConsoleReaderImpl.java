package com.titarenko.io;

import com.titarenko.di.annotation.Brick;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
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
