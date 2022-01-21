package com.dodo.Models;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import com.dodo.Utils.Parser;

public class Order implements Comparable<Order>, Serializable {
    private static final long serialVersionUID = 125235L;
    private int orderId;
    private int custId;
    private String orderDate;
    private List<Product> productList;

    public Order(int orderId, int custId, List<Product> productList, String orderDate) {
        this.orderId = orderId;
        this.custId = custId;
        this.productList = productList;
        this.orderDate = orderDate;
    }

    public final static String FORMAT = "%-20s%-20s%-20s%-20s\n+-----------------------------------------------------------+";

    public final static String[] HEADERS = { "CUSTOMER ID", "ORDER ID", "DATE", "PRODUCTS" };

    public static final Comparator<Order> SORT_BY_NEWEST_FIRST = new Comparator<Order>() {

        public int compare(Order order1, Order order2) {
            return order2.getOrderDate().compareTo(order1.getOrderDate());
        }

    };
    public static final Comparator<Order> SORT_BY_OLDEST_FIRST = new Comparator<Order>() {

        public int compare(Order order1, Order order2) {
            return order1.getOrderDate().compareTo(order2.getOrderDate());
        }

    };

    public int getOrderId() {
        return orderId;
    }

    public int getCustId() {
        return custId;
    }

    public double totalOrderCost() {

        double totalPrice = 0;
        for (Product product : productList) {
            totalPrice += product.getPrice();
        }
        return totalPrice;

    }

    @Override
    public int compareTo(Order order) {
        return Double.compare(order.totalOrderCost(), this.totalOrderCost());
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public int setProductList(List<Product> productList) {
        this.productList = productList;
        return 1;
    }

    @Override
    public String toString() {
        return "Order [custId=" + custId + ", orderDate=" + orderDate + ", orderId=" + orderId + ", productList="
                + productList + "]";
    }

    public String toFile() {

        StringBuilder order = new StringBuilder();

        order.append("Order (Customer ID=" + custId + ", Order Date=" + orderDate + ", Order ID=" + orderId + ")\n");

        for (Product product : productList) {
            order.append("\n" + product.toFile());
        }

        order.append("\nTOTAL ORDER COST: " + Parser.getCurrencyFormat(this.totalOrderCost()));

        return order.toString();
    }
}
