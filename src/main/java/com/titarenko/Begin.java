package com.titarenko;

import com.titarenko.service.Menu;
import com.titarenko.service.MenuImpl;
import com.titarenko.service.ReadFromConsole;

public class Begin {

    public static void main(String[] args) {
        Menu menu = new MenuImpl();
        while (menu.isContinue()) {
            menu.show();
            menu.perform(ReadFromConsole.nextInt());
        }
    }

}
