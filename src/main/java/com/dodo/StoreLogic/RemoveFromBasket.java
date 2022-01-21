package com.dodo.StoreLogic;

import java.util.List;

import com.dodo.Exceptions.ProductNotFoundException;
import com.dodo.Models.Product;
import com.dodo.Utils.Colors;

public class RemoveFromBasket {
    public static boolean removeFromBasket(List<Product> newBasket, String productName)
            throws ProductNotFoundException {

        if (!newBasket.isEmpty()) {

            for (Product product : newBasket) {

                if (product.getProductName().toLowerCase().contains(productName.toLowerCase())) {

                    System.out.println(Colors.YELLOW + "Removed " + product.getProductName() +
                            " from your basket." + Colors.RESET);

                    newBasket.remove(product);
                    return true;
                }
            }
        }
        throw new ProductNotFoundException("A product with that name could not be found.");
    }
}
