package com.example.sergey.testtask.mvvm.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * @author Sergey Rodionov
 */

public class DimensionUtils {

    public static int dpToPx(Context context, int valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }
}
