package com.dodo.Exceptions;

import com.dodo.Utils.Colors;

public class CustomerEmailException extends Exception {
    public CustomerEmailException(String errorMessage) {
        super(Colors.RED + errorMessage + Colors.RESET);
    }
}
