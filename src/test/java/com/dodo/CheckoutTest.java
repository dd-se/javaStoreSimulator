package com.dodo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.dodo.Exceptions.CustomerNameException;
import com.dodo.Models.Store;
import com.dodo.StoreLogic.Checkout;
import com.dodo.StoreLogic.TakeOrder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheckoutTest {
    private Store store;

    @BeforeEach
    public void beforeEachTest() {
        store = TestData.getTestStore();
    }

    @Test
    public void shouldbeAbleToCheckOut() throws CustomerNameException {
        TakeOrder.setOrderCustomer("Martin", store.getCustomers());
        assertTrue(Checkout.checkout(store.getProducts(), store.getOrders(), false));
    }

    @Test
    public void shouldNotBeAbleToCheckOutIfOrderIsEmpty() throws CustomerNameException {
        TakeOrder.setOrderCustomer("Martin", store.getCustomers());
        store.getProducts().clear();
        assertFalse(Checkout.checkout(store.getProducts(), store.getOrders(), false));
    }
}
