package com.dodo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import java.util.ArrayList;
import com.dodo.Models.Product;
import com.dodo.Models.Store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StoreTest {
    private Store store;

    @BeforeEach
    public void beforeEachTest() {
        store = TestData.getTestStore();
    }

    @Test
    public void shouldPassWithFlyingColors() {
        assertEquals(Store.class, store.getClass());
    }

    @Test
    public void shouldPassProductAddTest() {
        Product product = new Product(1, "somefood", 99);
        store.getProducts().add(product);
        assertThat(store.getProducts(), hasItem(product));
    }

    @Test
    public void shouldPassConstructorTest() {
        assertEquals(Store.class, new Store(new ArrayList<>(), new ArrayList<>(), new ArrayList<>()).getClass());
    }

    @Test
    public void assertThatNewTestDataIsNotEqual() {
        Store newStore = TestData.getTestStore();
        assertThat(newStore, not(equalTo(store)));
    }

    @Test
    public void assertProperties() {
        assertThat(store, hasProperty("products"));
        assertThat(store, hasProperty("orders"));
        assertThat(store, hasProperty("customers"));
    }

}
