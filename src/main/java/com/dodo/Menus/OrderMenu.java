package com.dodo.Menus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.dodo.App;
import com.dodo.Exceptions.OrderNotFoundException;
import com.dodo.Exceptions.ProductNotFoundException;
import com.dodo.Models.Order;
import com.dodo.Models.Product;
import com.dodo.Models.Store;
import com.dodo.StoreLogic.AddToBasket;
import com.dodo.StoreLogic.Checkout;
import com.dodo.StoreLogic.RemoveFromBasket;
import com.dodo.StoreLogic.TakeOrder;
import com.dodo.Utils.Colors;
import com.dodo.Utils.ListUtils;
import com.dodo.Utils.Parser;
import com.dodo.Utils.PrintUtils;

public class OrderMenu {

    public final static String ORDER_MENU = Colors.GREEN +
            "-[ORDER UTILS]-\n" + Colors.RESET
            + "[1] Take order\n" + "[2] List orders sorted by size\n"
            + "[3] List orders sorted by date" + "\n[4] Find order by ID\nChoose: ";

    public final static ArrayList<String> SINGULAR_COMMANDS = new ArrayList<>(
            Arrays.asList("ABORT", "CHECKOUT", "RANDOM", "HELP"));

    public static void orderMenu(Store store) {
        System.out.print(ORDER_MENU);
        String command = App.scanner.nextLine();
        switch (command) {
            case "1":
                System.out.print("Input customer name: ");
                String name = App.scanner.nextLine();
                boolean customerIsSet = TakeOrder.setOrderCustomer(name, store.getCustomers());
                if (customerIsSet) {
                    takeOrderHandler(store.getProducts(), store.getOrders());
                }
                break;
            case "2":
                Collections.sort(store.getOrders()); // SORT BY ORDER TOTAL COST
                PrintUtils.printOrders(store.getOrders());
                break;
            case "3":
                ListUtils.sortOrdersByNewestFirst(store.getOrders()); // SORT BY LATEST
                PrintUtils.printOrders(store.getOrders());
                break;
            case "4":
                try {
                    System.out.print("Input Order ID: ");
                    int orderId = Parser.parseInt(App.scanner.nextLine());
                    Order orderFoundById = ListUtils.findOrderById(store.getOrders(), orderId);
                    PrintUtils.printOrders(Arrays.asList(orderFoundById));
                } catch (NumberFormatException e) {
                    System.out.println("Not an integer.");
                } catch (OrderNotFoundException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "b":
                return;
            default:
                System.out.println(App.INVALID_INPUT);
                break;
        }
        orderMenu(store);
    }

    public static void takeOrderHandler(List<Product> storeProducts, List<Order> storeOrders) {
        PrintUtils.printProducts(storeProducts);
        List<Product> customerBasket = new ArrayList<>();
        boolean isRunning = true;
        while (isRunning) {

            System.out.print("Type [help] to see commands: ");
            String[] input = App.scanner.nextLine().toUpperCase().split("\\s+");
            if (input.length < 2 && !SINGULAR_COMMANDS.contains(input[0])) {
                System.out.println(App.INVALID_INPUT);
                continue;
            }
            switch (input[0]) {
                case "HELP":
                    TakeOrder.helpMenu();
                    break;
                case "ADD":
                    try {
                        int productId = Parser.parseInt(input[1]);
                        AddToBasket.addToBasket(productId, customerBasket, storeProducts);
                    } catch (NumberFormatException e) {
                        System.out.println("Not an integer.");
                    } catch (ProductNotFoundException e) {
                        System.out.println(e.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "REMOVE":
                    try {
                        String productName = input[1];
                        RemoveFromBasket.removeFromBasket(customerBasket, productName);
                    } catch (ProductNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "CHECKOUT":
                    boolean checkOutIsSuccessful = Checkout.checkout(customerBasket, storeOrders, true);
                    if (checkOutIsSuccessful) {
                        isRunning = false;
                    }
                    break;
                case "RANDOM":
                    Product randomProduct = (Product) ListUtils.pickRandomFromCollection(storeProducts);
                    System.out.println(
                            Colors.YELLOW + "Added " + randomProduct.getProductName() + " to your basket."
                                    + Colors.RESET);
                    customerBasket.add(randomProduct);
                    break;
                case "ABORT":
                    System.out.println(Colors.RED + "Order deleted, thank you and please come again." + Colors.RESET);
                    isRunning = false;
                    break;
                default:
                    System.out.println(App.INVALID_INPUT);
                    break;
            }
        }
    }
}
