package com.dodo.Exceptions;

import com.dodo.Utils.Colors;

public class CustomerNotFoundException extends Exception {
    public CustomerNotFoundException(String errorMessage) {
        super(Colors.RED + errorMessage + Colors.RESET);
    }
}
