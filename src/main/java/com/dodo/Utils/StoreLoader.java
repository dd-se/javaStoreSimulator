package com.dodo.Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

public class StoreLoader {

    public static final String DIRNAME = "store_data";

    public static final String PRODUCTS = "products.txt";

    public static final String ORDERS = "orders.txt";

    public static final String CUSTOMERS = "customers.txt";

    public static final String LOGO = "logo.txt";

    public static final String STORE = "store.bin";

    public static final Path DIR_PATH = Paths.get(DIRNAME);

    public static final Path STORE_PATH = Paths.get(DIRNAME, STORE);

    public static final Path CUSTOMERS_PATH = Paths.get(DIRNAME, CUSTOMERS);

    public static final Path ORDERS_PATH = Paths.get(DIRNAME, ORDERS);

    public static final Path PRODUCTS_PATH = Paths.get(DIRNAME, PRODUCTS);

    public static final Path LOGO_PATH = Paths.get(DIRNAME, LOGO);

    private static List<String> logo;

    public static void WriteOrderToFile(Order order) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ORDER" + Parser.getNewOrderDate() + ".txt"))) {
            String orderWithOutLogo = order.toFile();

            if (!logo.isEmpty()) {
                StringBuilder orderWithLogo = new StringBuilder();
                logo.forEach(line -> {
                    orderWithLogo.append(line + "\n");
                });
                orderWithLogo.append(orderWithOutLogo);
                writer.write(orderWithLogo.toString());
            } else {
                writer.write(orderWithOutLogo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveStore(Store store) {

        try (ObjectOutputStream obs = new ObjectOutputStream(
                new FileOutputStream(STORE_PATH.toAbsolutePath().toString()));) {
            obs.writeObject(store);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Store LoadStore() {

        try (ObjectInputStream obs = new ObjectInputStream(
                new FileInputStream(STORE_PATH.toAbsolutePath().toString()))) {
            Store store = (Store) obs.readObject();
            return store;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Store(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

    }

    public static StringBuilder loadStoreLogo() {
        StringBuilder foodStoreLogo = new StringBuilder();
        String line;

        try (BufferedReader reader = new BufferedReader(new FileReader(LOGO_PATH.toAbsolutePath().toString()))) {
            while ((line = reader.readLine()) != null) {
                foodStoreLogo.append(line + "\n");
            }
            return foodStoreLogo;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void createDirectory(String directory) {
        Path dir = Paths.get(directory);
        try {
            Path createdDirectory = Files.createDirectories(dir.toAbsolutePath());
            System.out.println("Created: " + createdDirectory + " to store future data.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> readAllLines(String filename) {
        Path path = Paths.get(DIRNAME, filename);
        List<String> readAllLines;
        try {
            readAllLines = Files.readAllLines(path);
            return readAllLines;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void printLogo() {
        System.out.print(Colors.GREEN);
        logo.forEach(line -> {
            System.out.println(line);
        });
        System.out.println(Colors.RESET);
    }

    private static List<Product> productsLoader() {

        List<Product> products = new ArrayList<>();

        try (Scanner productParser = new Scanner(new File(PRODUCTS_PATH.toAbsolutePath().toString()))) {

            productParser.useDelimiter(",|\n");
            while (productParser.hasNext()) {
                int prodId = productParser.nextInt();
                String productName = productParser.next();
                double price = Double.valueOf(productParser.next());
                products.add(new Product(prodId, productName, price));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return products;
    }

    private static List<Order> ordersLoader(List<Product> storeProducts) {
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
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return orders;
    }

    private static List<Customer> customersLoader() {
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
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public static Store csvToStoreLoader() {
        List<Product> products = productsLoader();
        List<Order> orders = ordersLoader(products);
        List<Customer> customers = customersLoader();
        return new Store(products, orders, customers);

    }

    public static void storeToCsvSaver(Store store) {
        if (!Files.isDirectory(DIR_PATH)) {
            createDirectory(DIRNAME);
        }
        try {
            Files.write(CUSTOMERS_PATH, store.customersToBytes());
            Files.write(PRODUCTS_PATH, store.productsToBytes());
            Files.write(ORDERS_PATH, store.ordersToBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getLogo() {
        return logo;
    }

    public static void setLogo(List<String> logo) {
        StoreLoader.logo = logo;
    }

    public static void main(String[] args) {
        // Store store = csvToStoreLoader();
        // storeToCsvSaver(store);
    }
}