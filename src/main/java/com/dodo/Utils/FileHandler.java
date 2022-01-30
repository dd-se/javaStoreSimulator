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

    public static final String DIRNAME = CsvHandler.DIRNAME;

    public static Path createDirectory(String directory) throws IOException {
        Path dir = Paths.get(directory);
        Path createdDirectory = Files.createDirectories(dir.toAbsolutePath());
        return createdDirectory;

    }

    public static List<String> readAllLines(String filename) throws IOException {
        Path path = Paths.get(DIRNAME, filename);
        return Files.readAllLines(path);

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
