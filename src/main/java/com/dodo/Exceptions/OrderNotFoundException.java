package com.dodo.Exceptions;

import com.dodo.Utils.Colors;

public class OrderNotFoundException extends Exception {
    public OrderNotFoundException(String errorMessage) {
        super(Colors.RED + errorMessage + Colors.RESET);
    }
}
