package com.example.sergey.testtask.mvvm.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * @author Sergey Rodionov
 */

public class ToastUtils {
    private static Toast mToast;

    public static void show(final Context context, final String text, final int duration) {
        Handler shower = new Handler(Looper.getMainLooper());
        shower.post(new Runnable() {
            @Override
            public void run() {
                if (mToast != null) {
                    mToast.cancel();
                }
                mToast = Toast.makeText(context, text, duration);
                mToast.show();
            }
        });
    }

    public static void show(final Context context, final int text, final int duration) {
        Handler shower = new Handler(Looper.getMainLooper());
        shower.post(new Runnable() {
            @Override
            public void run() {
                if (mToast != null) {
                    mToast.cancel();
                }
                mToast = Toast.makeText(context, text, duration);
                mToast.show();
            }
        });
    }
}
