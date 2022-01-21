package com.dodo;

import static org.junit.jupiter.api.Assertions.assertTrue;
import com.dodo.Exceptions.CustomerNotFoundException;
import com.dodo.Models.Store;
import com.dodo.StoreLogic.TakeOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TakeOrderTest {
    private Store store;

    @BeforeEach
    public void setup() {
        store = TestData.getTestStore();
    }

    @Test
    public void shouldSetCustToMartin() {
        TakeOrder.setOrderCustomer("Martin", store.getCustomers());
        assertTrue(TakeOrder.getCustomer().getName().equals("Martin Dahlin"));
    }

    @Test
    public void shouldPrintOutHelpMenu() {
        assertTrue(TakeOrder.helpMenu());
    }

    @Test
    public void shouldThrowCustomerNotFoundException() throws CustomerNotFoundException {
        TakeOrder.setOrderCustomer("93923923", store.getCustomers());

    }

}
