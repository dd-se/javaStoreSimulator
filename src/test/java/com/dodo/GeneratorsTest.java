package com.dodo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.dodo.Exceptions.CustomerAddressException;
import com.dodo.Exceptions.CustomerEmailException;
import com.dodo.Exceptions.CustomerNameException;
import com.dodo.Models.Customer;
import com.dodo.StoreLogic.Generators;
import com.dodo.Utils.Colors;

import org.junit.jupiter.api.Test;

public class GeneratorsTest {

    @Test
    public void shouldThrowCustomerNameException() {
        Throwable exceptionThrown = assertThrows(CustomerNameException.class, () -> {
            Generators.generateCustomer("", "asasasasasasas", "asasasasa@mail.com",
                    1);
        });

        assertEquals(exceptionThrown.getMessage(), Colors.RED + "Your chosen name is invalid." + Colors.RESET);
    }

    @Test
    public void shouldThrowCustomerEmailException() {
        Throwable exceptionThrown = assertThrows(CustomerEmailException.class, () -> {
            Generators.generateCustomer("asasasas aasas", "saasasasasasasas", "asasasas_gmail.com",
                    1);
        });

        assertEquals(exceptionThrown.getMessage(), Colors.RED + "Your chosen email is invalid." + Colors.RESET);
    }

    @Test
    public void shouldThrowCustomerAddressException() {
        Throwable exceptionThrown = assertThrows(CustomerAddressException.class, () -> {
            Generators.generateCustomer("asasasas asasasas", "", "asasasas@gmail.com",
                    1);
        });

        assertEquals(exceptionThrown.getMessage(), Colors.RED + "Your chosen address is invalid." + Colors.RESET);
    }

    @Test
    public void shouldReturnACustomer() throws CustomerNameException, CustomerEmailException, CustomerAddressException {
        Customer aCustomer = Generators.generateCustomer("melvin karlsson", "melvinstreet 1", "melvin@gmail.com",
                1);
        assertEquals(Customer.class, aCustomer.getClass());
        assertEquals("Melvin Karlsson", aCustomer.getName());
    }

}
