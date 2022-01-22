package com.dodo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.dodo.Exceptions.ProductNameException;
import com.dodo.Exceptions.ProductPriceException;
import com.dodo.Models.Product;
import com.dodo.StoreLogic.Generators;
import com.dodo.Utils.Colors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class ProductTest {
    private Product product;

    @BeforeEach
    public void setup() {
        product = new Product(1, "SomeFood", 500);
    }

    @Test
    public void ShoudBeAbleToGetAndSetFields() {
        product.setPrice(501);
        product.setProductId(99);
        assertEquals(501, product.getPrice());
        assertEquals(99, product.getProductId());
    }

    @Test
    public void shouldThrowInvalidProductNameExceptionMessage() throws ProductNameException {

        Throwable exceptionThrown = assertThrows(ProductNameException.class, () -> {
            Generators.generateProduct(1, "", "12.12");
        });

        assertEquals(Colors.RED + "Invalid product name." + Colors.RESET, exceptionThrown.getMessage());
    }

    @Test
    public void shouldThrowLowPriceExceptionMessage() throws ProductPriceException {

        Throwable exceptionThrown = assertThrows(ProductPriceException.class, () -> {
            Generators.generateProduct(1, "asasasasasassa", "4");
        });

        assertEquals(Colors.RED + "Product price is too low for our standards." + Colors.RESET,
                exceptionThrown.getMessage());
    }

    @Test
    public void shouldReturnAProduct() throws ProductNameException, ProductPriceException {

        Product aProduct = Generators.generateProduct(1, "39u2439u4", "15");
        assertEquals("39u2439u4", aProduct.getProductName());
    }

    @Test
    public void assertProperties() {
        assertThat(product, hasProperty("productName"));
        assertThat(product, hasProperty("productId"));
        assertThat(product, hasProperty("price"));
    }

    @Test
    public void shouldBeAbleToCompareTwoProducts() {
        Product newProduct = new Product(5, "productName", 501);
        assertEquals(-1, newProduct.compareTo(product));
        assertEquals(1, product.compareTo(newProduct));
        newProduct.setPrice(500);
        assertEquals(0, product.compareTo(newProduct));
    }

    @Test
    public void assertThatSwedishMeatballsIsColorYellow() {
        Product newProduct = new Product(5, "NoName", 501);
        assertFalse(newProduct.toString().contains(Colors.YELLOW));
        newProduct.setproductName("Swedish Meatballs");
        assertTrue(newProduct.toString().contains(Colors.YELLOW));

    }

}
