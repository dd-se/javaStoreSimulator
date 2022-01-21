package com.dodo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import com.dodo.Menus.ProdMenu;
import com.dodo.Models.Product;
import com.dodo.Models.Store;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProdMenuTest {
    private Store store;
    private String inputData;
    private PrintStream defaultOutStream;
    private ByteArrayOutputStream outStream;
    private ByteArrayInputStream bais;

    @BeforeEach
    public void setup() {
        outStream = new ByteArrayOutputStream();
        defaultOutStream = System.out;
        System.setOut(new PrintStream(outStream));
        store = TestData.getTestStore();
    }

    @AfterEach
    public void aftereachtest() {
        System.setOut(defaultOutStream);
    }

    private String getConsoleOutput() {
        return outStream.toString();
    }

    @Test
    public void shouldPrintStoreProducts() {
        inputData = "1\nb";
        bais = new ByteArrayInputStream(inputData.getBytes());
        App.scanner = new Scanner(bais);
        ProdMenu.productMenu(store.getProducts(), store.getOrders());
        assertTrue(getConsoleOutput().contains("SomeFood6"));
        assertFalse(getConsoleOutput().contains("SomeFood9999"));
    }

    @Test
    public void shouldCreateAndAddMyProductToStoreProductList() {
        inputData = "2\nFre47230402834\n15.1513\nb";
        bais = new ByteArrayInputStream(inputData.getBytes());
        App.scanner = new Scanner(bais);
        ProdMenu.productMenu(store.getProducts(), store.getOrders());
        Product product = store.getProducts().get(store.getProducts().size() - 1);
        assertEquals("Fre47230402834", product.getProductName());
        assertEquals(15.1513, product.getPrice());
        assertTrue(getConsoleOutput().contains("Fre47230402834"));
        assertFalse(getConsoleOutput().contains("SomeFood6"));
    }

    @Test
    public void shouldEnterProductMenuAndThrowNumberFormatException() {
        inputData = "2\nFreshhhhhhhhhhh\n11S3\nb";
        bais = new ByteArrayInputStream(inputData.getBytes());
        App.scanner = new Scanner(bais);
        ProdMenu.productMenu(store.getProducts(), store.getOrders());
        Product product = store.getProducts().get(store.getProducts().size() - 1);
        assertNotEquals("Freshhhhhhhhhhh", product.getProductName());
        assertNotEquals(15.1513, product.getPrice());
        assertTrue(getConsoleOutput().contains("Invalid price input."));
    }

    @Test
    public void shouldEnterProductMenuAndThrowProductNameException() {
        inputData = "2\nFres\n15.1513\nb";
        bais = new ByteArrayInputStream(inputData.getBytes());
        App.scanner = new Scanner(bais);
        ProdMenu.productMenu(store.getProducts(), store.getOrders());
        Product product = store.getProducts().get(store.getProducts().size() - 1);
        assertNotEquals("Fres", product.getProductName());
        assertNotEquals(15.1513, product.getPrice());
        assertTrue(getConsoleOutput().contains("Invalid product name."));
    }

    @Test
    public void shouldEnterProductMenuAndPrintMostPopularProducts() {
        inputData = "3\nb";
        bais = new ByteArrayInputStream(inputData.getBytes());
        App.scanner = new Scanner(bais);
        ProdMenu.productMenu(store.getProducts(), store.getOrders());
        assertTrue(getConsoleOutput().contains("+-----------------------+"));
    }

    @Test
    public void shouldEnterProductMenuAndThrowProductPriceException() {
        inputData = "2\nFreshhh\n4\nb";
        bais = new ByteArrayInputStream(inputData.getBytes());
        App.scanner = new Scanner(bais);
        ProdMenu.productMenu(store.getProducts(), store.getOrders());
        Product product = store.getProducts().get(store.getProducts().size() - 1);
        assertNotEquals("Freshhh", product.getProductName());
        assertNotEquals(4, product.getPrice());
        assertTrue(getConsoleOutput().contains("Product price is too low for our standards."));
    }

    @Test
    public void shouldPrintOutInvalidInput() {
        inputData = "9999\n\nb";
        bais = new ByteArrayInputStream(inputData.getBytes());
        App.scanner = new Scanner(bais);
        ProdMenu.productMenu(store.getProducts(), store.getOrders());
        assertTrue(getConsoleOutput().contains("Invalid input."));

    }
}
