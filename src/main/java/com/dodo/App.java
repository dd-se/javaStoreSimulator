package com.dodo;

import java.util.Scanner;

import com.dodo.Menus.MainMenu;
import com.dodo.Utils.Colors;

/**
 * Simulates a store.
 *
 */
public class App {

    public static final String INVALID_INPUT = Colors.RED + "Invalid input." + Colors.RESET;

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        MainMenu.startLoop();
    }
}
