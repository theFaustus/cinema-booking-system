package com.evil.cbs_ticketvalidator.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (isOnline(context)) {
                dismissAlertDialog(context);
                log.info("--> There is internet!");
            } else {
                showAlertDialog(context);
                log.info("--> There is no internet!");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    private AlertDialog.Builder dialog = null;
    private AlertDialog alertDialog = null;

    private void showAlertDialog(Context context) {
        dialog = new AlertDialog.Builder(context);
        dialog.setTitle("No internet connection...");
        dialog.setCancelable(false);
        dialog.setMessage("In order to use this application you need to enable either the mobile networks or connect to a Wi-Fi network.");
        dialog.setNegativeButton("Exit", (dialog, i) -> {
            dialog.dismiss();
            ((Activity) context).finish();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        });
        dialog.setPositiveButton("Refresh", (dialog, i) -> {
            showAlertDialog(context);
        });
        alertDialog = dialog.show();
    }

    private void dismissAlertDialog(Context context) {
        if (alertDialog != null && alertDialog.isShowing())
            alertDialog.dismiss();
    }
}