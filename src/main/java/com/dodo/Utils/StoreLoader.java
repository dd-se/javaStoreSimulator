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

    private static Path storePath = Paths.get("store.txt");

    private static Path logoPath = Paths.get("logo.txt");

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
                new FileOutputStream(storePath.toAbsolutePath().toString()));) {
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
                new FileInputStream(storePath.toAbsolutePath().toString()))) {
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

        try (BufferedReader reader = new BufferedReader(new FileReader(logoPath.toAbsolutePath().toString()))) {
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
            System.out.println("Created: " + createdDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> readAllLines(Path path) {
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

    public static void setLogo(List<String> logo) {
        StoreLoader.logo = logo;
    }

    public static Path getStorePath() {
        return storePath;
    }

    public static void setStorePath(Path storePath) {
        StoreLoader.storePath = storePath;
    }

    public static Path getLogoPath() {
        return logoPath;
    }

    public static void setLogoPath(Path logoPath) {
        StoreLoader.logoPath = logoPath;
    }

    public static Store csvToStoreLoader() {
        Store store = new Store(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        try (Scanner productParser = new Scanner(new File("products.txt"))) {
            productParser.useDelimiter(",|\n");

            while (productParser.hasNext()) {
                int prodId = productParser.nextInt();
                String productName = productParser.next();
                double price = Double.valueOf(productParser.next());

                store.getProducts().add(new Product(prodId, productName, price));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {

            try (Scanner orderParser = new Scanner(new File("orders.txt"))) {
                orderParser.useDelimiter(",|\n");

                while (orderParser.hasNext()) {
                    int orderId = orderParser.nextInt();
                    int custId = orderParser.nextInt();

                    String[] productIds = orderParser.next().split("-");
                    List<Product> productList = new ArrayList<>();
                    for (String id : productIds) {
                        productList.add(store.getProducts().get(Integer.parseInt(id) - 1));
                    }

                    String orderDate = orderParser.next();
                    store.getOrders().add(new Order(orderId, custId, productList, orderDate));
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {

                try (Scanner customerParser = new Scanner(new File("customers.txt"))) {
                    customerParser.useDelimiter(",|\n");

                    while (customerParser.hasNext()) {
                        int custId = customerParser.nextInt();
                        String customerName = ListUtils.convertToTitleCase(customerParser.next());
                        String customerAddress = ListUtils.convertToTitleCase(customerParser.next());
                        String customerEmail = customerParser.next().toLowerCase();
                        store.getCustomers().add(new Customer(custId, customerName, customerAddress, customerEmail));
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return store;
    }

    public static void storeToCsvSaver(Store store) {
        Path customerPath = Paths.get("customers.txt");
        Path orderPath = Paths.get("orders.txt");
        Path productPath = Paths.get("products.txt");
        try {
            Files.write(customerPath, store.customersToBytes());
            Files.write(productPath, store.productsToBytes());
            Files.write(orderPath, store.ordersToBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Store store = csvToStoreLoader();
        storeToCsvSaver(store);
    }
}