package com.dodo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.dodo.Exceptions.CustomerNotFoundException;
import com.dodo.Exceptions.OrderNotFoundException;
import com.dodo.Models.Customer;
import com.dodo.Models.CustomerHelper;
import com.dodo.Models.Order;
import com.dodo.Models.Product;
import com.dodo.Models.Store;
import com.dodo.Utils.Colors;
import com.dodo.Utils.ListUtils;
import com.dodo.Utils.Parser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ListUtilsTest {
    private Store store;
    private PrintStream defaultOutStream;
    private ByteArrayOutputStream outStream;

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

    @Test
    public void shouldConvertToTitleText() {
        assertEquals("Henke", ListUtils.convertToTitleCase("henke"));
        assertEquals("Henke Henke", ListUtils.convertToTitleCase("henke hEnKe"));
    }

    @Test
    public void shouldReturnTotalSpentCorrectly() {
        List<CustomerHelper> cts = ListUtils.getCustomersTotalExpenditureList(store.getCustomers(), store.getOrders());
        assertEquals(44.11 + 55.21 + 66.31 + 77.32 + 88 + 99, cts.get(1).getTotalSpent());
        assertEquals(44.11 + 55.21 + 66.31, cts.get(0).getTotalSpent());
    }

    @Test
    public void shouldReturnTotalCostOfAnOrder() {
        assertEquals(44.11 + 55.21 + 66.31 + 77.32 + 88 + 99, ListUtils.getOrderTotalCost(1, store.getOrders()));
    }

    @Test
    public void shouldReturnARandomProductOrOrder() {
        assertEquals(Product.class, ListUtils.pickRandomFromCollection(store.getProducts()).getClass());
        assertEquals(Order.class, ListUtils.pickRandomFromCollection(store.getOrders()).getClass());
    }

    @Test
    public void shouldGetNewDate() {
        assertTrue(Parser.getNewOrderDate() instanceof String);
    }

    @Test
    public void shouldReturnIntOrThrowException() throws NumberFormatException {

        Throwable exceptionThrown = assertThrows(NumberFormatException.class, () -> {
            Parser.parseInt("dsds");
        });

        assertEquals("For input string: \"dsds\"", exceptionThrown.getMessage());

        assertEquals(84823, Parser.parseInt("84823"));
    }

    @Test
    public void shouldReturnDoubleorThrowException() throws NumberFormatException {

        Throwable exceptionThrown = assertThrows(NumberFormatException.class, () -> {
            Parser.parseDouble("asas");
        });

        assertEquals("For input string: \"asas\"", exceptionThrown.getMessage());

        assertEquals(84823.4343, Parser.parseDouble("84823.4343"));
    }

    @Test
    public void shouldThrowOrderNotFoundException() throws OrderNotFoundException {

        Throwable exceptionThrown = assertThrows(OrderNotFoundException.class, () -> {
            ListUtils.findOrderById(store.getOrders(), 99);
        });

        assertEquals(Colors.RED + "Order with that ID does not exist." + Colors.RESET, exceptionThrown.getMessage());
    }

    @Test
    public void shouldFindOrderByID() throws OrderNotFoundException {
        assertEquals(Order.class, ListUtils.findOrderById(store.getOrders(), 1).getClass());
        assertEquals("2022/01/13 18:00:19", ListUtils.findOrderById(store.getOrders(), 2).getOrderDate());
    }

    @Test
    public void shouldFindByName() throws CustomerNotFoundException {
        Customer martin = ListUtils.findCustByName("martin", store.getCustomers());
        assertEquals(martin, ListUtils.findCustByName("martin", store.getCustomers()));
        assertEquals("Martin Dahlin", martin.getName());
    }

    @Test
    public void shouldThrowCustomerNameException() {
        Throwable exceptionThrown = assertThrows(CustomerNotFoundException.class, () -> {
            ListUtils.findCustByName("4235435345435345", store.getCustomers());
        });

        Throwable exceptionThrown2 = assertThrows(CustomerNotFoundException.class, () -> {
            ListUtils.findCustByName("", store.getCustomers());
        });

        assertEquals(exceptionThrown.getMessage(), Colors.RED + "No customer with that name." + Colors.RESET);

        assertEquals(exceptionThrown2.getMessage(), Colors.RED + "No customer with that name." + Colors.RESET);
    }

    @Test
    public void shouldSortCollectionByNewestFirst() {
        int oldhash = store.getOrders().hashCode();
        assertEquals("2022/01/12 18:00:19", store.getOrders().get(0).getOrderDate());
        ListUtils.sortOrdersByNewestFirst(store.getOrders());
        assertNotEquals(oldhash, store.getOrders().hashCode());
        assertEquals("2022/01/13 18:00:19", store.getOrders().get(0).getOrderDate());
    }

    @Test
    public void shouldSortOrderOldestFirst() {
        int oldhash = store.getOrders().hashCode();
        assertEquals("2022/01/12 18:00:19", store.getOrders().get(0).getOrderDate());
        ListUtils.sortOrdersByOldestFirst(store.getOrders());
        assertNotEquals(oldhash, store.getOrders().hashCode());
        assertEquals("2022/01/11 18:00:19", store.getOrders().get(0).getOrderDate());
    }

    @Test
    public void shouldSortCustomersTotalSpentListBiggestSpenderFirst() {
        List<CustomerHelper> customersTotalSpentList = ListUtils.getCustomersTotalExpenditureList(store.getCustomers(),
                store.getOrders());
        ListUtils.sortCustomersTotalExpenditureList(customersTotalSpentList);
        assertEquals("Tomas Brolin (⌐■_■)", customersTotalSpentList.get(0).getName());
    }
}
