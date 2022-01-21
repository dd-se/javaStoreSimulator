package com.dodo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.dodo.Exceptions.ProductNotFoundException;
import com.dodo.Models.Store;
import com.dodo.StoreLogic.RemoveFromBasket;
import com.dodo.Utils.Colors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RemoveFromBasketTest {
    private Store store;

    @BeforeEach
    public void setup() {
        store = TestData.getTestStore();
    }

    @Test
    public void shouldRemoveItemFromList() throws ProductNotFoundException {
        assertTrue(RemoveFromBasket.removeFromBasket(store.getProducts(), "SomeFood5"));
    }

    @Test
    public void shouldThrowExceptionIfItemIsNotInTheList() throws ProductNotFoundException {
        Throwable exceptionThrown = assertThrows(ProductNotFoundException.class, () -> {
            RemoveFromBasket.removeFromBasket(store.getProducts(), "SomeFood9139239");
        });

        assertEquals(Colors.RED + "A product with that name could not be found." + Colors.RESET,
                exceptionThrown.getMessage());
    }

}
