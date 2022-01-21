package com.dodo.Models;

public class CustomerHelper implements Comparable<CustomerHelper> {
    private int custId;
    private String name;
    private double totalSpent;

    public CustomerHelper(int custId, String name, double totalSpent) {
        this.custId = custId;
        this.name = name;
        this.totalSpent = totalSpent;
    }

    public int getCustId() {
        return custId;
    }

    public String getName() {
        return name;
    }

    public double getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(double totalSpent) {
        this.totalSpent = totalSpent;
    }

    @Override
    public int compareTo(CustomerHelper customerOfMonth) {
        return Double.compare(customerOfMonth.getTotalSpent(), this.getTotalSpent());
    }

    public void setName(String name) {
        this.name = name;
    }

}
