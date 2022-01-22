package com.dodo.Utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dodo.Models.CustomerHelper;
import com.dodo.Models.Order;
import com.dodo.Models.Product;

public class PrintUtils {

    public final static String BEST_CUST_FORMAT = "%-20s%-15s%-15s\n+---------------------------------------------+";

    public final static Object[] BEST_CUST_HEADERS = { "CUSTOMER NAME", "TOTAL SPENT", "CUSTOMER ID", };

    public final static String POPULAR_PRODUCTS_FORMAT = "%-20s%-20s\n+-----------------------+";

    public final static Object[] POPULAR_PRODUCTS_HEADERS = { "PRODUCT NAME", "COUNT" };

    public final static String ORDER_FORMAT = "%-29s%-49s%-35s";

    public static void printOrders(List<Order> orders) {

        orders.forEach(order -> {
            System.out.println(Colors.YELLOW + "--- ORDER START ---" + Colors.RESET);
            System.out.println(Colors.CYAN
                    + String.format(ORDER_FORMAT, "Order ID: " + Colors.RESET + order.getOrderId() + Colors.CYAN,
                            "Customer ID: " + Colors.RESET + order.getCustId() + Colors.CYAN,
                            "Date: " + Colors.RESET + order.getOrderDate() + Colors.CYAN + "\n"));

            order.getProductList().forEach(product -> {
                System.out.println(product);
            });
            System.out.println(
                    String.format("%87s", Colors.PURPLE + "TOTAL: " + Colors.WHITE +
                            Parser.getCurrencyFormat(order.totalOrderCost()) + Colors.RESET));
            System.out.print(Colors.YELLOW + "--- ORDER END ---\n" + Colors.RESET);
        });
    }

    public static void printOrders2(List<Order> orders) {
        for (Order order : orders) {
            System.out.println(Colors.YELLOW + "--- ORDER START ---" + Colors.RESET);

            System.out.println(Colors.CYAN
                    + String.format(ORDER_FORMAT, "Order ID: " + Colors.RESET + order.getOrderId() + Colors.CYAN,
                            "Customer ID: " + Colors.RESET + order.getCustId() + Colors.CYAN,
                            "Date: " + Colors.RESET + order.getOrderDate() + Colors.CYAN + "\n"));

            for (Product product : order.getProductList()) {
                System.out.println(product);
            }
            System.out.println(
                    String.format("%87s", Colors.PURPLE + "TOTAL: " + Colors.WHITE +
                            Parser.getCurrencyFormat(order.totalOrderCost()) + Colors.RESET));

            System.out.print(Colors.YELLOW + "--- ORDER END ---\n" + Colors.RESET);
        }
    }

    public static void printCustomersTotalExpenditureList(List<CustomerHelper> customersTotalExpenditureList) {

        System.out.println(Colors.YELLOW + String.format(BEST_CUST_FORMAT, BEST_CUST_HEADERS));
        for (CustomerHelper customer : customersTotalExpenditureList) {
            System.out.println(String.format(BEST_CUST_FORMAT, customer.getName(),
                    Parser.getCurrencyFormat(customer.getTotalSpent()), customer.getCustId()));
        }
        System.out.print(Colors.RESET);
    }

    public static void printProducts2(List<Product> products) {
        for (Product product : products) {
            System.out.println(Colors.CYAN + product);
        }
        System.out.print(Colors.RESET);
    }

    public static void printProducts(List<Product> products) {
        products.forEach(product -> {
            System.out.println(product);
        });
    }

    public static boolean printMostPopularProducts(Map<String, Integer> sortedMostPopularProductsList, int howMany) {

        System.out.println(Colors.YELLOW + String.format(POPULAR_PRODUCTS_FORMAT, POPULAR_PRODUCTS_HEADERS));
        sortedMostPopularProductsList.forEach((k, v) -> {
            System.out.println(String.format(POPULAR_PRODUCTS_FORMAT, k, v));
        });
        return true;
    }

    public static boolean printMostPopularProducts2(Map<String, Integer> sortedMostPopularProductsList, int howMany) {
        Set<String> sortedListNames = sortedMostPopularProductsList.keySet();
        Collection<Integer> sortedListValues = sortedMostPopularProductsList.values();

        Iterator<String> iteratorNames = sortedListNames.iterator();
        Iterator<Integer> iteratorValues = sortedListValues.iterator();

        System.out.println(Colors.YELLOW + String.format(POPULAR_PRODUCTS_FORMAT, POPULAR_PRODUCTS_HEADERS));
        int count = 0;
        while (iteratorNames.hasNext() && count < howMany) {
            System.out.println(String.format(POPULAR_PRODUCTS_FORMAT, iteratorNames.next(), iteratorValues.next()));
            count++;
        }
        System.out.print(Colors.RESET);
        return true;
    }

}
