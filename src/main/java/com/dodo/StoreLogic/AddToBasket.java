package com.dodo.StoreLogic;

import java.util.List;

import com.dodo.Exceptions.ProductNotFoundException;
import com.dodo.Models.Product;
import com.dodo.Utils.Colors;

public class AddToBasket {

    public static boolean addToBasket(int productId, List<Product> newBasket, List<Product> storeProductList)
            throws ProductNotFoundException {

        if (productId <= storeProductList.size() && productId > 0) {

            Product chosenProduct = storeProductList.get(productId - 1);
            newBasket.add(chosenProduct);
            System.out.println(
                    Colors.YELLOW + "Added " + chosenProduct.getProductName() + " to your basket." + Colors.RESET);

            return true;
        }
        throw new ProductNotFoundException("A product with that name could not be found.");
    }
}
