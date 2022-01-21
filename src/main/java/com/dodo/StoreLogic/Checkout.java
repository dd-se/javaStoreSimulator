package com.dodo.StoreLogic;

import java.util.Arrays;
import java.util.List;

import com.dodo.Models.Order;
import com.dodo.Models.Product;
import com.dodo.Utils.Colors;
import com.dodo.Utils.PrintUtils;
import com.dodo.Utils.StoreLoader;

public class Checkout {
    public static boolean checkout(List<Product> newBasket, List<Order> storeOrders, boolean writeToFile) {

        if (!newBasket.isEmpty()) {

            System.out.println(Colors.GREEN + "Done taking order for " +
                    TakeOrder.getCustomer().getName() + "." + Colors.RESET);

            Order newOrder = Generators.generateOrder(
                    storeOrders.size() + 1, TakeOrder.getCustomer().getCustId(), newBasket);
            storeOrders.add(newOrder);

            PrintUtils.printOrders(Arrays.asList(newOrder));

            if (writeToFile) {
                StoreLoader.WriteOrderToFile(newOrder);
            }
            return true;
        }
        System.out.println("You can't checkout, because your basket is empty.");
        return false;

    }
}
