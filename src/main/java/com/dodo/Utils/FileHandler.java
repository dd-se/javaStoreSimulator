package com.dodo.Utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.dodo.Models.Order;

public class FileHandler {

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

    public static void createDirectory(String directory) {
        Path dir = Paths.get(directory);
        try {
            Path createdDirectory = Files.createDirectories(dir.toAbsolutePath());
            System.out.println("Created: " + createdDirectory + " to store future data.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> readAllLines(String filename) throws IOException {
        Path path = Paths.get(DIRNAME, filename);
        List<String> readAllLines;
        readAllLines = Files.readAllLines(path);
        return readAllLines;

    }

    public static void WriteOrderToFile(Order order) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ORDER" + Parser.getNewOrderDate() + ".txt"))) {
            String orderWithOutLogo = order.toFile();

            if (StoreLoader.logo != null) {

                StringBuilder orderWithLogo = new StringBuilder();
                StoreLoader.logo.forEach(line -> {
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

}
