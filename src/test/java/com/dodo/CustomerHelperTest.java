package com.dodo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import com.dodo.Models.CustomerHelper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class CustomerHelperTest {
    private CustomerHelper ch;

    @BeforeEach
    public void beforeEachTest() {
        ch = new CustomerHelper(4, "someName", 3334);

    }

    @Test
    public void shouldAnswerWithTrue() {
        assertEquals(CustomerHelper.class, ch.getClass());
        assertEquals("someName", ch.getName());
        assertEquals(3334, ch.getTotalSpent());
    }

    @Test
    public void shouldBeAbleToChangeFields() {
        ch.setTotalSpent(1000.50445);
        ch.setName("changedName");
        assertEquals(1000.50445, ch.getTotalSpent());
        assertEquals("changedName", ch.getName());
    }

    @Test
    public void shouldBeAbleToCompareToObjects() {
        CustomerHelper ch2 = new CustomerHelper(4, "name33", 3334);
        assertEquals(0, ch.compareTo(ch2));
        ch2.setTotalSpent(3335);
        assertEquals(-1, ch2.compareTo(ch));
        assertEquals(1, ch.compareTo(ch2));
    }

    @Test
    public void assertProperties() {
        assertThat(ch, hasProperty("name"));
        assertThat(ch, hasProperty("custId"));
        assertThat(ch, hasProperty("totalSpent"));
    }
}
