package com.dodo.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.dodo.Models.Customer;
import com.dodo.Models.Order;
import com.dodo.Models.Product;
import com.dodo.Models.Store;

public class CsvHandler {

    public static final String DIRNAME = "store_data";

    public static final String PRODUCTS = "products.txt";

    public static final String ORDERS = "orders.txt";

    public static final String CUSTOMERS = "customers.txt";

    public static final Path DIR_PATH = Paths.get(DIRNAME);

    public static final Path CUSTOMERS_PATH = Paths.get(DIRNAME, CUSTOMERS);

    public static final Path ORDERS_PATH = Paths.get(DIRNAME, ORDERS);

    public static final Path PRODUCTS_PATH = Paths.get(DIRNAME, PRODUCTS);

    public static Store csvToStoreLoader() {
        List<Product> products;
        List<Order> orders;
        List<Customer> customers;
        try {
            products = productsLoader();
            orders = ordersLoader(products);
            customers = customersLoader();
            return new Store(products, orders, customers);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new Store(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

    }

    public static void storeToCsvSaver(Store store) {
        try {
            if (!Files.isDirectory(DIR_PATH)) {
                Path createdDirectory = FileHandler.createDirectory(DIRNAME);
                System.out.println("Created: " + createdDirectory + " to store future data.");
            }
            Files.write(CUSTOMERS_PATH, store.customersToBytes());
            Files.write(PRODUCTS_PATH, store.productsToBytes());
            Files.write(ORDERS_PATH, store.ordersToBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Order> ordersLoader(List<Product> storeProducts)
            throws NumberFormatException, FileNotFoundException {
        List<Order> orders = new ArrayList<>();

        try (Scanner orderParser = new Scanner(new File(ORDERS_PATH.toAbsolutePath().toString()))) {
            orderParser.useDelimiter(",|\n");

            while (orderParser.hasNext()) {

                int orderId = orderParser.nextInt();
                int custId = orderParser.nextInt();
                String[] productIds = orderParser.next().split("-");
                List<Product> productList = new ArrayList<>();
                for (String id : productIds) {
                    productList.add(storeProducts.get(Integer.parseInt(id) - 1));
                }
                String orderDate = orderParser.next();
                orders.add(new Order(orderId, custId, productList, orderDate));
            }
            return orders;
        }

    }

    private static List<Customer> customersLoader() throws NumberFormatException, FileNotFoundException {
        List<Customer> customers = new ArrayList<>();
        try (Scanner customerParser = new Scanner(new File(CUSTOMERS_PATH.toAbsolutePath().toString()))) {

            customerParser.useDelimiter(",|\n");

            while (customerParser.hasNext()) {

                int customerId = customerParser.nextInt();
                String customerName = ListUtils.convertToTitleCase(customerParser.next());
                String customerAddress = ListUtils.convertToTitleCase(customerParser.next());
                String customerEmail = customerParser.next().toLowerCase();
                customers.add(new Customer(customerId, customerName, customerAddress, customerEmail));

            }
            return customers;
        }

    }

    private static List<Product> productsLoader() throws NumberFormatException, FileNotFoundException {

        List<Product> products = new ArrayList<>();

        try (Scanner productParser = new Scanner(new File(PRODUCTS_PATH.toAbsolutePath().toString()))) {

            productParser.useDelimiter(",|\n");

            while (productParser.hasNext()) {

                int prodId = productParser.nextInt();
                String productName = productParser.next();
                double price = Double.valueOf(productParser.next());
                products.add(new Product(prodId, productName, price));

            }
            return products;
        }

    }

}
