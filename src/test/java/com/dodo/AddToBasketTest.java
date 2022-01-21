package com.dodo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import com.dodo.Exceptions.ProductNotFoundException;
import com.dodo.Models.Product;
import com.dodo.Models.Store;
import com.dodo.StoreLogic.AddToBasket;
import com.dodo.StoreLogic.TakeOrder;
import com.dodo.Utils.Colors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddToBasketTest {
    private Store store;
    private List<Product> pl;

    @BeforeEach
    public void setup() {
        store = TestData.getTestStore();
        pl = new ArrayList<>();
    }

    @Test
    public void shouldAddExpectedProduct() throws ProductNotFoundException {

        TakeOrder.setOrderCustomer("Martin", store.getCustomers());
        assertTrue(AddToBasket.addToBasket(4, pl, store.getProducts()));
        Product product = pl.get(0);
        assertEquals("SomeFood4", product.getProductName());
        assertTrue(AddToBasket.addToBasket(5, pl, store.getProducts()));
        product = pl.get(1);
        assertEquals("SomeFood5", product.getProductName());
    }

    @Test
    public void shouldThrowException() throws ProductNotFoundException {

        Throwable exceptionThrown = assertThrows(ProductNotFoundException.class, () -> {
            AddToBasket.addToBasket(99192, pl, store.getProducts());
        });

        assertEquals(Colors.RED + "A product with that name could not be found." + Colors.RESET,
                exceptionThrown.getMessage());
    }
}
