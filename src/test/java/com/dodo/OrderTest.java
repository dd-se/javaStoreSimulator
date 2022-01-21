package com.dodo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.dodo.Models.Order;
import com.dodo.Models.Product;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

/**
 * Unit test for simple App.
 */
public class OrderTest {
    private Order order;

    @BeforeEach
    public void setup() {
        List<Product> productList = new ArrayList<>(Arrays.asList(
                new Product(1, "SomeFood", 99),
                new Product(2, "SomeFood2", 100)));
        order = new Order(1, 2, productList, "2022/01/14 18:00:19");
    }

    @Test
    public void shouldPassGetterMethods() {
        assertEquals(2, order.getProductList().size());
        assertEquals(2, order.getCustId());
        assertEquals(1, order.getOrderId());
        Product product = order.getProductList().get(0);
        assertEquals("SomeFood", product.getProductName());
        assertEquals(99, product.getPrice());
        assertEquals("2022/01/14 18:00:19", order.getOrderDate());
        order.setOrderDate("2022/01/15 18:00:19");
        assertEquals("2022/01/15 18:00:19", order.getOrderDate());
    }

    @Test
    public void shouldBeAbleToCompareTwoOrders() {
        List<Product> productList = new ArrayList<>(Arrays.asList(
                new Product(1, "SomeFood", 99),
                new Product(2, "SomeFood2", 100)));
        Order order2 = new Order(2, 1, productList, "2040/12/12 12:12:12");

        assertEquals(0, order2.compareTo(order));
        order2.getProductList().get(0).setPrice(101);
        assertEquals(1, order.compareTo(order2));
        assertEquals(-1, order2.compareTo(order));
    }

    @Test
    public void shouldBeAbleToSetProductList() {
        List<Product> productList2 = new ArrayList<>();
        productList2.add(new Product(1, "SomeFood", 101));
        assertEquals(1, order.setProductList(productList2));
        assertEquals(1, order.getProductList().size());
    }

    @Test
    public void shouldBeAbleToGetList() {
        assertEquals(ArrayList.class, order.getProductList().getClass());
    }

    @Test
    public void shouldBeAbleToPrintOrder() {
        assertThat(order.toString(), hasToString(equalTo(
                "Order [custId=2, orderDate=2022/01/14 18:00:19, orderId=1, productList="
                        + "[[36mProduct ID: [37m1[36m       Product name: [37mSomeFood[36m         "
                        + "         Price: [37m$99.00[0m             , [36mProduct ID: [37m2[36m    "
                        + "   Product name: [37mSomeFood2[36m                 Price: [37m$100.00[0m            ]]")));
    }

    @Test
    public void shouldBeAbleToGetTotalCostOfAnOrder() {
        assertEquals(199, order.totalOrderCost());
    }

    @Test
    public void shouldBeAbleToCallToFileMethod() {
        assertTrue(order.toFile().contains("TOTAL ORDER COST: $199.00"));
    }

    @Test
    public void assertProperties() {
        assertThat(order, hasProperty("productList"));
        assertThat(order, hasProperty("custId"));
        assertThat(order, hasProperty("orderId"));
        assertThat(order, hasProperty("orderDate"));
    }
}
