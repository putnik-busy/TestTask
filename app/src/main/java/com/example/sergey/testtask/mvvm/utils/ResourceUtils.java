package com.example.sergey.testtask.mvvm.utils;

import com.example.sergey.testtask.MyApplication;

/**
 * @author Sergey Rodionov
 */

public class ResourceUtils {

    public static String getString(int resourceId, String defaultValue) {
        String result = defaultValue;
        try {
            return MyApplication.getInstance()
                    .getResources()
                    .getString(resourceId);
        }
        catch (Exception ex) {
        }
        return result;
    }

    public static String getString(int resourceId) {
        return getString(resourceId, "");
    }
}
