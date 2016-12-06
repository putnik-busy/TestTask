package com.example.sergey.testtask.mvvm.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.example.sergey.testtask.R;

/**
 * @author Sergey Rodionov
 */

public class DialogUtils {

    public static final int IDD_NETWORK = 1;

    public static AlertDialog getDialog(Activity activity, int ID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        switch (ID) {
            case IDD_NETWORK:
                builder.setTitle(R.string.internet_connection_title);
                builder.setMessage(R.string.internet_connection_message);
                builder.setCancelable(true);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                return builder.create();
            default:
                return null;
        }
    }
}
