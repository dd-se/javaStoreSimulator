package com.dodo.Exceptions;

import com.dodo.Utils.Colors;

public class ProductPriceException extends Exception {
    public ProductPriceException() {
        super(Colors.RED + "Product price is too low for our standards." + Colors.RESET);
    }
}