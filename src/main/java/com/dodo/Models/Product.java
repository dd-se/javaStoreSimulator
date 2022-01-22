package com.dodo.Models;

import java.io.Serializable;

import com.dodo.Utils.Colors;
import com.dodo.Utils.Parser;

public class Product implements Comparable<Product>, Serializable {
    private static final long serialVersionUID = 24355L;
    private int productId;
    private String productName;
    private double price;

    public Product(int productId, String productName, double price) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setproductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int compareTo(Product product) {
        return Double.compare(product.price, this.price);
    }

    public final static String FORMAT = "%-35s%-50s%-35s";

    @Override
    public String toString() {
        if (this.getProductName().equals("Swedish Meatballs")) {
            return String.format(FORMAT, Colors.CYAN + "Product ID: " + Colors.WHITE + productId + Colors.CYAN,
                    "Product name: " + Colors.YELLOW + productName + Colors.CYAN,
                    "Price: " + Colors.WHITE + Parser.getCurrencyFormat(price) + Colors.RESET);
        }
        return String.format(FORMAT, Colors.CYAN + "Product ID: " + Colors.WHITE + productId + Colors.CYAN,
                "Product name: " + Colors.WHITE + productName + Colors.CYAN,
                "Price: " + Colors.WHITE + Parser.getCurrencyFormat(price) + Colors.RESET);
    }

    public String toFile() {
        return String.format(FORMAT, "Product ID: " + productId, "Product name: " + productName,
                "Price: " + Parser.getCurrencyFormat(price));

    }

    public String toCsv() {
        return this.productId + "," + this.productName + "," + this.price + "\n";

    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

}
