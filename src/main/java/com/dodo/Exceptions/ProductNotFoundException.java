package com.dodo.Exceptions;

import com.dodo.Utils.Colors;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(String errorMessage) {
        super(Colors.RED + errorMessage + Colors.RESET);
    }
}