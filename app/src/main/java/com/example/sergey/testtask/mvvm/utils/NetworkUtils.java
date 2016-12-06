package com.example.sergey.testtask.mvvm.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;

/**
 * @author Sergey Rodionov
 */

public class NetworkUtils {

    public static boolean isNetworkAvailable(AppCompatActivity appCompatActivity) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) appCompatActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
