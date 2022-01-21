package com.dodo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.dodo.Models.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class CustomerTest {
    private Customer customer;

    @BeforeEach
    public void beforeEachTest() {
        customer = new Customer(1, "someName", "someaddress", "some@mail.com");
    }

    @Test
    public void shouldAnswerWithTrue() {
        assertEquals(Customer.class, customer.getClass());
        assertEquals("someName", customer.getName());
        assertEquals(1, customer.getCustId());
    }

    @Test
    public void assertToStringMethod() {
        assertEquals(
                "Customer ID: 1 Customer Name: someName Customer Email: some@mail.com Customer Address: someaddress",
                customer.toString());
    }

    @Test
    public void assertProperties() {
        assertThat(customer, hasProperty("name"));
        assertThat(customer, hasProperty("address"));
        assertThat(customer, hasProperty("email"));

    }

    @Test
    public void assertGetterSetterMethods() {
        customer.setName("Martin Dahlin");
        assertEquals("Martin Dahlin", customer.getName());
        customer.setName("Martin Dahlin_");
        assertNotEquals("Martin Dahlin", customer.getName());
        assertEquals(1, customer.getCustId());
        customer.setCustId(2);
        assertNotEquals(1, customer.getCustId());
    }
}
