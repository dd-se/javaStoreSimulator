package com.dodo.Exceptions;

import com.dodo.Utils.Colors;

public class ProductNameException extends Exception {
    public ProductNameException() {
        super(Colors.RED + "Invalid product name." + Colors.RESET);
    }
}