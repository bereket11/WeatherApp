package com.bignerdranch.android.weatherapp;

/**
 * Created by berekethaile on 4/24/18.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

public class ErrorHandler {
    public static void dialogWindow(@NonNull Context context, @NonNull String msg,
                                    @NonNull String title, String pos,
                                    DialogInterface.OnClickListener pListener,
                                    String neg, DialogInterface.OnClickListener nListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg).setTitle(title);

        if (pListener != null) {
            builder.setPositiveButton(pos, pListener);
        }

        if (nListener != null) {
            builder.setNegativeButton(neg, nListener);
        }
        builder.show();
    }
}
