package com.dodo.StoreLogic;

import java.util.List;

import com.dodo.Exceptions.CustomerNotFoundException;
import com.dodo.Models.Customer;
import com.dodo.Utils.Colors;
import com.dodo.Utils.ListUtils;

public class TakeOrder {

    private static Customer customer;

    public static boolean setOrderCustomer(String custName, List<Customer> customers) {
        try {

            Customer result = ListUtils.findCustByName(custName, customers);
            customer = result;
            System.out.println(Colors.YELLOW + "Customer set to: " + customer.getName() + Colors.RESET);
            return true;

        } catch (CustomerNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean helpMenu() {
        System.out.println(Colors.YELLOW
                + "Available commands: ADD, REMOVE, CHECKOUT, RANDOM and ABORT."
                + "\nExamples: ADD <PRODUCT ID>, REMOVE <PRODUCT NAME>."
                + Colors.RESET);
        return true;
    }

    public static Customer getCustomer() {
        return customer;
    }

    public static void setCustomer(Customer customer) {
        TakeOrder.customer = customer;
    }

}
