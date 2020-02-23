package com.titarenko.service;

import com.titarenko.model.Employee;

import java.util.Scanner;

public class ReadFromConsole {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static Employee readEmployee() {
        SCANNER.nextLine();
        SCANNER.nextLine();
        SCANNER.nextLine();
        return new Employee();
    }

    public static int nextInt() {
        return SCANNER.nextInt();
    }
}
