package com.dodo.Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.dodo.Models.Order;
import com.dodo.Models.Store;

public class StoreLoader {

    private static Path storePath = Paths.get("store.txt");

    private static Path logoPath = Paths.get("logo.txt");

    public static StringBuilder logo = loadStoreLogo();

    public static void WriteOrderToFile(Order order) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ORDER" + Parser.getNewOrderDate() + ".txt"))) {
            String orderWithOutLogo = order.toFile();
            if (!logo.isEmpty()) {
                String orderWithLogo = logo + "\n" + orderWithOutLogo;
                writer.write(orderWithLogo);
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

}
