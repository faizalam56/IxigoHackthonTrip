package com.example.faiyaz.ixigohackthontrip.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.faiyaz.ixigohackthontrip.R;

/**
 * Created by Faiyaz on 09-Apr-17.
 */
public class AlertDialogManager {
    public void showAlertDialog(Context context, String title, String message,
                                Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        alertDialog.setTitle(title);

        alertDialog.setMessage(message);

        if(status != null)

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        alertDialog.show();
    }
}
