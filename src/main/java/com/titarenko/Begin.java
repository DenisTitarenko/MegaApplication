package com.titarenko;

import com.titarenko.model.Employee;
import com.titarenko.service.ConsoleReaderImpl;
import com.titarenko.service.Menu;
import com.titarenko.service.MenuImpl;

public class Begin {

    public static void main(String[] args) {

        Employee employee = new ConsoleReaderImpl().readEmployee();
        System.out.println(employee);

//        Menu menu = new MenuImpl();
//        while (menu.isContinue()) {
//            menu.show();
//            menu.perform(new ConsoleReaderImpl().readInt());
//        }
    }

}
