package com.dodo.Models;

import java.io.Serializable;
import java.util.List;

public class Store implements Serializable {
    private static final long serialVersionUID = 121212L;
    private List<Product> products;
    private List<Order> orders;
    private List<Customer> customers;

    public Store(List<Product> products, List<Order> orders, List<Customer> customers) {
        this.products = products;
        this.orders = orders;
        this.customers = customers;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

}
