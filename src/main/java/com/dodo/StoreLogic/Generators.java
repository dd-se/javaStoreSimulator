package com.dodo.StoreLogic;

import java.util.List;

import com.dodo.Exceptions.CustomerAddressException;
import com.dodo.Exceptions.CustomerEmailException;
import com.dodo.Exceptions.CustomerNameException;
import com.dodo.Exceptions.ProductNameException;
import com.dodo.Exceptions.ProductPriceException;
import com.dodo.Models.Customer;
import com.dodo.Models.Order;
import com.dodo.Models.Product;
import com.dodo.Utils.ListUtils;
import com.dodo.Utils.Parser;

public class Generators {

    public static Product generateProduct(int productId, String productName, String productPrice)
            throws NumberFormatException, ProductNameException, ProductPriceException {

        String name = ListUtils.convertToTitleCase(productName);
        double price = Parser.parseDouble(productPrice);

        if (name.isEmpty() || name == null || name.strip().length() < 5) {
            throw new ProductNameException();
        } else if (price < 5) {
            throw new ProductPriceException();
        }
        return new Product(productId, name, price);
    }

    public static Order generateOrder(int orderId, int customerId, List<Product> productList) {
        return new Order(orderId, customerId, productList, Parser.getDate());

    }

    public static Customer generateCustomer(String customerName, String customerAddress, String customerEmail,
            int custId) throws CustomerNameException, CustomerEmailException, CustomerAddressException {
        String name = ListUtils.convertToTitleCase(customerName);
        String address = ListUtils.convertToTitleCase(customerAddress);
        String email = customerEmail.toLowerCase();

        if (name.isEmpty() || name == null || name.strip().length() < 5 || name.split(" ").length < 2) {
            throw new CustomerNameException("Your chosen name is invalid.");

        } else if (email.isEmpty() || email == null || email.strip().length() < 10 || !email.contains("@")) {
            throw new CustomerEmailException("Your chosen email is invalid.");

        } else if (address.isEmpty() || address == null || address.strip().length() < 10) {
            throw new CustomerAddressException("Your chosen address is invalid.");
        }

        return new Customer(custId, name, address, email);
    }
}
