package com.dodo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import com.dodo.Menus.CustMenu;
import com.dodo.Models.Store;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CustMenuTest {
    private Store store;
    private String inputData;
    private PrintStream defaultOutStream;
    private ByteArrayOutputStream outStream;
    private ByteArrayInputStream bais;

    @BeforeEach
    public void beforeEachTest() {
        outStream = new ByteArrayOutputStream();
        defaultOutStream = System.out;
        System.setOut(new PrintStream(outStream));
        store = TestData.getTestStore();
    }

    @AfterEach
    public void afterEachTest() {
        System.setOut(defaultOutStream);
    }

    private String getConsoleOutput() {
        return outStream.toString();
    }

    @Test
    public void shouldCreateACustomer() {
        inputData = "1\nhenRik lArsson\nhenkegatan 12\nhenke@gmail.com\nb";
        bais = new ByteArrayInputStream(inputData.getBytes());
        App.scanner = new Scanner(bais);
        CustMenu.customerMenu(store.getCustomers(), store.getOrders());
        assertTrue(getConsoleOutput().contains("added"));
        assertEquals("Henrik Larsson", store.getCustomers().get(store.getCustomers().size() - 1).getName());
    }

    @Test
    public void shouldThrowCustomerEmailException() {
        inputData = "1\nhenRik lArsson\nhenkegatan 12\nhenke_gmail.com\nb";
        bais = new ByteArrayInputStream(inputData.getBytes());
        App.scanner = new Scanner(bais);
        CustMenu.customerMenu(store.getCustomers(), store.getOrders());
        assertTrue(getConsoleOutput().contains("Your chosen email is invalid."));
    }

    @Test
    public void shouldThrowCustomerNameException() {
        inputData = "1\nhenRiklArsson\nhenkegatan 12\nhenke@gmail.com\nb";
        bais = new ByteArrayInputStream(inputData.getBytes());
        App.scanner = new Scanner(bais);
        CustMenu.customerMenu(store.getCustomers(), store.getOrders());
        assertTrue(getConsoleOutput().contains("Your chosen name is invalid."));
    }

    @Test
    public void shouldThrowCustomerAddressException() {
        inputData = "1\nhenRik lArsson\ngata\nhenke@gmail.com\nb";
        bais = new ByteArrayInputStream(inputData.getBytes());
        App.scanner = new Scanner(bais);
        CustMenu.customerMenu(store.getCustomers(), store.getOrders());
        assertTrue(getConsoleOutput().contains("Your chosen address is invalid."));
    }

    @Test
    public void shouldPrintOutSpendingList() {
        inputData = "2\nb";
        bais = new ByteArrayInputStream(inputData.getBytes());
        App.scanner = new Scanner(bais);
        CustMenu.customerMenu(store.getCustomers(), store.getOrders());
        assertTrue(getConsoleOutput().contains("Tomas Brolin (⌐■_■) $429.95"));
    }

    @Test
    public void shouldPrintOutInvalidInput() {
        inputData = "9999\nb";
        bais = new ByteArrayInputStream(inputData.getBytes());
        App.scanner = new Scanner(bais);
        CustMenu.customerMenu(store.getCustomers(), store.getOrders());
        assertTrue(getConsoleOutput().contains("Invalid input."));
    }
}
