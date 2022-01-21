package com.dodo.Exceptions;

import com.dodo.Utils.Colors;

public class CustomerNameException extends Exception {
    public CustomerNameException(String errorMessage) {
        super(Colors.RED + errorMessage + Colors.RESET);
    }
}
