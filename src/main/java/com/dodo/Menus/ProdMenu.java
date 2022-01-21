package com.dodo.Menus;

import java.util.List;
import java.util.Map;

import com.dodo.App;
import com.dodo.Exceptions.ProductNameException;
import com.dodo.Exceptions.ProductPriceException;
import com.dodo.Models.Order;
import com.dodo.Models.Product;
import com.dodo.StoreLogic.Generators;
import com.dodo.Utils.Colors;
import com.dodo.Utils.ListUtils;
import com.dodo.Utils.PrintUtils;

public class ProdMenu {

    public final static String PROD_MENU = Colors.GREEN +
            "-[PRODUCT UTILS]-\n" + Colors.RESET + "[1] List products\n"
            + "[2] Add a product\n"
            + "[3] Top 5 products\nChoose: ";

    public static void productMenu(List<Product> storeProducts, List<Order> storeOrders) {

        System.out.print(PROD_MENU);
        String command = App.scanner.nextLine();

        switch (command) {
            case "1":
                PrintUtils.printProducts(storeProducts);
                break;
            case "2":
                try {
                    System.out.print("Product name: ");
                    String productName = App.scanner.nextLine();

                    System.out.print("Product price: ");
                    String productPrice = App.scanner.nextLine();

                    Product newProduct = Generators.generateProduct(storeProducts.size() + 1, productName,
                            productPrice);
                    storeProducts.add(newProduct);
                    System.out.println(Colors.YELLOW + newProduct + "added to the store." + Colors.RESET);

                } catch (ProductPriceException e) {
                    System.out.println(e.getMessage());
                } catch (ProductNameException e) {
                    System.out.println(e.getMessage());
                } catch (NumberFormatException e) {
                    System.out.println(Colors.RED + "Invalid price input." + Colors.RESET);
                }
                break;
            case "3":
                Map<String, Integer> mostPopularProducts = ListUtils.getMostPopularProducts(storeOrders);
                PrintUtils.printMostPopularProducts(mostPopularProducts, 5);
                break;
            case "b":
                return;
            default:
                System.out.println(App.INVALID_INPUT);
                break;
        }
        productMenu(storeProducts, storeOrders);
    }
}
