package com.dodo.Exceptions;

import com.dodo.Utils.Colors;

public class CustomerAddressException extends Exception {
    public CustomerAddressException(String errorMessage) {
        super(Colors.RED + errorMessage + Colors.RESET);
    }
}
