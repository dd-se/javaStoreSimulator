package com.dodo.Utils;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Parser {
    static NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
    static SimpleDateFormat toFileDateFormat = new SimpleDateFormat("_yyyy-MM-dd_HH-mm-ss");
    static SimpleDateFormat newOrderDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public static double parseDouble(String doubleString) throws NumberFormatException {
        double aDouble = Double.parseDouble(doubleString);
        return aDouble;
    }

    public static int parseInt(String integerString) throws NumberFormatException {
        int integer = Integer.parseInt(integerString);
        return integer;
    }

    public static String getDate() {
        Date date = new Date();
        return newOrderDateFormat.format(date);
    }

    public static String getNewOrderDate() {
        Date date = new Date();
        return toFileDateFormat.format(date);
    }

    public static String getCurrencyFormat(double price) {
        return currencyFormat.format(price);

    }
}
