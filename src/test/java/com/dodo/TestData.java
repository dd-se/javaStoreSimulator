package com.dodo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dodo.Models.Customer;
import com.dodo.Models.Order;
import com.dodo.Models.Product;
import com.dodo.Models.Store;

public class TestData {

        public static Store getTestStore() {
                List<Product> productList = new ArrayList<>(Arrays.asList(
                                new Product(1, "SomeFood", 44.11), new Product(2, "SomeFood2", 55.21),
                                new Product(3, "SomeFood3", 66.31)));

                List<Product> productList2 = new ArrayList<>(Arrays.asList(
                                new Product(4, "SomeFood4", 77.32), new Product(5, "SomeFood5", 88),
                                new Product(6, "SomeFood6", 99)));

                List<Product> storeProductList = new ArrayList<>();
                storeProductList.addAll(productList);
                storeProductList.addAll(productList2);

                List<Customer> custList = new ArrayList<>(Arrays.asList(
                                new Customer(2, "Martin Dahlin", "martinstreet 1", "martin@gmail.com"),
                                new Customer(1, "Tomas Brolin", "tomasstreet 1", "tomas@gmail.com")));
                List<Order> orderList = new ArrayList<>(Arrays.asList(
                                new Order(1, 1, productList, "2022/01/12 18:00:19"),
                                new Order(2, 1, productList2, "2022/01/13 18:00:19"),
                                new Order(3, 2, productList, "2022/01/11 18:00:19")));

                return new Store(storeProductList, orderList, custList);
        }

}
