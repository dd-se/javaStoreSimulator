package com.dodo.Models;

import java.io.Serializable;

public class Customer implements Serializable {
    private static final long serialVersionUID = 46345L;
    private int custId;
    private String name;
    private String address;
    private String email;

    public Customer(int custId, String name, String address, String email) {
        this.custId = custId;
        this.name = name;
        this.address = address;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public int getCustId() {
        return custId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    @Override
    public String toString() {
        return "Customer ID: " + this.getCustId() + " Customer Name: " + this.getName() + " Customer Email: "
                + this.getEmail() + " Customer Address: " + this.getAddress();
    }
}
