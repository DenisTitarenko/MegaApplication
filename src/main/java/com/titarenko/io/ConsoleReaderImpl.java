package com.titarenko.io;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleReaderImpl extends AbstractReader {

    private Scanner scanner;

    private ConsoleReaderImpl() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }
}
