package com.dodo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
//import org.hamcrest.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import com.dodo.Models.CustomerHelper;
import com.dodo.Models.Store;
import com.dodo.Utils.ListUtils;
import com.dodo.Utils.Parser;
import com.dodo.Utils.PrintUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PrintUtilsTest {
    private PrintStream defaultOutStream;
    private ByteArrayOutputStream outStream;
    private Store store;

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
    public void shouldPrintOrdersCorrectly() {
        PrintUtils.printOrders(store.getOrders());
        assertTrue(getConsoleOutput().contains("$264.32"));
    }

    @Test
    public void shouldPrintProductsCorrectly() {
        PrintUtils.printProducts(store.getProducts());
        assertTrue(getConsoleOutput().contains("Price: [37m$55.21[0m"));
    }

    @Test
    public void shouldSortPrintCustomerTotalSpentList() {
        List<CustomerHelper> cts = ListUtils.getCustomersTotalExpenditureList(store.getCustomers(), store.getOrders());
        int oldHash = cts.hashCode();
        assertEquals(oldHash, cts.hashCode());
        Collections.sort(cts);
        assertNotEquals(oldHash, cts.hashCode());
        PrintUtils.printCustomersTotalExpenditureList(cts);
        assertTrue(getConsoleOutput().contains("Tomas Brolin        $429.95"));
    }

    @Test
    public void shouldGetNewDate() {
        String date = Parser.getNewOrderDate();
        assertTrue(date instanceof String);
    }

    @Test
    public void shouldPrintMostPopularProducts() {
        assertTrue(PrintUtils.printMostPopularProducts(ListUtils.getMostPopularProducts(store.getOrders()), 5));
    }

    @Test
    public void shouldPrintMostPopularProducts2() {
        PrintUtils.printMostPopularProducts(ListUtils.getMostPopularProducts(store.getOrders()), 5);
        assertTrue(getConsoleOutput().contains("[33mPRODUCT NAME"));
    }
}
