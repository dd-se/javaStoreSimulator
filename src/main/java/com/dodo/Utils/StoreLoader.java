package com.dodo.Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.dodo.Models.Store;

public class StoreLoader {

    public static final String STORE = "store.bin";

    public static final Path STORE_PATH = Paths.get(FileHandler.DIRNAME, STORE);

    public static List<String> logo;

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

    public static void loadStoreLogo() {
        try {
            logo = FileHandler.readAllLines("logo.txt");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            logo = null;
        }
    }

    public static List<String> getLogo() {
        return logo;
    }

    public static void setLogo(List<String> logo) {
        StoreLoader.logo = logo;
    }
}