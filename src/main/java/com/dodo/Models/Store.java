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

    public byte[] productsToBytes() {
        StringBuilder products = new StringBuilder();
        this.products.forEach(product -> {
            products.append(product.toCsv());
        });
        return products.toString().getBytes();

    }

    public byte[] ordersToBytes() {
        StringBuilder orders = new StringBuilder();
        this.orders.forEach(order -> {
            orders.append(order.toCsv());
        });
        return orders.toString().getBytes();
    }

    public byte[] customersToBytes() {
        StringBuilder customers = new StringBuilder();
        this.customers.forEach(customer -> {
            customers.append(customer.toCsv());
        });
        return customers.toString().getBytes();

    }

}
