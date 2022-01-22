package com.dodo.Menus;

import java.util.List;

import com.dodo.App;
import com.dodo.Models.Store;
import com.dodo.Utils.StoreLoader;

public class MainMenu {

    public final static String MAIN_MENU = "[1] Order utils\n" + "[2] Product utils\n"
            + "[3] Customer utils\n[e] Exit\nChoose: ";

    public static void startLoop() {

        Store store = StoreLoader.csvToStoreLoader();

        List<String> logo = StoreLoader.readAllLines(StoreLoader.getLogoPath());
        StoreLoader.setLogo(logo);
        StoreLoader.printLogo();
        while (true) {

            System.out.print(MainMenu.MAIN_MENU);
            String command = App.scanner.nextLine();

            if (command.equals("e")) {
                System.out.println("Bye.");
                StoreLoader.storeToCsvSaver(store);
                break;
            }
            mainMenu(command, store);
        }
    }

    public static void mainMenu(String command, Store store) {
        switch (command) {
            case "1":
                OrderMenu.orderMenu(store);
                break;
            case "2":
                ProdMenu.productMenu(store.getProducts(), store.getOrders());
                break;
            case "3":
                CustMenu.customerMenu(store.getCustomers(), store.getOrders());
                break;
            case "b":
                return;
            default:
                System.out.println(App.INVALID_INPUT);
                break;
        }
    }
}
