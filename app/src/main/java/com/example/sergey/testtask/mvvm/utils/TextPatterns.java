package com.example.sergey.testtask.mvvm.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * @author Sergey Rodionov
 */

public class TextPatterns {

    public static String formatDoubleToString(double value) {
        String result = "";
        try {
            DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
            DecimalFormat formatter = new DecimalFormat("#.##", otherSymbols);
            String temp = formatter.format(value);
            result = temp.replace(",", " ") + " ";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
