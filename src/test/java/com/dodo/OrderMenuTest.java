package com.dodo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import com.dodo.Menus.OrderMenu;
import com.dodo.Models.Order;
import com.dodo.Models.Store;
import com.dodo.StoreLogic.TakeOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderMenuTest {
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
    public void shouldAddSomeFood4toOrder() {
        inputData = "1\nTomas\nadd 1\nABort\nb";
        bais = new ByteArrayInputStream(inputData.getBytes());
        App.scanner = new Scanner(bais);
        OrderMenu.orderMenu(store);
        assertTrue(getConsoleOutput().contains("SomeFood4"));
    }

    @Test
    public void shouldAddRandomProductToProductList() {
        inputData = "1\nTomas\nrandom\nABorT\nb";
        bais = new ByteArrayInputStream(inputData.getBytes());
        App.scanner = new Scanner(bais);
        OrderMenu.orderMenu(store);
        // Added a product that starts with SomeFood<int>
        assertTrue(getConsoleOutput().contains("Added SomeFood"));
        assertTrue(getConsoleOutput().contains("Customer set to: Tomas Brolin"));
    }

    @Test
    public void shouldSetCustomerToTomas() {
        inputData = "1\nTomas\nABORT\nb";
        bais = new ByteArrayInputStream(inputData.getBytes());
        App.scanner = new Scanner(bais);
        OrderMenu.orderMenu(store);
        assertEquals("Tomas Brolin", TakeOrder.getCustomer().getName());
    }

    @Test
    public void shouldSortByTotalOrderCost() {
        inputData = "2\nb";
        bais = new ByteArrayInputStream(inputData.getBytes());
        App.scanner = new Scanner(bais);
        int oldHash = store.getOrders().hashCode();
        Order oldOrderAtIndex0 = store.getOrders().get(0);
        OrderMenu.orderMenu(store);
        Order newOrderAtIndex0 = store.getOrders().get(0);
        int sortedHash = store.getOrders().hashCode();
        assertNotEquals(oldHash, sortedHash);
        assertNotEquals(oldOrderAtIndex0, newOrderAtIndex0);
        assertTrue(newOrderAtIndex0.totalOrderCost() > oldOrderAtIndex0.totalOrderCost());
    }

    @Test
    public void shouldSortOrderList() {
        inputData = "3\nb";
        bais = new ByteArrayInputStream(inputData.getBytes());
        App.scanner = new Scanner(bais);
        int oldHash = store.getOrders().hashCode();
        Order oldOrderAtIndex0 = store.getOrders().get(0);
        OrderMenu.orderMenu(store);
        int newHash = store.getOrders().hashCode();
        Order newOrderAtIndex0 = store.getOrders().get(0);
        assertNotEquals(oldHash, newHash);
        assertNotEquals(oldOrderAtIndex0, newOrderAtIndex0);
        assertTrue(getConsoleOutput().contains("2022/01/13"));

    }

    @Test
    public void shouldFindOrderById() {
        inputData = "4\n2\nb";
        bais = new ByteArrayInputStream(inputData.getBytes());
        App.scanner = new Scanner(bais);
        OrderMenu.orderMenu(store);
        assertTrue(getConsoleOutput().contains("Order ID: [0m2"));
    }

    @Test
    public void shouldEnterOrderHandlerAndAddRemoveAProduct() {
        inputData = "1\nTomas\nadd 6\nremove SomefOoD\nABOrT";
        bais = new ByteArrayInputStream(inputData.getBytes());
        App.scanner = new Scanner(bais);
        OrderMenu.takeOrderHandler(store.getProducts(), store.getOrders());
        assertTrue(getConsoleOutput().contains("Removed SomeFood6"));
    }

    @Test
    public void shouldEnterOrderHandlerAndThrowNumberFormatException() {
        inputData = "add 1,\nABOrT";
        bais = new ByteArrayInputStream(inputData.getBytes());
        App.scanner = new Scanner(bais);
        OrderMenu.takeOrderHandler(store.getProducts(), store.getOrders());
        assertTrue(getConsoleOutput().contains("Not an integer."));
    }

    @Test
    public void shouldEnterOrderHandlerAndThrowProductNotFoundException() {
        inputData = "add 128\nABOrT";
        bais = new ByteArrayInputStream(inputData.getBytes());
        App.scanner = new Scanner(bais);
        OrderMenu.takeOrderHandler(store.getProducts(), store.getOrders());
        assertTrue(getConsoleOutput().contains("A product with that name could not be found."));
    }

    @Test
    public void shouldEnterOrderHandlerAndFailCheckoutBecauseProductListIsEmpty() {
        TakeOrder.setCustomer(store.getCustomers().get(0));
        inputData = "CHECKouT\nAbort";
        bais = new ByteArrayInputStream(inputData.getBytes());
        App.scanner = new Scanner(bais);
        OrderMenu.takeOrderHandler(store.getProducts(), store.getOrders());
        assertTrue(getConsoleOutput().contains("You can't checkout, because your basket is empty."));
    }

    @Test
    public void shouldEnterOrderHandlerAndPrintOutInvalidCommand() {
        inputData = "?sAS\nAbort";
        bais = new ByteArrayInputStream(inputData.getBytes());
        App.scanner = new Scanner(bais);
        OrderMenu.takeOrderHandler(store.getProducts(), store.getOrders());
        assertTrue(getConsoleOutput().contains("Invalid command."));
    }

    @Test
    public void shouldEnterOrderMenuAndThrowNumberFormatException2() {
        inputData = "4\n123s\nb";
        bais = new ByteArrayInputStream(inputData.getBytes());
        App.scanner = new Scanner(bais);
        OrderMenu.orderMenu(store);
        assertTrue(getConsoleOutput().contains("Not an integer."));

    }

    @Test
    public void shouldEnterOrderMenuAndThrowOrderNotFoundException() {
        inputData = "4\n9999\nb";
        bais = new ByteArrayInputStream(inputData.getBytes());
        App.scanner = new Scanner(bais);
        OrderMenu.orderMenu(store);
        assertTrue(getConsoleOutput().contains("Order with that ID does not exist."));

    }

    @Test
    public void shouldEnterOrderMenuAndPrintOutInvalidInput() {
        inputData = "9999\nb";
        bais = new ByteArrayInputStream(inputData.getBytes());
        App.scanner = new Scanner(bais);
        OrderMenu.orderMenu(store);
        assertTrue(getConsoleOutput().contains("Invalid input."));

    }
}
